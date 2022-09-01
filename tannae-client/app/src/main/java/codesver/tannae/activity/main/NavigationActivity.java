package codesver.tannae.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import ua.naiksoftware.stomp.StompClient;

public class NavigationActivity extends AppCompatActivity {

    private Button buttonTransfer, buttonEnd, buttonBack;
    private TextView textPath;
    private SwitchCompat switchDrive;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    private boolean driverState, shareState;
    private String origin, destination;
    private double originLatitude, originLongitude, destinationLatitude, destinationLongitude;

    private StompClient stomp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        if (getIntent().getBooleanExtra("driver", true))
            onCreateDriver();
        else
            onCreatePassenger();
    }

    private void onCreateDriver() {
        setMap();
        setViews();
        setEventListeners();
        
    }

    private void onCreatePassenger() {

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

    }

    private void setVisibility() {
        if (!driverState) {
            buttonBack.setVisibility(View.INVISIBLE);
            buttonTransfer.setVisibility(View.INVISIBLE);
            buttonEnd.setVisibility(View.INVISIBLE);
            switchDrive.setVisibility(View.INVISIBLE);
        }
    }

    private void bringExtras() {
        Intent intent = getIntent();
        driverState = intent.getBooleanExtra("driverState", true);
        if (!driverState) {
            shareState = intent.getBooleanExtra("shareState", false);
            origin = intent.getStringExtra("origin");
            destination = intent.getStringExtra("destination");
            originLatitude = intent.getDoubleExtra("originLatitude", 0);
            originLongitude = intent.getDoubleExtra("originLongitude", 0);
            destinationLatitude = intent.getDoubleExtra("destinationLatitude", 0);
            destinationLongitude = intent.getDoubleExtra("destinationLongitude", 0);
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
                ServiceResponseDTO res = response.body();
                responseHandler(res);
            }

            @Override
            public void onFailure(Call<ServiceResponseDTO> call, Throwable t) {
                Toaster.toast(NavigationActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
                startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void responseHandler(ServiceResponseDTO dto) {
        int flag = dto.getFlag();

        if (flag == -2) {
            Toaster.toast(NavigationActivity.this, "교통 혼잡으로 이용 가능한 차량이 없습니다.\n다음에 다시 이용해주세요.");
            startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (flag == -1) {
            Toaster.toast(NavigationActivity.this, "이용 가능한 차량이 없습니다.\n다음에 다시 이용해주세요.");
            startActivity(new Intent(NavigationActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (flag == 1) {
            Toaster.toast(NavigationActivity.this, "차량이 배차 되었습니다.\n출발지에서 대기해주세요.");
            setMap();
            setViews();
            setEventListeners();
            connectStompForPassengers(dto);
        }
    }

    private void connectStompForDriver() {
        stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + Network.ip + "/service");
        stomp.connect();

        Disposable subscribe = stomp.topic("/sub/vehicle/" + InnerDB.getter(getApplicationContext()).getInt("vsn", 0)).subscribe(topicMessage -> {
            runOnUiThread(() -> {

            });
        });
    }

    private void connectStompForPassengers(ServiceResponseDTO dto) {
        stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + Network.ip + "/service");
        stomp.connect();

        Disposable subscribe = stomp.topic("/sub/vehicle/" + dto.getVsn()).subscribe(topicMessage -> {
            runOnUiThread(() -> {

            });
        });

        JSONObject data;
        try {
            data = new JSONObject().put("flag", 0)
                    .put("vsn", dto.getVsn())
                    .put("summary", dto.getSummary())
                    .put("path", dto.getPath());
            stomp.send("/pub/request", data.toString()).subscribe();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}