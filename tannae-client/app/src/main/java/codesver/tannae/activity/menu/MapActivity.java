package codesver.tannae.activity.menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import codesver.tannae.R;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private ViewGroup mapViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setMap();
        ((Button) findViewById(R.id.button_back_map)).setOnClickListener(v -> onBackPressed());
    }

    private void setMap() {
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);

        mapView = new MapView(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
        mapView.setZoomLevel(2, true);
        mapViewContainer = findViewById(R.id.frame_layout_main);
        mapViewContainer.addView(mapView);
        mapView.addCircle(new MapCircle(MapPoint.mapPointWithGeoCoord(latitude, longitude), 30,
                Color.argb(255, 18, 124, 234), Color.argb(255, 18, 124, 234)));
    }

    @Override
    public void onBackPressed() {
        mapViewContainer.removeView(mapView);
        super.onBackPressed();
    }
}