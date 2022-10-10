package codesver.tannae.activity.menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import codesver.tannae.R;
import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.ListViewAdapter;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<HistoryDTO> adapter;

    private SharedPreferences getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getter = InnerDB.getter(getApplicationContext());

        listView = findViewById(R.id.list_view_histories_history);
        findViewById(R.id.button_back_history).setOnClickListener(v -> onBackPressed());
        setAdapter();
    }

    private void setAdapter() {
        Network.service.getHistories(getter.getInt("usn", 0)).enqueue(new Callback<List<HistoryDTO>>() {
            @Override
            public void onResponse(Call<List<HistoryDTO>> call, Response<List<HistoryDTO>> response) {
                List<HistoryDTO> histories = response.body();
                if (histories.isEmpty()) {
                    Toaster.toast(getApplicationContext(), "이용 기록이 없습니다.");
                    onBackPressed();
                } else {
                    adapter = new ListViewAdapter<>();
                    for (HistoryDTO history : histories) adapter.addItem(history);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<HistoryDTO>> call, Throwable t) {
                Log.e("Error", t.getLocalizedMessage());
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}