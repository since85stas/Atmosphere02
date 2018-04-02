package com.batura.stas.atmosphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateActivity extends AppCompatActivity {

    private static final String TAG = "CalcClass";
    public final static String USER = "stasbatura.myapp.USER";

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
                else {
                    String machStr = machValueText.getText().toString();
                    double machValue = Double.parseDouble(machStr);
                    String altitudeStr = altitudeValueText.getText().toString();
                    double altitudeValue = Double.parseDouble(altitudeStr);
                    if(machValue < 0) {
                        Toast toast01 = Toast.makeText(getApplicationContext(),"Wrong Mach number value",Toast.LENGTH_SHORT);
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

                        Atmosphere atmFull = new Atmosphere(altitudeValue,machValue);
                        double fullPressure = atmFull.getFullPressure();
                        TextView fullPressureTextView = findViewById(R.id.fullPressureValue);

                        fullPressureTextView.setText((String.valueOf(fullPressure)));

                    }

                }

            }
        });
    }

    public void calculateHelpOnClick(View view) {
        Intent helpIntent = new Intent(CalculateActivity.this,HelpActivity.class);
        switch (view.getId()){
            case(R.id.altitudeHelp)   :
                helpIntent.putExtra("helpResources",R.string.altitudeHelpString);
                startActivity(helpIntent);
                break;
            case(R.id.machHelp)       :
                helpIntent.putExtra("helpResources",R.string.machHelpString);
                startActivity(helpIntent);
                break;
            case(R.id.pressureHelp)   :
                helpIntent.putExtra("helpResources",R.string.pressureHelpString);
                startActivity(helpIntent);
                break;
            case(R.id.temperatureHelp):
                helpIntent.putExtra("helpResources",R.string.temperatureHelpString);
                startActivity(helpIntent);
                break;
            case(R.id.densityHelp)    :
                helpIntent.putExtra("helpResources",R.string.densityHelpString);
                startActivity(helpIntent);
                break;
            case (R.id.fullPressureHelp):
                helpIntent.putExtra("helpResources",R.string.fullPressureHelpString);
                startActivity(helpIntent);
                break;
            case (R.id.fullTempHelp)  :
                helpIntent.putExtra("helpResources",R.string.fullTemperatureHelpString);
                startActivity(helpIntent);
                break;

        }
    }
}
