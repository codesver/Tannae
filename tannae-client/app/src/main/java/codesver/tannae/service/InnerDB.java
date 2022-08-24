package codesver.tannae.service;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.domain.User;

public class InnerDB extends AppCompatActivity {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public InnerDB(Context context) {
        sp = context.getSharedPreferences("InnerDB", MODE_PRIVATE);
        editor = sp.edit();
    }

    public SharedPreferences getter() {
        return sp;
    }

    public SharedPreferences.Editor setter() {
        return editor;
    }

    public void saveUser(User user) {
        editor.putInt("usn", user.getUsn())
                .putString("id", user.getId())
                .putString("pw", user.getPw())
                .putString("name", user.getName())
                .putString("rrn", user.getRrn())
                .putInt("gender", user.getGender())
                .putString("email", user.getEmail())
                .putString("phone", user.getPhone())
                .putBoolean("exist", true)
                .apply();
    }
}
