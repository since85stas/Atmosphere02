package com.batura.stas.atmosphere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalculateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_activity);

        Button calculateButton = findViewById(R.id.calculateButtonIn);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText altitudeValueText = findViewById(R.id.altitudeValue);
                EditText machValueText     = findViewById(R.id.machValue);
                if (machValueText.getText().length() == 0) {
                    if (altitudeValueText.getText().length() == 0) {
                        Toast toast01 = Toast.makeText(getApplicationContext(),"No input parameters",Toast.LENGTH_SHORT);
                        toast01.show();
                    }
                }

            }
        });
    }
}
