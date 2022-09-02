package codesver.tannae.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

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
    private TextView textPath;
    private SwitchCompat switchDrive;

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
                String payload = topicMessage.getPayload();
                Toaster.toast(getApplicationContext(), payload);
            });
        });

        Network.stomp.send("/pub/connect", InnerDB.getter(getApplicationContext()).getString("id", "")).subscribe();
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
            Toaster.toast(NavigationActivity.this, "차량이 배차 되었습니다.\n출발지에서 대기해주세요.");
            setting();
            setVisibility();
            connectStomp(responseDTO.getVsn());
            sendResponseBack();
        }
    }

    private void sendResponseBack() {
        JSONObject data;
        try {
            data = new JSONObject().put("flag", 0)
                    .put("vsn", responseDTO.getVsn())
                    .put("summary", responseDTO.getSummary())
                    .put("path", responseDTO.getPath());
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
        textPath = findViewById(R.id.text_path_navigation);
        switchDrive = findViewById(R.id.switch_drive_navigation);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> {
            if (switchDrive.isChecked())
                Toaster.toast(getApplicationContext(), "운행중에는 화면을 전환할 수 없습니다.");
            else
                onBackPressed();
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
        buttonBack.setVisibility(View.INVISIBLE);
        buttonTransfer.setVisibility(View.INVISIBLE);
        buttonEnd.setVisibility(View.INVISIBLE);
        switchDrive.setVisibility(View.INVISIBLE);
    }
}