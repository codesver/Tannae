package codesver.tannae.activity.menu.qna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QnaDetailActivity extends AppCompatActivity {

    private TextView textTitle, textDate, textQuestion, textAnswer;
    private Button buttonEdit, buttonDelete, buttonAnswer, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_detail);
        setViews();

    }

    private void setViews() {
        textTitle = findViewById(R.id.text_title_qna_detail);
        textDate = findViewById(R.id.text_date_qna_detail);
        textQuestion = findViewById(R.id.text_question_qna_detail);
        textAnswer = findViewById(R.id.text_answer_qna_detail);
        buttonEdit = findViewById(R.id.button_edit_qna_detail);
        buttonDelete = findViewById(R.id.button_delete_qna_detail);
        buttonAnswer = findViewById(R.id.button_answer_qna_detail);
        buttonBack = findViewById(R.id.button_back_qna_detail);
        getContent();
    }

    private void getContent() {
        Intent intent = getIntent();
        int csn = intent.getIntExtra("csn", 0);
        getContentByServer(csn);
    }

    private void getContentByServer(int csn) {
        Network.service.getContent(csn).enqueue(new Callback<ContentDTO>() {
            @Override
            public void onResponse(Call<ContentDTO> call, Response<ContentDTO> response) {
                ContentDTO content = response.body();
                setContent(content);
            }

            @Override
            public void onFailure(Call<ContentDTO> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setContent(ContentDTO content) {
        textTitle.setText("제목 : " + content.getTitle());
        textDate.setText("등록일 : " + content.getDateTime());
        textQuestion.setText(content.getQuestion());
        textAnswer.setText(content.getAnswer() != null ? content.getAnswer() : "답변이 아직 등록되지 않았습니다.");
    }
}