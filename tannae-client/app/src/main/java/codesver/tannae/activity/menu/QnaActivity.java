package codesver.tannae.activity.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import codesver.tannae.R;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.ListViewAdapter;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QnaActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<ContentDTO> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);

        listView = findViewById(R.id.list_view_qnas_qna);
        findViewById(R.id.button_back_content).setOnClickListener(v -> onBackPressed());
        setAdapter();
    }

    private void setAdapter() {
        Network.service.getContents().enqueue(new Callback<List<ContentDTO>>() {
            @Override
            public void onResponse(Call<List<ContentDTO>> call, Response<List<ContentDTO>> response) {
                List<ContentDTO> contents = response.body();
                if (contents.isEmpty()) {
                    Toaster.toast(getApplicationContext(), "QnA가 없습니다.");
                } else {
                    adapter = new ListViewAdapter<>();
                    for (ContentDTO content : contents) adapter.addItem(content);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ContentDTO>> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}