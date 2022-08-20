package codesver.tannae.activity.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.network.Network;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private Button buttonBack, buttonCheckId, buttonCheckUser, buttonSignUp;
    private EditText editId, editPw, editPwCheck, editName, editRrnFront, editRrnBack, editEmail, editPhone;
    private TextView textIdState, textPwState;

    private boolean idAvailable, idChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setViews();
        setEventListeners();
    }

    private void setViews() {
        buttonBack = findViewById(R.id.button_back_sign_up);
        buttonCheckId = findViewById(R.id.button_check_id_sign_up);
        buttonCheckUser = findViewById(R.id.button_check_user_sign_up);
        buttonSignUp = findViewById(R.id.button_sign_up_sign_up);

        editId = findViewById(R.id.edit_id_sign_up);
        editPw = findViewById(R.id.edit_pw_sign_up);
        editPwCheck = findViewById(R.id.edit_pw_check_sign_up);
        editName = findViewById(R.id.edit_name_sign_up);
        editRrnFront = findViewById(R.id.edit_rrn_front_sign_up);
        editRrnBack = findViewById(R.id.edit_rrn_back_sign_up);
        editEmail = findViewById(R.id.edit_email_sign_up);
        editPhone = findViewById(R.id.edit_phone_sign_up);

        textIdState = findViewById(R.id.text_id_state_sign_up);
        textPwState = findViewById(R.id.text_pw_state_sign_up);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonCheckId.setOnClickListener(v -> checkId());
        buttonCheckUser.setOnClickListener(v -> checkUser());
        buttonSignUp.setOnClickListener(v -> signUp());

        editId.addTextChangedListener(idChecker());
    }

    private void checkId() {
        if (!idAvailable) {
            textIdState.setTextColor(0xAAFF0000);
            textIdState.setText("사용 불가능한 ID 형식입니다.");
            Toast.makeText(this, "ID를 다시 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Network.service.checkId(editId.getText().toString()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                idChecked = response.body();
                textIdState.setText(idChecked ? "사용 가능한 ID 입니다." : "다른 ID를 사용해주세요.");
                textIdState.setTextColor(idChecked ? 0xAA0000FF : 0xAAFF0000);
                Toast.makeText(SignUpActivity.this, idChecked ? "사용 가능한 ID 입니다." : "이미 사용 중인 ID 입니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUser() {
    }

    private void signUp() {
    }

    private TextWatcher idChecker() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String id = editId.getText().toString();
                idChecked = false;

                if (id.length() == 0) {
                    textIdState.setText("영문과 숫자를 사용하여 6자리 이상 작성하세요.");
                    textIdState.setTextColor(0xAA000000);
                    idAvailable = false;
                } else if (id.length() >= 6
                        && id.matches(".*[a-zA-Z0-9].*")
                        && !id.matches(".[가-힣].*")
                        && !id.matches(".*[\\W].*")) {
                    textIdState.setText("사용 가능한 ID 형식입니다. 사용을 위해서 중복체크를 하세요.");
                    textIdState.setTextColor(0xAA000000);
                    idAvailable = true;
                } else {
                    textIdState.setText("사용 불가능한 ID 형식입니다.");
                    textIdState.setTextColor(0xAAFF0000);
                    idAvailable = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
}