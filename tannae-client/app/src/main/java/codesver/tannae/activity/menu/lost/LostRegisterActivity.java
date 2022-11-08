package codesver.tannae.activity.menu.lost;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;

public class LostRegisterActivity extends AppCompatActivity {

    private EditText editLost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_register);
        setViews();
    }

    private void setViews() {
        editLost = findViewById(R.id.edit_lost_lost_register);
        findViewById(R.id.button_back_lost_register).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.button_register_lost_register).setOnClickListener(v -> registerByServer());
    }

    private void registerByServer() {

    }
}