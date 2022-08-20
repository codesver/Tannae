package codesver.tannae.activity.account;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.network.Network;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private Button buttonBack, buttonCheckId, buttonCheckUser, buttonSignUp;
    private EditText editId, editPw, editPwCheck, editName, editRrnFront, editRrnBack, editEmail, editPhone;

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
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonCheckId.setOnClickListener(v -> checkId());
        buttonCheckUser.setOnClickListener(v -> checkUser());
        buttonSignUp.setOnClickListener(v -> signUp());
    }

    private void checkId() {
        Network.service.checkId(editId.getText().toString()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println("HI");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println("BYE");
            }
        });
    }

    private void checkUser() {
    }

    private void signUp() {
    }
}