package codesver.tannae.activity.menu.lost;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.ContentFaqDTO;
import codesver.tannae.dto.LostDTO;
import codesver.tannae.service.ListViewAdapter;

public class LostActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<LostDTO> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        listView = findViewById(R.id.list_view_losts_lost);
        findViewById(R.id.button_back_lost).setOnClickListener(v -> onBackPressed());
        setAdapter();
    }

    private void setAdapter() {
        // Network
    }
}