package codesver.tannae.activity.menu.lost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import codesver.tannae.R;
import codesver.tannae.dto.LostDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.ListViewAdapter;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter<LostDTO> adapter;

    private SharedPreferences getter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);

        getter = InnerDB.getter(getApplicationContext());

        listView = findViewById(R.id.list_view_losts_lost);
        setViews();
        setAdapter();
    }

    private void setViews() {
        findViewById(R.id.button_back_lost).setOnClickListener(v -> onBackPressed());

        Button registerButton = (Button) findViewById(R.id.button_register_lost_lost);
        int vsn = getter.getInt("vsn", -1);
        if (vsn == -1) registerButton.setVisibility(View.GONE);
        else registerButton.setOnClickListener(v -> startActivity(new Intent(LostActivity.this, LostRegisterActivity.class)));
    }

    private void setAdapter() {
        Network.service.getLosts().enqueue(new Callback<List<LostDTO>>() {
            @Override
            public void onResponse(Call<List<LostDTO>> call, Response<List<LostDTO>> response) {
                List<LostDTO> losts = response.body();
                if (losts.isEmpty()) {
                    Toaster.toast(getApplicationContext(), "분실물이 없습니다.");
                } else {
                    adapter = new ListViewAdapter<>();
                    for (LostDTO lost : losts) adapter.addItem(lost);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<LostDTO>> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }
}