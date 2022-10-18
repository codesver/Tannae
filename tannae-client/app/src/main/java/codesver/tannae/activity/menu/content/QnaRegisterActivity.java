package codesver.tannae.activity.menu.content;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.RegisterContentDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QnaRegisterActivity extends AppCompatActivity {

    private EditText editTitle, editQuestion;
    private Button buttonRegister, buttonBack;

    private SharedPreferences getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna_register);
        getter = InnerDB.getter(getApplicationContext());
        setViews();
        setEventListeners();
    }

    private void setViews() {
        editTitle = findViewById(R.id.edit_title_qna_register);
        editQuestion = findViewById(R.id.edit_question_qna_register);
        buttonRegister = findViewById(R.id.button_register_qna_register);
        buttonBack = findViewById(R.id.button_back_qna_register);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonRegister.setOnClickListener(v -> register());
    }

    private void register() {
        RegisterContentDTO dto = new RegisterContentDTO(getter.getInt("usn", 0),
                editTitle.getText().toString(), editQuestion.getText().toString());
        registerByServer(dto);
    }

    private void registerByServer(RegisterContentDTO dto) {
        Network.service.postContent(dto).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                Toaster.toast(getApplicationContext(), Boolean.TRUE.equals(response.body()) ? "QnA가 등록되었습니다." : "QnA 등록이 실패했습니다.");
                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}