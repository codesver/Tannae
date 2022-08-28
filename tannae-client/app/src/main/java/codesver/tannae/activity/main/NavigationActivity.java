package codesver.tannae.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import codesver.tannae.R;
import codesver.tannae.network.Network;

public class NavigationActivity extends AppCompatActivity {

    private Button buttonTransfer, buttonEnd, buttonBack;
    private TextView textPath;
    private SwitchCompat switchDrive;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    private boolean driverState, shareState;
    private double originLatitude, originLongitude, destinationLatitude, destinationLongitude;

    private WebSocketClient socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        bringExtras();
        connectWebSocket();
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
        }
    }

    private void connectWebSocket() {
        socket = createWebSocket(createUri());
        socket.connect();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socket.send("HELLO SPRING!!!");
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
    }

    private void setEventListeners() {

    }

    private URI createUri() {
        try {
            return new URI("ws://" + Network.ip + "/service");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private WebSocketClient createWebSocket(URI uri) {
        return new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakeMessage) {
            }

            @Override
            public void onMessage(String message) {

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {
            }
        };
    }
}