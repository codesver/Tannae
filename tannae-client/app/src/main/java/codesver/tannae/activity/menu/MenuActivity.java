package codesver.tannae.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.activity.menu.account.AccountActivity;
import codesver.tannae.activity.menu.account.PointActivity;
import codesver.tannae.activity.menu.content.FaqActivity;
import codesver.tannae.activity.menu.content.QnaActivity;
import codesver.tannae.activity.menu.history.HistoryActivity;
import codesver.tannae.activity.menu.lost.LostActivity;
import codesver.tannae.activity.user.LoginActivity;
import codesver.tannae.service.InnerDB;

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
        linearAccount.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, AccountActivity.class)));
        linearPoint.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, PointActivity.class)));
        linearHistory.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, HistoryActivity.class)));
        linearQna.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, QnaActivity.class)));
        linearFaq.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, FaqActivity.class)));
        linearLost.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, LostActivity.class)));

        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonLogout.setOnClickListener(v -> {
            InnerDB.clear(getApplicationContext());
            startActivity(new Intent(MenuActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }
}