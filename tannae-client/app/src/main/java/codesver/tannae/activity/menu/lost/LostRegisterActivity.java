package codesver.tannae.activity.menu.lost;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.RegisterLostDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostRegisterActivity extends AppCompatActivity {

    private EditText editLost;

    private SharedPreferences getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_register);

        getter = InnerDB.getter(getApplicationContext());

        setViews();
    }

    private void setViews() {
        editLost = findViewById(R.id.edit_lost_lost_register);
        findViewById(R.id.button_back_lost_register).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.button_register_lost_register).setOnClickListener(v -> registerByServer());
    }

    private void registerByServer() {
        Network.service.postLost(new RegisterLostDTO(editLost.getText().toString(), getter.getInt("vsn", 0))).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean respond = response.body();
                Toaster.toast(getApplicationContext(), Boolean.TRUE.equals(respond) ? "분실물이 등록되었습니다." : "분실물 등록이 되지 않았습니다.");
                onBackPressed();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}