package codesver.tannae.service;

import android.content.Context;
import android.content.SharedPreferences;

import codesver.tannae.dto.UserDTO;

public class InnerDB {

    public static SharedPreferences getter(Context context) {
        return context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor setter(Context context) {
        return context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE).edit();
    }

    public static void saveUser(Context context, UserDTO user) {
        SharedPreferences.Editor editor = context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE).edit();
        editor.putInt("usn", user.getUsn())
                .putString("id", user.getId())
                .putString("pw", user.getPw())
                .putString("name", user.getName())
                .putString("rrn", user.getRrn())
                .putBoolean("gender", user.getGender())
                .putString("email", user.getEmail())
                .putString("phone", user.getPhone())
                .putBoolean("isManage", user.getManage())
                .putBoolean("isDriver", user.getDriver())
                .putBoolean("board", user.getOnBoard())
                .putInt("point", user.getPoint())
                .putFloat("score", user.getScore())
                .putBoolean("exist", true)
                .apply();
    }

    public static UserDTO getUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE);

        int usn = sp.getInt("usn", -1);
        String id = sp.getString("id", null);
        String pw = sp.getString("pw", null);
        String name = sp.getString("name", null);
        String rrn = sp.getString("rrn", null);
        boolean gender = sp.getBoolean("gender", false);
        String email = sp.getString("email", null);
        String phone = sp.getString("phone", null);
        boolean isManage = sp.getBoolean("isManage", false);
        boolean isDriver = sp.getBoolean("isDriver", false);
        boolean board = sp.getBoolean("board", false);
        int point = sp.getInt("point", 0);
        float score = sp.getFloat("score", 0.0f);

        return new UserDTO(usn, id, pw, name, rrn, gender, email, phone, board, isManage, isDriver, point, score);
    }

    public static void clear(Context context) {
        context.getSharedPreferences("InnerDB", Context.MODE_PRIVATE).edit().clear().apply();
    }
}
