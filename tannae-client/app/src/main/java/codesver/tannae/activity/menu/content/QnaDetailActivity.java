package codesver.tannae.activity.menu.content;

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

    private boolean questionFlag, answerFlag;

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
            if (!questionFlag) editQuestion();
            else editQuestionByServer();
        });
        buttonDelete.setOnClickListener(v -> deleteByServer(getIntent().getIntExtra("csn", 0)));
        buttonAnswer.setOnClickListener(v -> {
            if (!answerFlag) editAnswer();
            else editAnswerByServer();
        });
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
                setContent(content);
            }

            @Override
            public void onFailure(@NonNull Call<ContentDTO> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "????????? ??????????????????.\n??????????????? ??????????????????.");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setContent(ContentDTO content) {
        textTitle.setText("?????? : " + content.getTitle());
        textDate.setText("????????? : " + content.getDateTime());
        editQuestion.setText(content.getQuestion());
        editAnswer.setText(content.getAnswer() != null ? content.getAnswer() : "????????? ?????? ???????????? ???????????????.");

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
        Toaster.toast(getApplicationContext(), "?????? ????????? ????????? ??? ????????????.");
        questionFlag = true;
    }

    private void editQuestionByServer() {
        Network.service.patchQuestion(getIntent().getIntExtra("csn", 0), new StringDTO(editQuestion.getText().toString())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (Boolean.TRUE.equals(response.body())) {
                    Toaster.toast(getApplicationContext(), "????????? ?????????????????????.");
                    editQuestion.setBackgroundResource(R.drawable.rectangle);
                    editQuestion.setEnabled(false);
                    buttonDelete.setVisibility(View.VISIBLE);
                    questionFlag = false;
                } else {
                    Toaster.toast(getApplicationContext(), "?????? ????????? ?????????????????????.");
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "????????? ??????????????????.\n??????????????? ??????????????????.");
            }
        });
    }

    private void deleteByServer(int csn) {
        Network.service.deleteContent(csn).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toaster.toast(getApplicationContext(), "????????? ?????????????????????.");
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "????????? ??????????????????.\n??????????????? ??????????????????.");
            }
        });
    }

    private void editAnswer() {
        editAnswer.setEnabled(true);
        editAnswer.setBackgroundResource(R.drawable.rectangle_edit);
        Toaster.toast(getApplicationContext(), "????????? ????????? ??? ????????????.");
        answerFlag = true;
    }

    private void editAnswerByServer() {
        Network.service.patchAnswer(getIntent().getIntExtra("csn", 0), new StringDTO(editAnswer.getText().toString())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toaster.toast(getApplicationContext(), "????????? ?????????????????????.");
                editAnswer.setBackgroundResource(R.drawable.rectangle);
                editAnswer.setEnabled(false);
                answerFlag = false;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "????????? ??????????????????.\n??????????????? ??????????????????.");
            }
        });
    }
}