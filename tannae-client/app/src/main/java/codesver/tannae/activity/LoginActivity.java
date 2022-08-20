package codesver.tannae.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import codesver.tannae.R;


public class LoginActivity extends AppCompatActivity {
    private EditText editId, editPw;
    private Button buttonFind, buttonSignUp, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
        setEventListeners();
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
        buttonLogin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }
}