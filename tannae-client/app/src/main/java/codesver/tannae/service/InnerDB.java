package codesver.tannae.service;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.domain.User;

public class InnerDB {

    public static SharedPreferences getter(Context context) {
        return context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor setter(Context context) {
        return context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE).edit();
    }

    public static void saveUser(Context context, User user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE).edit();
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

    public static User callUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE);

        int usn = sp.getInt("usn", -1);
        String id = sp.getString("id", null);
        String pw = sp.getString("pw", null);
        String name = sp.getString("name", null);
        String rrn = sp.getString("rrn", null);
        int gender = sp.getInt("gender", 1);
        String email = sp.getString("email", null);
        String phone = sp.getString("phone", null);

        return new User(usn, id, pw, name, rrn, gender, email, phone);
    }

    public static void clear(Context context) {
        context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE).edit().clear().apply();
    }
}
