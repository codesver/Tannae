package codesver.tannae.activity.menu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.domain.History;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptActivity extends AppCompatActivity {

    private Button buttonBack, buttonCheckOrigin, buttonCheckDestination, buttonEvaluate;
    private TextView textHsn, textDate, textRequestTime, textBoardTime, textArrivalTime, textOrigin, textDestination;
    private TextView textOriginalDistance, textOriginalDuration, textOriginalFare;
    private TextView textRealDistance, textRealDuration, textRealFare;
    private TextView textFare, textSaleRatio, textLeftPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        getReceiptInfoByServer();
        setViews();
        setEventListeners();
    }

    private void getReceiptInfoByServer() {
        Network.service.getReceipt(InnerDB.getter(getApplicationContext()).getInt("usn", 0)).enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {

            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                Toaster.toast(ReceiptActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void setViews() {

    }

    private void setEventListeners() {

    }
}