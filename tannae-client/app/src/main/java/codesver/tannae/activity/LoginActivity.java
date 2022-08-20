package codesver.tannae.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import codesver.tannae.R;


public class LoginActivity extends AppCompatActivity {
    private EditText editIdView, editPwView;
    private Button buttonFindView, buttonSignUpView, buttonLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
        setEventListeners();
    }

    private void setViews() {
        editIdView = findViewById(R.id.edit_id_login);
        editPwView = findViewById(R.id.edit_pw_login);
        buttonFindView = findViewById(R.id.button_find_login);
        buttonSignUpView = findViewById(R.id.button_sign_up_login);
        buttonLoginView = findViewById(R.id.button_login_login);
    }

    private void setEventListeners() {
        buttonFindView.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FindActivity.class)));
        buttonSignUpView.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SignUpActivity.class)));
        buttonLoginView.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }
}