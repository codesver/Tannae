package codesver.tannae.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import codesver.tannae.R;
import codesver.tannae.activity.menu.MenuActivity;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;

public class MainActivity extends AppCompatActivity {

    private Button buttonMenu;
    private FloatingActionButton buttonService;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    private final SharedPreferences getter = InnerDB.getter(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMap();
        setViews();
        setEventListeners();
    }

    private void setMap() {
        mapView = new MapView(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.1761175, 126.9058167), true);
        mapView.setZoomLevel(2, true);
        mapViewContainer = findViewById(R.id.frame_layout_main);
        mapViewContainer.addView(mapView);
        (findViewById(R.id.text_tannae_main)).bringToFront();
        (findViewById(R.id.image_tannae_logo_main)).bringToFront();
    }

    private void setViews() {
        buttonMenu = findViewById(R.id.button_menu_main);
        buttonService = findViewById(R.id.button_service_main);
    }

    private void setEventListeners() {
        buttonMenu.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MenuActivity.class)));
        buttonService.setOnClickListener(v -> request());
    }

    private void request() {
        if (getter.getInt("point", 0) < 1000) {
            Toaster.toast(getApplicationContext(), "잔여 포인트가 1000p 미만입니다.\n충전 후 이용 가능합니다.");
            return;
        }
        mapViewContainer.removeView(mapView);
        boolean driver = getter.getBoolean("driver", false);
        startActivity(new Intent(MainActivity.this, driver ? NavigationActivity.class : RequestActivity.class).putExtra("driver", driver));
    }

    @Override
    public void onBackPressed() {
        Toaster.toast(getApplicationContext(), "메뉴에서 로그아웃 해주세요.");
    }
}