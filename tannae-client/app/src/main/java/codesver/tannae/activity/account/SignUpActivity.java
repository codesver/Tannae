package codesver.tannae.activity.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.network.Network;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private Button buttonBack, buttonCheckId, buttonCheckUser, buttonSignUp;
    private EditText editId, editPw, editPwCheck, editName, editRrnFront, editRrnBack, editEmail, editPhone;
    private TextView textIdState, textPwState, textPrivateState;

    private boolean idAvailable, idChecked;
    private boolean pwAvailable, pwChecked;
    private boolean privateAvailable, privateChecked;

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
        textPrivateState = findViewById(R.id.text_private_sign_up);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonCheckId.setOnClickListener(v -> checkId());
        buttonCheckUser.setOnClickListener(v -> checkUser());
        buttonSignUp.setOnClickListener(v -> signUp());

        editId.addTextChangedListener(idChecker());
        editPw.addTextChangedListener(pwChecker());
        editPwCheck.addTextChangedListener(pwDoubleChecker());

        editName.addTextChangedListener(privateChecker());
        editRrnFront.addTextChangedListener(privateChecker());
        editRrnBack.addTextChangedListener(privateChecker());
    }

    private void checkId() {
        if (!idAvailable) {
            textIdState.setTextColor(0xAAFF0000);
            textIdState.setText("사용 불가능한 ID 형식입니다.");
            Toaster.toast(this, "ID를 다시 입력하세요.");
            return;
        }

        Network.service.checkId(editId.getText().toString()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                idChecked = response.body();
                textIdState.setText(idChecked ? "사용 가능한 ID 입니다." : "다른 ID를 사용해주세요.");
                textIdState.setTextColor(idChecked ? 0xAA0000FF : 0xAAFF0000);
                Toaster.toast(SignUpActivity.this, idChecked ? "사용 가능한 ID 입니다." : "이미 사용 중인 ID 입니다.");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(SignUpActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void checkUser() {
        String name = editName.getText().toString();
        String rrnFront = editRrnFront.getText().toString();
        String rrnBack = editRrnBack.getText().toString();

        if (!privateAvailable) {
            textPrivateState.setText("개인정보를 정확하게 입력하세요.");
            textPrivateState.setTextColor(0xAAFF0000);
            privateChecked = false;
        } else {
            Network.service.checkPrivate(name, rrnFront + "-" + rrnBack).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Boolean isNew = response.body();
                    if (isNew) {
                        textPrivateState.setText("본인인증이 되었습니다.");
                        textPrivateState.setTextColor(0xAA0000FF);
                        privateChecked = true;
                        Toaster.toast(SignUpActivity.this, "본인인증이 완료되었습니다.");
                    } else {
                        textPrivateState.setText("이미 가입된 사용자입니다.");
                        textPrivateState.setTextColor(0xAAFF0000);
                        privateChecked = false;
                        Toaster.toast(SignUpActivity.this, "이미 가입된 사용자입니다.");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toaster.toast(SignUpActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
                }
            });
        }
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

    private TextWatcher pwChecker() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = editPw.getText().toString();
                pwChecked = false;

                if (pw.length() == 0) {
                    textPwState.setText("영문과 숫자를 사용하여 8자 이상 작성하세요.");
                    textPwState.setTextColor(0xAA000000);
                    pwAvailable = false;
                } else if (pw.length() >= 8
                        && pw.matches(".*[a-zA-Z0-9].*")
                        && !pw.matches(".[가-힣].*")
                        && !pw.matches(".*[\\W].*")) {
                    if (pw.equals(editPwCheck.getText().toString())) {
                        textPwState.setText("PW가 일치합니다.");
                        textPwState.setTextColor(0xAA0000FF);
                        pwChecked = true;
                    } else {
                        textPwState.setText("사용 가능한 PW 형식힙니다. 비밀번호를 확인해주세요.");
                        textPwState.setTextColor(0xAA000000);
                    }
                    pwAvailable = true;
                } else {
                    textPwState.setText("사용 불가능한 PW 형식입니다.");
                    textPwState.setTextColor(0xAAFF0000);
                    pwAvailable = false;
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

    private TextWatcher pwDoubleChecker() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pwAvailable) {
                    textPwState.setText("사용 불가능한 PW 형식입니다. PW를 다시 입력하세요.");
                    textPwState.setTextColor(0xAAFF0000);
                    pwChecked = false;
                } else if (editPw.getText().toString().equals(editPwCheck.getText().toString())) {
                    textPwState.setText("PW가 일치합니다.");
                    textPwState.setTextColor(0xAA0000FF);
                    pwChecked = true;
                } else {
                    textPwState.setText("PW가 일치하지 않습니다.");
                    textPwState.setTextColor(0xAAFF0000);
                    pwChecked = false;
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

    private TextWatcher privateChecker() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                privateAvailable = false;
                if (editName.getText().toString().length() == 0) {
                    textPrivateState.setText("이름을 입력하세요.");
                    textPrivateState.setTextColor(0xAAFF0000);
                } else if (editRrnFront.getText().toString().length() != 6) {
                    textPrivateState.setText("주민등록번호 앞자리를 정확하게 입력하세요.");
                    textPrivateState.setTextColor(0xAAFF0000);
                } else if (editRrnBack.getText().toString().length() != 7) {
                    textPrivateState.setText("주민등록번호 뒷자리를 정확하게 입력하세요.");
                    textPrivateState.setTextColor(0xAAFF0000);
                } else {
                    textPrivateState.setText("본인인증을 하세요.");
                    textPrivateState.setTextColor(0xAA000000);
                    privateAvailable = true;
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