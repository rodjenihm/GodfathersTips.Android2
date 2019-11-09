package com.rodjenihm.godfatherstips.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.model.Tip;

import java.util.Date;

public class AddTipActivity extends AppCompatActivity {
    private EditText rivalsView ;
    private EditText timeView ;
    private EditText tipView;
    private EditText oddsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        rivalsView = findViewById(R.id.rivals);
        timeView = findViewById(R.id.time);
        tipView = findViewById(R.id.tip);
        oddsView = findViewById(R.id.odds);

        Button buttonAddTip = findViewById(R.id.button_add_tip);
        buttonAddTip.setOnClickListener(v -> {
            if (validateAddTipFormData()) {
                addTip(rivalsView.getText().toString(), timeView.getText().toString(), tipView.getText().toString(), oddsView.getText().toString());
            }
        });
    }

    private boolean validateAddTipFormData() {
        String rivals = rivalsView.getText().toString();
        String time = timeView.getText().toString();
        String tip = tipView.getText().toString();
        String odds = oddsView.getText().toString();

        boolean isRivalsEmpty = rivals.trim().length() <= 0;
        boolean isTimeEmpty = time.trim().length() <= 0;
        boolean isTipEmpty = tip.trim().length() <= 0;
        boolean isOddsEmpty = odds.trim().length() <= 0;

        if (isRivalsEmpty) {
            rivalsView.setError("Please enter rivals");
        } else {
            rivalsView.setError(null);
        }

        if (isTimeEmpty) {
            timeView.setError("Please enter match time");
        } else {
            timeView.setError(null);
        }

        if (isTipEmpty) {
            tipView.setError("Please enter tip");
        } else {
            tipView.setError(null);
        }

        if (isOddsEmpty) {
            oddsView.setError("Please enter odds");
        } else {
            oddsView.setError(null);
        }

        return !isRivalsEmpty && !isTimeEmpty && !isTipEmpty && !isOddsEmpty;
    }

    private void addTip(String rivals, String time, String tip, String odds) {
        Tip obj = new Tip();
        obj.setTipId(String.valueOf((DateFormat.format("yyyy-MM-dd-HH-mm-ss", new Date()))));
        obj.setRivals(rivals);
        obj.setTime(time);
        obj.setTip(tip);
        obj.setOdds(odds);
        obj.setStatus(1);

        FirebaseFirestore.getInstance()
                .collection("tips")
                .add(obj)
                .addOnSuccessListener(documentReference -> {
                    clearAddTipFormData();
                    Toast.makeText(this, R.string.tip_successfully_added, Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show());
    }

    private void clearAddTipFormData() {
        rivalsView.getText().clear();
        timeView.getText().clear();
        tipView.getText().clear();
        oddsView.getText().clear();
    }
}
