package codesver.tannae.activity.menu.content;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import codesver.tannae.R;
import codesver.tannae.dto.ContentFaqDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.ListViewAdapter;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<ContentFaqDTO> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        findViewById(R.id.button_back_faq_activity).setOnClickListener(v -> onBackPressed());
        setAdapter();
    }

    private void setAdapter() {
        listView = findViewById(R.id.list_view_faqs_faq);
        Network.service.getFaqs().enqueue(new Callback<List<ContentFaqDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<ContentFaqDTO>> call, @NonNull Response<List<ContentFaqDTO>> response) {
                List<ContentFaqDTO> faqs = response.body();
                if (faqs.isEmpty()) {
                    Toaster.toast(getApplicationContext(), "등록된 FAQ 가 없습니다.");
                    onBackPressed();
                } else {
                    adapter = new ListViewAdapter<>();
                    for (ContentFaqDTO faq : faqs) adapter.addItem(faq);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ContentFaqDTO>> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}