package codesver.tannae.activity.menu.content;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_detail);
        findViewById(R.id.button_back_faq_detail_activity).setOnClickListener(v -> onBackPressed());
        setTextByServer();
    }

    private void setTextByServer() {
        Network.service.getContent(getIntent().getIntExtra("csn", 0)).enqueue(new Callback<ContentDTO>() {
            @Override
            public void onResponse(@NonNull Call<ContentDTO> call, @NonNull Response<ContentDTO> response) {
                ContentDTO faq = response.body();
                setTexts(faq);
            }

            @Override
            public void onFailure(@NonNull Call<ContentDTO> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void setTexts(ContentDTO faq) {
        ((TextView) findViewById(R.id.text_title_faq_detail)).setText(faq.getTitle());
        ((TextView) findViewById(R.id.text_date_faq_detail)).setText(faq.getDateTime());
        ((TextView) findViewById(R.id.text_question_faq_detail)).setText(faq.getQuestion());
        ((TextView) findViewById(R.id.text_answer_faq_detail)).setText(faq.getAnswer());
    }
}