package codesver.tannae.activity.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import codesver.tannae.R;
import codesver.tannae.domain.History;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.ListViewAdapter;

public class QnaActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<History> adapter;

    private SharedPreferences getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);

        getter = InnerDB.getter(getApplicationContext());

        listView = findViewById(R.id.list_view_qnas_qna);
    }
}