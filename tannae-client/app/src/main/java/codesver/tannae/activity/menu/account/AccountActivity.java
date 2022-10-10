package codesver.tannae.activity.menu.account;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.domain.User;
import codesver.tannae.service.InnerDB;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setActivity();
    }

    private void setActivity() {
        User user = InnerDB.getUser(getApplicationContext());
        ((TextView) findViewById(R.id.text_id_account)).setText(user.getId());
        ((TextView) findViewById(R.id.text_pw_account)).setText(user.getPw());
        ((TextView) findViewById(R.id.text_name_account)).setText(user.getName());
        ((TextView) findViewById(R.id.text_rrn_account)).setText(user.getRrn());
        ((TextView) findViewById(R.id.text_email_account)).setText(user.getEmail());
        ((TextView) findViewById(R.id.text_phone_account)).setText(user.getPhone());
        ((TextView) findViewById(R.id.text_score_account)).setText(String.valueOf(user.getScore()));
        ((TextView) findViewById(R.id.text_point_account)).setText(String.valueOf(user.getPoint()));
        ((TextView) findViewById(R.id.text_driver_account)).setText(user.getDriver() ? "등록" : "미등록");
        findViewById(R.id.button_back_account).setOnClickListener(v -> onBackPressed());
    }
}