package com.batura.stas.atmosphere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateActivity extends AppCompatActivity {

    private static final String TAG = "CalcClass";

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
                    else{
                        String altitudeStr = altitudeValueText.getText().toString();
                        double altitudeValue = Double.parseDouble(altitudeStr);
                        if (altitudeValue < 0) {
                            Toast toast01 = Toast.makeText(getApplicationContext(),"Wrong altitiude value",Toast.LENGTH_SHORT);
                            toast01.show();
                        }
                        if (altitudeValue > 90000) {
                            Toast toast01 = Toast.makeText(getApplicationContext(),"Maximum altitude exceeding",Toast.LENGTH_SHORT);
                            toast01.show();
                        }
                        else{
                            Atmosphere atm = new Atmosphere(altitudeValue);
                            double pressure = atm.getPressure();
                            double density  = atm.getDensity();
                            double temperature = atm.getTempreture();
                            if (BuildConfig.DEBUG) {   Log.d(TAG, "atm pres " +pressure+density+temperature);}
                            TextView pressureTextView = findViewById(R.id.pressureValue);
                            TextView densityTextView = findViewById(R.id.densityValue);
                            TextView temperatureTextView = findViewById(R.id.temperatureValue);

                            pressureTextView.setText(String.valueOf(pressure));
                            densityTextView.setText(String.valueOf(density));
                            temperatureTextView.setText(String.valueOf(temperature));
                        }
                    }
                    //if( Double. )
                }

            }
        });
    }
}
