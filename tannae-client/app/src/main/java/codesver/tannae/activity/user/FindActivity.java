package codesver.tannae.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.dto.FoundAccountDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindActivity extends AppCompatActivity {

    private EditText editName, editRrnFront, editRrnBack;
    private TextView textPrivateState, textIdFound, textPwFound;
    private Button buttonCheckPrivate, buttonBack;

    private boolean privateAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        setViews();
        setEventListeners();
    }

    private void setViews() {
        editName = findViewById(R.id.edit_name_find);
        editRrnFront = findViewById(R.id.edit_rrn_front_find);
        editRrnBack = findViewById(R.id.edit_rrn_back_find);

        textPrivateState = findViewById(R.id.text_private_find);
        textIdFound = findViewById(R.id.text_id_found_find);
        textPwFound = findViewById(R.id.text_pw_found_find);

        buttonCheckPrivate = findViewById(R.id.button_check_user_find);
        buttonBack = findViewById(R.id.button_back_find);
    }

    private void setEventListeners() {
        editName.addTextChangedListener(privateChecker());
        editRrnFront.addTextChangedListener(privateChecker());
        editRrnBack.addTextChangedListener(privateChecker());

        buttonCheckPrivate.setOnClickListener(v -> checkUser());
        buttonBack.setOnClickListener(v -> onBackPressed());
    }

    private TextWatcher privateChecker() {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                privateAvailable = false;
                if (editName.getText().toString().length() == 0) {
                    textPrivateState.setText("????????? ???????????????.");
                    textPrivateState.setTextColor(0xAAFF0000);
                } else if (editRrnFront.getText().toString().length() != 6) {
                    textPrivateState.setText("?????????????????? ???????????? ???????????? ???????????????.");
                    textPrivateState.setTextColor(0xAAFF0000);
                } else if (editRrnBack.getText().toString().length() != 7) {
                    textPrivateState.setText("?????????????????? ???????????? ???????????? ???????????????.");
                    textPrivateState.setTextColor(0xAAFF0000);
                } else {
                    textPrivateState.setText("??????????????? ?????????.");
                    textPrivateState.setTextColor(0xAA000000);
                    privateAvailable = true;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void checkUser() {
        String name = editName.getText().toString();
        String rrnFront = editRrnFront.getText().toString();
        String rrnBack = editRrnBack.getText().toString();

        if (!privateAvailable) {
            textPrivateState.setText("??????????????? ???????????? ???????????????.");
            textPrivateState.setTextColor(0xAAFF0000);
            Toaster.toast(getApplicationContext(), "??????????????? ???????????? ???????????????.");
        } else findAccountByServer(name, rrnFront + "-" + rrnBack);
    }

    private void findAccountByServer(String name, String rrn) {
        Network.service.getAccount(name, rrn).enqueue(new Callback<FoundAccountDTO>() {
            @Override
            public void onResponse(Call<FoundAccountDTO> call, Response<FoundAccountDTO> response) {
                FoundAccountDTO account = response.body();

                if (account.isFound()) {
                    Toaster.toast(getApplicationContext(), "?????? ????????? ???????????? ????????? ???????????????.");
                    textIdFound.setText(account.getId());
                    textPwFound.setText(account.getPw());
                    textPrivateState.setText("????????? ???????????????.");
                    textPrivateState.setTextColor(0xAA0000FF);
                } else {
                    Toaster.toast(getApplicationContext(), "???????????? ????????? ????????????.");
                    textIdFound.setText("");
                    textPwFound.setText("");
                    textPrivateState.setText("???????????? ????????? ????????????.");
                    textPrivateState.setTextColor(0xAAFF0000);
                }
            }

            @Override
            public void onFailure(Call<FoundAccountDTO> call, Throwable t) {
                Toaster.toast(getApplicationContext(), "????????? ??????????????????.\n??????????????? ??????????????????.");
            }
        });
    }
}