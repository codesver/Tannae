package codesver.tannae.activity.main.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import codesver.tannae.R;
import codesver.tannae.activity.main.MainActivity;
import codesver.tannae.service.Toaster;

public class RequestActivity extends AppCompatActivity implements MapView.MapViewEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private RadioGroup radioGroupLocation;
    private TextView textOrigin, textDestination;
    private SwitchCompat switchShare;
    private Button buttonRequest, buttonBack;

    private MapView mapView;
    private ViewGroup mapViewContainer;
    private MapPoint center;
    private MapReverseGeoCoder mapGeoCoder;

    private boolean locationType = true, shareState;
    private double originLongitude, originLatitude, destinationLongitude, destinationLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        setMap();
        setViews();
        setEventListeners();
    }

    private void setMap() {
        mapView = new MapView(this);
        mapView.setMapViewEventListener(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.1761175, 126.9058167), true);
        mapView.setZoomLevel(2, true);
        mapViewContainer = findViewById(R.id.frame_layout_request);
        mapViewContainer.addView(mapView);
    }

    private void setViews() {
        radioGroupLocation = findViewById(R.id.radio_group_location_request);
        textOrigin = findViewById(R.id.text_origin_request);
        textDestination = findViewById(R.id.text_destination_request);
        switchShare = findViewById(R.id.switch_share_request);
        buttonRequest = findViewById(R.id.button_request_request);
        buttonBack = findViewById(R.id.button_back_request);
    }

    private void setEventListeners() {
        radioGroupLocation.setOnCheckedChangeListener((group, checkedId) -> locationType = checkedId == R.id.radio_origin_request);
        switchShare.setOnCheckedChangeListener((buttonView, isChecked) -> shareState = isChecked);
        buttonRequest.setOnClickListener(v -> request());
        buttonBack.setOnClickListener(v -> onBackPressed());
    }

    private void request() {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        center = mapView.getMapCenterPoint();
        mapGeoCoder = new MapReverseGeoCoder("be32c53145962ae88db090324e2223b0", center, this, RequestActivity.this);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        mapGeoCoder.startFindingAddress();

        if (locationType) {
            originLatitude = center.getMapPointGeoCoord().latitude;
            originLongitude = center.getMapPointGeoCoord().longitude;
        } else {
            destinationLatitude = center.getMapPointGeoCoord().latitude;
            destinationLongitude = center.getMapPointGeoCoord().longitude;
        }
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        if (locationType) textOrigin.setText(s);
        else textDestination.setText(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        if (locationType) textOrigin.setText("올바른 지역이 아닙니다.");
        else textDestination.setText("올바른 지역이 아닙니다.");
    }

    @Override
    public void onBackPressed() {
        mapViewContainer.removeView(mapView);
        startActivity(new Intent(RequestActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}