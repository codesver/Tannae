package codesver.tannae.activity.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.activity.main.MainActivity;
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
    }

    private void getReceiptInfoByServer() {
        Network.service.getReceipt(InnerDB.getter(getApplicationContext()).getInt("usn", 0)).enqueue(new Callback<History>() {
            @Override
            public void onResponse(Call<History> call, Response<History> response) {
                History history = response.body();
                assert history != null;
                setViews(history);
                setEventListeners(history);
            }

            @Override
            public void onFailure(Call<History> call, Throwable t) {
                Toaster.toast(ReceiptActivity.this, "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void setViews(History history) {
        SharedPreferences getter = InnerDB.getter(getApplicationContext());
        SharedPreferences.Editor setter = InnerDB.setter(getApplicationContext());
        setter.putInt("point", getter.getInt("point", 0) - history.getRealFare());

        buttonBack = findViewById(R.id.button_back_receipt);
        buttonCheckOrigin = findViewById(R.id.button_check_origin_receipt);
        buttonCheckDestination = findViewById(R.id.button_check_destination_receipt);
        buttonEvaluate = findViewById(R.id.button_evaluate_receipt);

        ((TextView) findViewById(R.id.text_hsn_receipt)).setText(history.getHsn());
        ((TextView) findViewById(R.id.text_date_receipt)).setText(history.getRequestTime().substring(0, 10));
        ((TextView) findViewById(R.id.text_request_time_receipt)).setText(history.getRequestTime().substring(11));
        ((TextView) findViewById(R.id.text_boarding_time_receipt)).setText(history.getBoardingTime().substring(11));
        ((TextView) findViewById(R.id.text_arrival_time_receipt)).setText(history.getArrivalTime().substring(11));
        ((TextView) findViewById(R.id.text_origin_receipt)).setText(history.getOrigin());
        ((TextView) findViewById(R.id.text_destination_receipt)).setText(history.getDestination());
        ((TextView) findViewById(R.id.text_original_distance_receipt)).setText(history.getOriginalDistance());
        ((TextView) findViewById(R.id.text_original_duration_receipt)).setText(history.getOriginalDuration());
        ((TextView) findViewById(R.id.text_original_fare_receipt)).setText(history.getOriginalFare());
        ((TextView) findViewById(R.id.text_real_distance_receipt)).setText(history.getRealDistance());
        ((TextView) findViewById(R.id.text_real_duration_receipt)).setText(history.getRealDuration());
        ((TextView) findViewById(R.id.text_real_fare_receipt)).setText(history.getRealFare());
        ((TextView) findViewById(R.id.text_fare_receipt)).setText(history.getRealFare());
        ((TextView) findViewById(R.id.text_sale_ratio_receipt)).setText((int) (100 - history.getRealFare() / (double) history.getOriginalFare() * 100));
        ((TextView) findViewById(R.id.text_left_point_receipt)).setText(getter.getInt("point", 0));
    }

    private void setEventListeners(History history) {
    }
}