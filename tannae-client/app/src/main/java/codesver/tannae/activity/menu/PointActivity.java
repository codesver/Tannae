package codesver.tannae.activity.menu;

import static codesver.tannae.service.InnerDB.getter;
import static codesver.tannae.service.InnerDB.setter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.network.Network;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointActivity extends AppCompatActivity {

    private TextView textCurrent;
    private EditText editPoint;
    private Button buttonCharge, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
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
        SharedPreferences getter = getter(getApplicationContext());
        int point = getter.getInt("point", 0);
        textCurrent.setText(String.valueOf(point));

        if (point < 0) {
            editPoint.setText(String.valueOf(point * -1));
            Toaster.toast(PointActivity.this, "초과 포인트 사용 기록이 있습니다.\n충전해주시기 바랍니다.");
        }
    }

    private void charge() {
        SharedPreferences getter = getter(getApplicationContext());
        int point = getter.getInt("point", 0);
        String chargePointText = editPoint.getText().toString();
        int chargePoint = Integer.parseInt(chargePointText);

        if (chargePointText.length() == 0 || chargePoint == 0)
            Toaster.toast(PointActivity.this, "충전량을 입력하세요.");
        else if (point < 0 && -point > chargePoint)
            Toaster.toast(PointActivity.this, "초과 사용 포인트 이상을 충전해주시기 바랍니다.");
        else chargeByServer(point);
    }

    private void chargeByServer(int point) {
        SharedPreferences getter = getter(getApplicationContext());
        int usn = getter.getInt("usn", 0);
        Network.service.charge(usn, point).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean success = response.body();
                if (!success) return;
                Toaster.toast(PointActivity.this, "충전을 완료하였습니다.");
                updatePoint(point);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(PointActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void updatePoint(int point) {
        SharedPreferences.Editor setter = setter(getApplicationContext());
        SharedPreferences getter = getter(getApplicationContext());
        int newPoint = getter.getInt("point", 0) + point;

        setter.putInt("point", newPoint);
        setter.apply();

        textCurrent.setText(String.valueOf(newPoint));
        editPoint.setText("");
    }
}