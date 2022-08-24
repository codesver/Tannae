package codesver.tannae.activity.menu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;

public class AccountActivity extends AppCompatActivity {

    private TextView textName, textRrn, textEmail, textPhone, textId, textPw;
    private Button buttonEdit, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setViews();
        setEventListeners();
    }

    private void setViews() {
        textName = findViewById(R.id.text_name_account);
        textRrn = findViewById(R.id.text_rrn_account);
        textEmail = findViewById(R.id.text_email_account);
        textPhone = findViewById(R.id.text_phone_account);
        textId = findViewById(R.id.text_phone_account);
        textPw = findViewById(R.id.text_pw_account);

        buttonEdit = findViewById(R.id.button_edit_account);
        buttonBack = findViewById(R.id.button_back_account);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
    }
}