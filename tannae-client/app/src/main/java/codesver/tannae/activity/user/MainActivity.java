package codesver.tannae.activity.user;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.MapView;

import codesver.tannae.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonMenu;
    private FloatingActionButton buttonService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMap();
        setViews();
        setEventListeners();
    }

    private void setMap() {
        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.frame_layout_main);
        mapViewContainer.addView(mapView);
        (findViewById(R.id.text_tannae_main)).bringToFront();
        (findViewById(R.id.image_tannae_logo_main)).bringToFront();
    }

    private void setViews() {
        buttonMenu = findViewById(R.id.button_menu_main);
        buttonService = findViewById(R.id.button_service_main);
    }

    private void setEventListeners() {

    }
}