package codesver.tannae.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import codesver.tannae.R;
import codesver.tannae.network.Network;
import codesver.tannae.service.Toaster;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class NavigationActivity extends AppCompatActivity {

    private Button buttonTransfer, buttonEnd, buttonBack;
    private TextView textPath;
    private SwitchCompat switchDrive;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    private boolean driverState, shareState;
    private double originLatitude, originLongitude, destinationLatitude, destinationLongitude;

    private StompClient stomp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        checkAvailability();
        setMap();
        setViews();
        setEventListeners();
        bringExtras();
    }

    private void bringExtras() {
        Intent intent = getIntent();
        driverState = intent.getBooleanExtra("driverState", true);
        if (!driverState) {
            shareState = intent.getBooleanExtra("shareState", false);
            originLatitude = intent.getDoubleExtra("originLatitude", 0);
            originLongitude = intent.getDoubleExtra("originLongitude", 0);
            destinationLatitude = intent.getDoubleExtra("destinationLatitude", 0);
            destinationLongitude = intent.getDoubleExtra("destinationLongitude", 0);
            connectStomp();
        }
    }

    private void checkAvailability() {

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
        setVisibility();
    }

    private void setVisibility() {
        if (!driverState) {
            buttonBack.setVisibility(View.INVISIBLE);
            buttonTransfer.setVisibility(View.INVISIBLE);
            buttonEnd.setVisibility(View.INVISIBLE);
            switchDrive.setVisibility(View.INVISIBLE);
        }
    }

    private void setEventListeners() {

    }

    private void connectStomp() {
        stomp = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://" + Network.ip + "/service");
        stomp.connect();

        Disposable subscribe = stomp.topic("/sub/vehicle/1").subscribe(topicMessage -> {
            runOnUiThread(() -> {
                String payload = topicMessage.getPayload();
                Toaster.toast(NavigationActivity.this, payload);
            });
        });

        stomp.send("/pub/hello", "HELLO SPRING!!!").subscribe();
    }
}