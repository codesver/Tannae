package codesver.tannae.activity.menu.content;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private Button buttonBack, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        setView();
        setEventListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    private void setView() {
        listView = findViewById(R.id.list_view_qnas_qna);
        buttonBack = findViewById(R.id.button_back_qna);
        buttonRegister = findViewById(R.id.button_register_qna);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonRegister.setOnClickListener(v -> startActivity(new Intent(QnaActivity.this, QnaRegisterActivity.class)));
    }

    private void setAdapter() {
        Network.service.getContents().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<ContentDTO>> call, @NonNull Response<List<ContentDTO>> response) {
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
            public void onFailure(@NonNull Call<List<ContentDTO>> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}