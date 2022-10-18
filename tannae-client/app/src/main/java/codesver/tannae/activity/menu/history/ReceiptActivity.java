package codesver.tannae.activity.menu.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;
import codesver.tannae.activity.main.MainActivity;
import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.network.Network;
import codesver.tannae.service.InnerDB;
import codesver.tannae.service.Toaster;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptActivity extends AppCompatActivity {

    private Button buttonBack, buttonCheckOrigin, buttonCheckDestination, buttonEvaluate;
    private RatingBar ratingEvaluate;

    private SharedPreferences getter;
    private SharedPreferences.Editor setter;

    private int hsn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        getter = InnerDB.getter(getApplicationContext());
        setter = InnerDB.setter(getApplicationContext());

        hsn = getIntent().getIntExtra("hsn", 0);
        if (hsn == 0) getReceiptInfoByServerWithUsn(getter.getInt("usn", 0));
        else getReceiptInfoByServerWithHsn(hsn);
    }

    private void getReceiptInfoByServerWithUsn(int usn) {
        Network.service.getReceiptWithUsn(usn).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HistoryDTO> call, @NonNull Response<HistoryDTO> response) {
                drawReceipt(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<HistoryDTO> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void getReceiptInfoByServerWithHsn(int hsn) {
        Network.service.getReceiptWithHsn(hsn).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HistoryDTO> call, @NonNull Response<HistoryDTO> response) {
                drawReceipt(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<HistoryDTO> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    private void drawReceipt(HistoryDTO history) {
        setViews(history);
        setEventListeners(history);
    }

    private void setViews(HistoryDTO history) {
        int currentPoint = getter.getInt("point", 0);
        int point = hsn == 0 ? currentPoint - history.getRealFare() : currentPoint;
        setter.putInt("point", point).apply();
        if (point < 0)
            Toaster.toast(getApplicationContext(), "포인트가 초과 사용되었습니다.\n충전하기 전까지 사용이 중지됩니다.");
        setButtons();
        setTextViews(history, point);
    }

    private void setButtons() {
        buttonBack = findViewById(R.id.button_back_receipt);
        buttonCheckOrigin = findViewById(R.id.button_check_origin_receipt);
        buttonCheckDestination = findViewById(R.id.button_check_destination_receipt);
        (buttonEvaluate = findViewById(R.id.button_evaluate_receipt)).setVisibility(hsn == 0 ? View.VISIBLE : View.GONE);
        (ratingEvaluate = findViewById(R.id.rating_evaluate_receipt)).setVisibility(hsn == 0 ? View.VISIBLE : View.GONE);
    }

    @SuppressLint("SetTextI18n")
    private void setTextViews(HistoryDTO history, int point) {
        ((TextView) findViewById(R.id.text_hsn_receipt)).setText("RECEIPT " + history.getHsn());
        ((TextView) findViewById(R.id.text_date_receipt)).setText(history.getRequestTime().substring(0, 10));
        ((TextView) findViewById(R.id.text_request_time_receipt)).setText(history.getRequestTime().substring(11));
        ((TextView) findViewById(R.id.text_boarding_time_receipt)).setText(history.getBoardingTime().substring(11));
        ((TextView) findViewById(R.id.text_arrival_time_receipt)).setText(history.getArrivalTime().substring(11));
        ((TextView) findViewById(R.id.text_origin_receipt)).setText(history.getOrigin());
        ((TextView) findViewById(R.id.text_destination_receipt)).setText(history.getDestination());
        ((TextView) findViewById(R.id.text_original_distance_receipt)).setText(history.getOriginalDistance() / 1000.0 + "km");
        ((TextView) findViewById(R.id.text_original_duration_receipt)).setText((int) (history.getOriginalDuration() / 60.0) + "분");
        ((TextView) findViewById(R.id.text_original_fare_receipt)).setText(history.getOriginalFare() + "p");
        ((TextView) findViewById(R.id.text_real_distance_receipt)).setText(history.getRealDistance() / 1000.0 + "km");
        ((TextView) findViewById(R.id.text_real_duration_receipt)).setText((int) (history.getRealDuration() / 60.0) + "분");
        ((TextView) findViewById(R.id.text_real_fare_receipt)).setText(history.getRealFare() + "p");
        ((TextView) findViewById(R.id.text_fare_receipt)).setText(history.getRealFare() + "p");
        ((TextView) findViewById(R.id.text_sale_ratio_receipt)).setText((int) (100 - history.getRealFare() / (double) history.getOriginalFare() * 100) + "%");
        if (hsn == 0) {
            ((TextView) findViewById(R.id.text_left_point_receipt)).setText(point + "p");
        } else {
            findViewById(R.id.text_left_point_receipt).setVisibility(View.GONE);
            findViewById(R.id.text_left_point_label_receipt).setVisibility(View.GONE);
        }
    }

    private void setEventListeners(HistoryDTO history) {
        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonCheckOrigin.setOnClickListener(v -> checkPoint(history.getOriginLatitude(), history.getOriginLongitude()));
        buttonCheckDestination.setOnClickListener(v -> checkPoint(history.getDestinationLatitude(), history.getDestinationLongitude()));
        buttonEvaluate.setOnClickListener(v -> evaluateByServer(history.getVsn()));
    }

    private void checkPoint(double latitude, double longitude) {
        startActivity(new Intent(ReceiptActivity.this, MapActivity.class)
                .putExtra("latitude", latitude)
                .putExtra("longitude", longitude));
    }

    private void evaluateByServer(Integer vsn) {
        Network.service.patchUserScore(vsn, ratingEvaluate.getRating()).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                Toaster.toast(getApplicationContext(), "평가가 완료되었습니다.");
                buttonEvaluate.setVisibility(View.GONE);
                ratingEvaluate.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Toaster.toast(getApplicationContext(), "오류가 발생했습니다.\n고객센터로 문의바랍니다.");
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (hsn == 0)
            startActivity(new Intent(ReceiptActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        else
            super.onBackPressed();
    }
}