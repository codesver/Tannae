package codesver.tannae.activity.menu.qna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.StringDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QnaDetailActivity extends AppCompatActivity {

    private TextView textTitle, textDate;
    private EditText editQuestion, editAnswer;
    private Button buttonEdit, buttonDelete, buttonAnswer, buttonBack;

    private SharedPreferences getter;

    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_detail);
        getter = InnerDB.getter(getApplicationContext());
        setViews();
        setEventListeners();
    }

    private void setViews() {
        textTitle = findViewById(R.id.text_title_qna_detail);
        textDate = findViewById(R.id.text_date_qna_detail);
        editQuestion = findViewById(R.id.edit_question_qna_detail);
        editAnswer = findViewById(R.id.edit_answer_qna_detail);
        buttonEdit = findViewById(R.id.button_edit_qna_detail);
        buttonDelete = findViewById(R.id.button_delete_qna_detail);
        buttonAnswer = findViewById(R.id.button_answer_qna_detail);
        buttonBack = findViewById(R.id.button_back_qna_detail);
        getContent();
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonEdit.setOnClickListener(v -> {
            if (!flag) editQuestion();
            else editQuestionByServer();
        });
        // buttonDelete
        // buttonAnswer
    }

    private void getContent() {
        Intent intent = getIntent();
        int csn = intent.getIntExtra("csn", 0);
        getContentByServer(csn);
    }

    private void getContentByServer(int csn) {
        Network.service.getContent(csn).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ContentDTO> call, @NonNull Response<ContentDTO> response) {
                ContentDTO content = response.body();
                assert content != null;
                setContent(content);
            }

            @Override
            public void onFailure(@NonNull Call<ContentDTO> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setContent(ContentDTO content) {
        textTitle.setText("제목 : " + content.getTitle());
        textDate.setText("등록일 : " + content.getDateTime());
        editQuestion.setText(content.getQuestion());
        editAnswer.setText(content.getAnswer() != null ? content.getAnswer() : "답변이 아직 등록되지 않았습니다.");

        int usn = getter.getInt("usn", 0);

        if (content.getUsn() == usn) {
            if (content.getAnswer() == null)
                buttonEdit.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.VISIBLE);
        }

        if (getter.getBoolean("isManage", false)) {
            buttonAnswer.setVisibility(View.VISIBLE);
        }
    }

    private void editQuestion() {
        buttonDelete.setVisibility(View.GONE);
        editQuestion.setEnabled(true);
        editQuestion.setBackgroundResource(R.drawable.rectangle_edit);
        Toaster.toast(getApplicationContext(), "질문 내용을 수정할 수 있습니다.");
        flag = true;
    }

    private void editQuestionByServer() {
        Network.service.postQuestion(getIntent().getIntExtra("csn", 0), new StringDTO(editQuestion.getText().toString())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (Boolean.TRUE.equals(response.body())) {
                    Toaster.toast(getApplicationContext(), "질문이 수정되었습니다.");
                    editQuestion.setBackgroundResource(R.drawable.rectangle);
                    editQuestion.setEnabled(false);
                    buttonDelete.setVisibility(View.VISIBLE);
                } else {
                    Toaster.toast(getApplicationContext(), "질문 수정이 거부되었습니다.");
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        }));
    }
}