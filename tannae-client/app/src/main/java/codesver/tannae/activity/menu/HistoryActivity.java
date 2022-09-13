package codesver.tannae.activity.menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import codesver.tannae.R;
import codesver.tannae.domain.History;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.ListViewAdapter;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<History> adapter;

    private final SharedPreferences getter = InnerDB.getter(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = findViewById(R.id.list_view_histories_history);
        setAdapter();
    }

    private void setAdapter() {
        Network.service.getHistories(getter.getInt("usn", 0)).enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                List<History> histories = response.body();
                if (histories.isEmpty()) {
                    Toaster.toast(getApplicationContext(), "이용 기록이 없습니다.");
                    onBackPressed();
                } else {
                    adapter = new ListViewAdapter<>();
                    for (History history : histories) adapter.addItem(history);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}