package codesver.tannae.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.activity.user.LoginActivity;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout linearAccount, linearPoint, linearLost, linearHistory, linearQna, linearFaq;
    private Button buttonBack, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setViews();
        setEventListeners();
    }

    private void setViews() {
        linearAccount = findViewById(R.id.linear_layout_account_menu);
        linearPoint = findViewById(R.id.linear_layout_point_menu);
        linearLost = findViewById(R.id.linear_layout_lost_menu);
        linearHistory = findViewById(R.id.linear_layout_history_menu);
        linearQna = findViewById(R.id.linear_layout_qna_menu);
        linearFaq = findViewById(R.id.linear_layout_faq_menu);

        buttonBack = findViewById(R.id.button_back_menu);
        buttonLogout = findViewById(R.id.button_logout_menu);
    }

    private void setEventListeners() {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonLogout.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }
}