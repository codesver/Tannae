package codesver.tannae.activity.menu.content;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.service.ListViewAdapter;

public class FaqActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<ContentDTO> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        findViewById(R.id.button_back_faq_activity).setOnClickListener(v -> onBackPressed());
    }
}