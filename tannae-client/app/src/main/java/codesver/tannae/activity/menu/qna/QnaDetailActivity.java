package codesver.tannae.activity.menu.qna;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;

public class QnaDetailActivity extends AppCompatActivity {

    private TextView textTitle, textQuestion, textAnswer;
    private Button buttonEdit, buttonDelete, buttonAnswer, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_detail);
        setViews();

    }

    private void setViews() {
        textTitle = findViewById(R.id.text_title_qna_detail);
        textQuestion = findViewById(R.id.text_question_qna_detail);
        textAnswer = findViewById(R.id.text_answer_qna_detail);
        buttonEdit = findViewById(R.id.button_edit_qna_detail);
        buttonDelete = findViewById(R.id.button_delete_qna_detail);
        buttonAnswer = findViewById(R.id.button_answer_qna_detail);
        buttonBack = findViewById(R.id.button_back_qna_detail);
        setContent();
    }

    private void setContent() {
        Intent intent = getIntent();
        int csn = intent.getIntExtra("csn", 0);
    }
}