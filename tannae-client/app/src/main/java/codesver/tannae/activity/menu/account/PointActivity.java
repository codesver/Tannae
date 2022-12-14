package codesver.tannae.activity.menu.account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointActivity extends AppCompatActivity {

    private TextView textCurrent;
    private EditText editPoint;
    private Button buttonCharge, buttonBack;

    private SharedPreferences getter;
    private SharedPreferences.Editor setter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        getter = InnerDB.getter(getApplicationContext());
        setter = InnerDB.setter(getApplicationContext());

        setViews();
        setEventListeners();
        setActivity();
    }

    private void setViews() {
        textCurrent = findViewById(R.id.text_current_point);
        editPoint = findViewById(R.id.edit_charge_point);
        buttonCharge = findViewById(R.id.button_charge_point);
        buttonBack = findViewById(R.id.button_back_point);
    }

    private void setEventListeners() {
        buttonCharge.setOnClickListener(v -> charge());
        buttonBack.setOnClickListener(v -> onBackPressed());
    }

    private void setActivity() {
        int point = getter.getInt("point", 0);
        textCurrent.setText(String.valueOf(point));

        if (point < 0) {
            editPoint.setText(String.valueOf(point * -1));
            Toaster.toast(getApplicationContext(), "초과 포인트 사용 기록이 있습니다.\n충전해주시기 바랍니다.");
        }
    }

    private void charge() {
        int point = getter.getInt("point", 0);
        String chargePointText = editPoint.getText().toString();
        int chargePoint = Integer.parseInt(chargePointText.equals("") ? "0" : chargePointText);

        if (chargePointText.length() == 0 || chargePoint == 0)
            Toaster.toast(getApplicationContext(), "충전량을 입력하세요.");
        else if (point < 0 && -point > chargePoint)
            Toaster.toast(getApplicationContext(), "초과 사용 포인트 이상을 충전해주시기 바랍니다.");
        else chargeByServer(chargePoint);
    }

    private void chargeByServer(int point) {
        int usn = getter.getInt("usn", 0);
        Network.service.patchPoint(usn, point).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                Integer charged = response.body();
                Toaster.toast(getApplicationContext(), "충전을 완료하였습니다.");
                updatePoint(charged);
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void updatePoint(Integer charged) {
        setter.putInt("point", charged).apply();

        textCurrent.setText(String.valueOf(charged));
        editPoint.setText("");
    }
}