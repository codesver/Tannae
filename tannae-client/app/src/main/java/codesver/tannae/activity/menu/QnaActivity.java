package codesver.tannae.activity.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import codesver.tannae.R;
import codesver.tannae.domain.Content;
import codesver.tannae.domain.History;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.ListViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        setAdapter();
    }

    private void setAdapter() {
        Network.service.getContents().enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {

            }
        });
    }
}