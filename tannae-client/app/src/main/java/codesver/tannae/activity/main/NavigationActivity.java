package codesver.tannae.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import codesver.tannae.activity.menu.history.ReceiptActivity;
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

    private boolean type;
    private JSONArray path, guides;
    private int passed;

    private SharedPreferences getter;
    private SharedPreferences.Editor setter;

    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        getter = InnerDB.getter(getApplicationContext());
        setter = InnerDB.setter(getApplicationContext());

        if (getIntent().getBooleanExtra("driver", true)) onCreateDriver();
        else onCreatePassenger();
    }

    private void onCreateDriver() {
        setting();
        connectStomp(getter.getInt("vsn", 0));
    }

    private void onCreatePassenger() {
        bringExtras();
        request();
    }

    private void connectStomp(int vsn) {
        Network.stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + Network.ip + "/service");
        Network.stomp.connect();

        subscribe = Network.stomp.topic("/sub/vehicle/" + vsn).subscribe(topicMessage -> runOnUiThread(() -> {
            try {
                mainProcess(topicMessage.getPayload());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));

        Network.stomp.send("/pub/connect", getter.getString("id", "")).subscribe();
    }

    private void mainProcess(String responseMessage) throws JSONException {
        JSONObject response = new JSONObject(responseMessage);
        int flag = response.getInt("flag");
        int usn = response.getInt("usn");

        int innerUsn = getter.getInt("usn", 0);
        boolean driver = getter.getBoolean("driver", false);

        switch (flag) {
            case 0: {
                extractDataFromResponse(response);
                Toaster.toast(getApplicationContext(), type ?
                        driver ? "승객이 탑승하였습니다." : usn == innerUsn ? "차량이 도착하였습니다.\n탑승해주세요." : "경유 지점입니다.\n승객이 승차합니다." :
                        driver ? "승객이 하차하였습니다." : usn == innerUsn ? "목적지에 도착하였습니다.\n하차해주세요." : "경유 지점입니다.\n승객이 하차합니다.");
                if (usn == innerUsn && !type) endService(false);
                else drawer(setter);
            }
            break;
            case 1: {
                extractDataFromResponse(response);
                Toaster.toast(getApplicationContext(), driver ?
                        "미동승 탑승자 요청이 들어왔습니다.\n탑승자 위치로 이동해주세요." :
                        "차량이 배차되었습니다.\n탑승 지점에서 기다려주세요.");
                if (driver) startService();
                drawer(setter);
            }
            break;
            case 2: {
                extractDataFromResponse(response);
                Toaster.toast(getApplicationContext(), driver ?
                        "동승 탑승자 요청이 들어왔습니다.\n 탑승자 위치로 이동해주세요." :
                        "동승 가능한 차량이 없어 신규 차량으로 배차되었습니다.\n탑승 지점에서 기다려주세요.");
                if (driver) startService();
                drawer(setter);
            }
            break;
            case 3: {
                extractDataFromResponse(response);
                Toaster.toast(getApplicationContext(), driver ?
                        "추가 탑승자 요청이 들어왔습니다.\n경로가 수정됩니다." :
                        usn == innerUsn ?
                                "동승 차량이 배차되었습니다.\n 탑승 지점에서 기다려주세요." :
                                "이용 중인 차량에 신규 동승자가 배차되었습니다.");
                drawer(setter);
            }
            break;
            case 4: {
                Toaster.toast(getApplicationContext(), driver ?
                        "운행이 종료되었습니다." :
                        "목적지에 도착하였습니다.\n 하차해주세요.");
                endService(driver);
            }
            break;
            default:
                Toaster.toast(getApplicationContext(), "Wrong response");
        }
    }

    private void extractDataFromResponse(JSONObject response) throws JSONException {
        type = response.getBoolean("type");
        path = new JSONArray(response.getString("path"));
        guides = new JSONArray(response.getString("guides"));
        passed = response.getInt("passed");
    }

    private void startService() {
        switchRun.setEnabled(false);
        buttonTransfer.setEnabled(true);
        buttonTransfer.setTextColor(Color.parseColor("#127CEA"));
    }

    private void endService(boolean driver) {
        if (driver) {
            switchRun.setEnabled(true);
            switchRun.setChecked(false);
            textCurrentPath.setText("현재 탑승자가 없습니다.");
            textCurrentPath.setTextColor(Color.parseColor("#CCCCCC"));
            textNextPath.setText("현재 탑승자가 없습니다.");
            textNextPath.setTextColor(Color.parseColor("#CCCCCC"));
            mapView.removeAllPolylines();
            mapView.removeAllCircles();
        } else {
            mapViewContainer.removeView(mapView);
            subscribe.dispose();
            startActivity(new Intent(NavigationActivity.this, ReceiptActivity.class));
        }
    }

    private void drawer(SharedPreferences.Editor setter) throws JSONException {
        setter.putString("guides", guides.toString()).apply();
        setter.putString("path", path.toString()).apply();
        drawGuides(guides);
        drawPath(path, passed);
    }

    private void drawGuides(JSONArray guides) throws JSONException {
        mapView.removeAllPolylines();
        mapView.removeAllCircles();

        MapPolyline polyline = new MapPolyline();
        polyline.setLineColor(Color.argb(255, 240, 128, 128));

        for (int i = 0; i < guides.length(); i++) {
            JSONObject point = guides.getJSONObject(i);
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
        int usn = getter.getInt("usn", 0);
        String id = getter.getString("id", null);
        boolean gender = getter.getBoolean("gender", true);
        ServiceRequestDTO dto = new ServiceRequestDTO(usn, id, gender, origin, destination, originLatitude, originLongitude, destinationLatitude, destinationLongitude, shareState);
        requestByServer(dto);
    }

    private void requestByServer(ServiceRequestDTO dto) {
        Network.service.request(dto).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ServiceResponseDTO> call, @NonNull Response<ServiceResponseDTO> response) {
                responseDTO = response.body();
                responseHandler();
            }

            @Override
            public void onFailure(@NonNull Call<ServiceResponseDTO> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
                startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void responseHandler() {
        int flag = responseDTO.getFlag();

        if (flag == -2) {
            Toaster.toast(getApplicationContext(), "교통 혼잡으로 이용 가능한 차량이 없습니다.\n다음에 다시 이용해주세요.");
            startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (flag == -1) {
            Toaster.toast(getApplicationContext(), "이용 가능한 차량이 없습니다.\n다음에 다시 이용해주세요.");
            startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else {
            setting();
            setVisibility();
            connectStomp(responseDTO.getVsn());
            setter.putBoolean("board", true).apply();
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
                    .put("guides", responseDTO.getGuides())
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
        try {
            JSONObject request = new JSONObject()
                    .put("vsn", getter.getInt("vsn", 0))
                    .put("guides", getter.getString("guides", null));
            Network.stomp.send("/pub/transfer", request.toString()).subscribe();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void switchRunByServer(boolean isChecked) {
        Network.service.switchRun(getter.getInt("vsn", 0), isChecked).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                Toaster.toast(getApplicationContext(), Boolean.TRUE.equals(response.body()) ? "운행이 활성화되었습니다." : "운행이 비활성화 되었습니다.");
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
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
            if (getter.getBoolean("board", false)) {
                Toaster.toast(getApplicationContext(), "서비스 이용 중에는 화면을 전환할 수 없습니다.");
                return;
            }
            Network.stomp.disconnect();
            mapViewContainer.removeView(mapView);
            startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}