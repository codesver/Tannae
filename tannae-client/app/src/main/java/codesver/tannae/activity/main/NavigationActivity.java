package codesver.tannae.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import codesver.tannae.R;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.dto.ServiceResponseDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;

public class NavigationActivity extends AppCompatActivity {

    private Button buttonTransfer, buttonEnd, buttonBack;
    private TextView textCurrentPath, textNextPath;
    private SwitchCompat switchRun;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    private boolean shareState;
    private String origin, destination;
    private double originLatitude, originLongitude, destinationLatitude, destinationLongitude;
    private ServiceResponseDTO responseDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        if (getIntent().getBooleanExtra("driver", true)) onCreateDriver();
        else onCreatePassenger();
    }

    private void onCreateDriver() {
        setting();
        connectStomp(InnerDB.getter(getApplicationContext()).getInt("vsn", 0));
    }

    private void onCreatePassenger() {
        bringExtras();
        request();
    }

    private void connectStomp(int vsn) {
        Network.stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + Network.ip + "/service");
        Network.stomp.connect();

        Disposable subscribe = Network.stomp.topic("/sub/vehicle/" + vsn).subscribe(topicMessage -> {
            runOnUiThread(() -> {
                try {
                    mainProcess(topicMessage.getPayload());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });

        Network.stomp.send("/pub/connect", InnerDB.getter(getApplicationContext()).getString("id", "")).subscribe();
    }

    private void mainProcess(String payload) throws JSONException {
        JSONObject data = new JSONObject(payload);
        SharedPreferences getter = InnerDB.getter(getApplicationContext());

        int flag = data.getInt("flag");
        int vsn = data.getInt("vsn");
        int usn = data.getInt("usn");
        boolean type = data.getBoolean("type");
        JSONArray path = new JSONArray(data.getString("path"));
        JSONArray guider = new JSONArray(data.getString("guider"));
        int passed = data.getInt("passed");

        int innerUsn = getter.getInt("usn", 0);
        boolean driver = getter.getBoolean("driver", false);
        String toast = "";

        switch (flag) {
            case 0: {   // When vehicle arrives at point
                if (driver) {
                    toast = "주요 지점에 도착하였습니다.\n" + (type ? "탑승자가 탑승할 때까지 기다려주세요." : "탑승자가 내릴 때까지 기다려 주세요.");
                } else {
                    if (usn == innerUsn) {
                        toast = type ? "차량이 도착하였습니다.\n탑승해주세요." : "목적지에 도착하였습니다.\n하차해주세요.";
                    } else {
                        toast = "주요 지점에 도착하였습니다.\n" + (type ? "탑승자가 탑승할 때까지 기다려주세요." : "탑승자가 내릴 때까지 기다려 주세요.");
                    }
                }
                break;
            }
            case 1: {   // Non-share user match
                if (driver) {
                    toast = "미동승 탑승자 요청이 들어왔습니다.\n탑승자의 출발지로 이동해주세요.";
                } else {
                    toast = "차량이 배차되었습니다.\n탑승 지점에서 기다려주세요.";
                }
                break;
            }
            case 2: {   // Share user new match
                if (driver) {
                    toast = "동승 탑승자 요청이 들어왔습니다.\n탑승자의 출발지로 이동해주세요.";
                } else {
                    toast = "차량이 배차되었습니다.\n탑승 지점에서 기다려주세요.";
                }
                break;
            }
            case 3: {   // Share user match
                if (driver) {
                    toast = "추가 탑승자 요청이 들어왔습니다.\n경로가 수정됩니다.";
                } else {
                    if (usn == innerUsn) {
                        toast = "차량이 배차되었습니다.\n탑승 지점에서 기다려주세요.";
                    } else {
                        toast = "고객님의 이용 차량에 동승자가 추가되었습니다.";
                    }
                }
                break;
            }
        }

        switchRun.setEnabled(false);
        buttonTransfer.setEnabled(true);
        buttonTransfer.setTextColor(Color.parseColor("#127CEA"));
        Toaster.toast(getApplicationContext(), toast);
        drawGuide(guider);
        drawPath(path, passed);
    }

    private void drawGuide(JSONArray guider) throws JSONException {
        mapView.removeAllPolylines();
        mapView.removeAllCircles();

        MapPolyline polyline = new MapPolyline();
        polyline.setLineColor(Color.argb(255, 240, 128, 128));

        for (int i = 0; i < guider.length(); i++) {
            JSONObject point = guider.getJSONObject(i);
            double longitude = point.getDouble("x");
            double latitude = point.getDouble("y");
            polyline.addPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        }

        mapView.addPolyline(polyline);
        MapPointBounds bounds = new MapPointBounds(polyline.getMapPoints());
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, 100));
    }

    private void drawPath(JSONArray path, int passed) throws JSONException {
        int count = 0;

        for (int i = passed + 1; i < path.length(); i++) {
            JSONObject point = path.getJSONObject(i);
            double longitude = point.getDouble("x");
            double latitude = point.getDouble("y");

            count++;
            if (count == 1) {
                mapView.addCircle(new MapCircle(MapPoint.mapPointWithGeoCoord(latitude, longitude), 15,
                        Color.argb(255, 18, 124, 234), Color.argb(255, 18, 124, 234)));
                textCurrentPath.setText(point.getString("name"));
                textCurrentPath.setTextColor(Color.parseColor("#000000"));
            } else if (count == 2) {
                mapView.addCircle(new MapCircle(MapPoint.mapPointWithGeoCoord(latitude, longitude), 15,
                        Color.argb(255, 18, 124, 234), Color.argb(255, 18, 124, 234)));
                textNextPath.setTextColor(Color.parseColor("#000000"));
                textNextPath.setText(point.getString("name"));
            } else
                mapView.addCircle(new MapCircle(MapPoint.mapPointWithGeoCoord(latitude, longitude), 15,
                        Color.argb(255, 0, 0, 0), Color.argb(255, 0, 0, 0)));
        }
    }

    private void request() {
        SharedPreferences getter = InnerDB.getter(getApplicationContext());
        int usn = getter.getInt("usn", 0);
        String id = getter.getString("id", null);
        boolean gender = getter.getBoolean("gender", true);
        ServiceRequestDTO dto = new ServiceRequestDTO(usn, id, gender, origin, destination, originLatitude, originLongitude, destinationLatitude, destinationLongitude, shareState);
        requestByServer(dto);
    }

    private void requestByServer(ServiceRequestDTO dto) {
        Network.service.request(dto).enqueue(new Callback<ServiceResponseDTO>() {
            @Override
            public void onResponse(Call<ServiceResponseDTO> call, Response<ServiceResponseDTO> response) {
                responseDTO = response.body();
                responseHandler();
            }

            @Override
            public void onFailure(Call<ServiceResponseDTO> call, Throwable t) {
                Toaster.toast(NavigationActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
                startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void responseHandler() {
        int flag = responseDTO.getFlag();

        if (flag == -2) {
            Toaster.toast(NavigationActivity.this, "교통 혼잡으로 이용 가능한 차량이 없습니다.\n다음에 다시 이용해주세요.");
            startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (flag == -1) {
            Toaster.toast(NavigationActivity.this, "이용 가능한 차량이 없습니다.\n다음에 다시 이용해주세요.");
            startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (flag == 1) {
            setting();
            setVisibility();
            connectStomp(responseDTO.getVsn());
            sendResponseBack();
        }
    }

    private void sendResponseBack() {
        JSONObject data;
        try {
            data = new JSONObject().put("flag", 1)
                    .put("vsn", responseDTO.getVsn())
                    .put("usn", responseDTO.getUsn())
                    .put("type", false)
                    .put("path", responseDTO.getPath())
                    .put("guider", responseDTO.getGuider())
                    .put("passed", responseDTO.getPassed());
            Network.stomp.send("/pub/request", data.toString()).subscribe();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setting() {
        setMap();
        setViews();
        setEventListeners();
    }

    private void setMap() {
        mapView = new MapView(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.1761175, 126.9058167), true);
        mapView.setZoomLevel(2, true);
        mapViewContainer = findViewById(R.id.frame_layout_navigation);
        mapViewContainer.addView(mapView);
    }

    private void setViews() {
        buttonTransfer = findViewById(R.id.button_transfer_navigation);
        buttonEnd = findViewById(R.id.button_end_navigation);
        buttonBack = findViewById(R.id.button_back_navigation);
        textCurrentPath = findViewById(R.id.text_current_path_navigation);
        textNextPath = findViewById(R.id.text_next_path_navigation);
        switchRun = findViewById(R.id.switch_run_navigation);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        switchRun.setOnCheckedChangeListener((buttonView, isChecked) -> switchRunByServer(isChecked));
        buttonTransfer.setOnClickListener(v -> transfer());
    }

    private void transfer() {
        Network.stomp.send("/pub/transfer", String.valueOf(InnerDB.getter(getApplicationContext()).getInt("vsn", 0))).subscribe();
    }

    private void switchRunByServer(boolean isChecked) {
        Network.service.switchRun(InnerDB.getter(getApplicationContext()).getInt("vsn", 0), isChecked).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toaster.toast(NavigationActivity.this, response.body() ? "운행이 활성화되었습니다." : "운행이 비활성화 되었습니다.");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(NavigationActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void bringExtras() {
        Intent intent = getIntent();
        shareState = intent.getBooleanExtra("shareState", false);
        origin = intent.getStringExtra("origin");
        destination = intent.getStringExtra("destination");
        originLatitude = intent.getDoubleExtra("originLatitude", 0);
        originLongitude = intent.getDoubleExtra("originLongitude", 0);
        destinationLatitude = intent.getDoubleExtra("destinationLatitude", 0);
        destinationLongitude = intent.getDoubleExtra("destinationLongitude", 0);
    }

    private void setVisibility() {
        buttonBack.setVisibility(View.GONE);
        buttonTransfer.setVisibility(View.GONE);
        buttonEnd.setVisibility(View.GONE);
        switchRun.setVisibility(View.GONE);
        findViewById(R.id.linear_layout_buttons_navigation).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (switchRun.isChecked())
            Toaster.toast(getApplicationContext(), "운행중에는 화면을 전환할 수 없습니다.");
        else {
            mapViewContainer.removeView(mapView);
            super.onBackPressed();
        }
    }
}