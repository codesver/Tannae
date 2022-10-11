package codesver.tannae.activity.menu.qna;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;

public class QnaRegisterActivity extends AppCompatActivity {

    private EditText editTitle, editQuestion;
    private Button buttonRegister, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_register);
        setViews();
    }

    private void setViews() {
        editTitle = findViewById(R.id.edit_title_qna_register);
        editQuestion = findViewById(R.id.edit_question_qna_register);
        buttonRegister = findViewById(R.id.button_register_qna_register);
        buttonBack = findViewById(R.id.button_back_qna_register);
    }
}