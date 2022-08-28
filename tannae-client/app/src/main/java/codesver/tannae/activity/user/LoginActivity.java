package codesver.tannae.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.activity.main.MainActivity;
import codesver.tannae.domain.User;
import codesver.tannae.dto.user.LoginDTO;
import codesver.tannae.network.Network;
import codesver.tannae.network.RetrofitClient;
import codesver.tannae.network.ServiceApi;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText editId, editPw;
    private Button buttonFind, buttonSignUp, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Network.service = RetrofitClient.getClient().create(ServiceApi.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
        setEventListeners();
        autoLogin();
    }

    private void setViews() {
        editId = findViewById(R.id.edit_id_login);
        editPw = findViewById(R.id.edit_pw_login);

        buttonFind = findViewById(R.id.button_find_login);
        buttonSignUp = findViewById(R.id.button_sign_up_login);
        buttonLogin = findViewById(R.id.button_login_login);
    }

    private void setEventListeners() {
        buttonFind.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FindActivity.class)));
        buttonSignUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        buttonLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String id = editId.getText().toString();
        String pw = editPw.getText().toString();

        if (id.equals(""))
            Toaster.toast(LoginActivity.this, "ID를 입력하세요.");
        else if (pw.equals(""))
            Toaster.toast(LoginActivity.this, "PW를 입력하세요.");
        else
            loginByServer(id, pw, false);
    }

    private void loginByServer(String id, String pw, boolean exist) {
        Network.service.login(id, pw).enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                LoginDTO login = response.body();
                if (login.exist()) {
                    if (!exist) InnerDB.saveUser(getApplicationContext(), login.getUser());
                    Toaster.toast(LoginActivity.this, id + "님 반갑습니다!");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toaster.toast(LoginActivity.this, "존재하지 않는 계정입니다.");
                    editId.setText("");
                    editPw.setText("");
                }
            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {
                Toaster.toast(LoginActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void autoLogin() {
//        Toaster.toast(LoginActivity.this, "Test Login");
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        SharedPreferences getter = InnerDB.getter(getApplicationContext());
        int usn = getter.getInt("usn", -1);
        if (usn != -1) {
            String id = getter.getString("id", null);
            String pw = getter.getString("pw", null);
            loginByServer(id, pw, true);
        }
    }
}