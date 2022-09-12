package codesver.tannae.activity.menu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import codesver.tannae.R;

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
        setViews();
        setEventListeners();
    }

    private void setViews() {

    }

    private void setEventListeners() {

    }
}