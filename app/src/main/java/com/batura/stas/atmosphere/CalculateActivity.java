package com.batura.stas.atmosphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CalculateActivity extends AppCompatActivity {

    private static final String TAG = "CalcClass";
    public final static String USER = "stas.batura.myapp.USER";

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
                        if (altitudeValue > 85) {
                            Toast toast01 = Toast.makeText(getApplicationContext(),"Maximum altitude exceeding",Toast.LENGTH_SHORT);
                            toast01.show();
                        }
                        else{
                            Atmosphere atm = new Atmosphere(altitudeValue);
                            double pressure = atm.getPressure();
                            double density  = atm.getDensity();
                            double temperature = atm.getTempreture();
                            double sonicSpeed = atm.getSonicSpeed();
                            if (BuildConfig.DEBUG) {   Log.d(TAG, "atm pres " +pressure+density+temperature);}
                            TextView pressureTextView = findViewById(R.id.pressureValue);
                            TextView densityTextView = findViewById(R.id.densityValue);
                            TextView temperatureTextView = findViewById(R.id.temperatureValue);
                            TextView sonicSpeedTextView = findViewById(R.id.sonicSpeedValue);

//                            String ss = formatPresssure(pressure);

                            pressureTextView.setText(formatPresssure(pressure));
                            densityTextView.setText(formatDens(density));
                            temperatureTextView.setText(formatTemp(temperature));
                            sonicSpeedTextView.setText(formatSonic(sonicSpeed));
                        }
                    }
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

                        pressureTextView.setText(formatPresssure(pressure));
                        densityTextView.setText(formatDens(density));
                        temperatureTextView.setText(formatTemp(temperature));

                        Atmosphere atmFull = new Atmosphere(altitudeValue,machValue);
                        double fullPressure = atmFull.getFullPressure();
                        TextView fullPressureTextView = findViewById(R.id.fullPressureValue);
                        fullPressureTextView.setText(formatPresssure(fullPressure));

                        TextView fullTemperatureTextView = findViewById(R.id.fullTempValue);
                        double fullTemp = atmFull.getFullTempreture();
                        fullTemperatureTextView.setText(formatPresssure(fullTemp));
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculate,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.menuCalculateSelectGraph:
                Intent graphIntent = new Intent(CalculateActivity.this,GraphActivity.class);
                startActivity(graphIntent);
                return true;
            case R.id.menuCalculateSelectAbout:
                Intent aboutIntent = new Intent(CalculateActivity.this,AboutActivity.class);
                startActivity(aboutIntent);
                return true;
//            case R.id.menuCalculateExit:
//                Intent closeIntent = new Intent(CalculateActivity.this,CalculateActivity.class);
//                closeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
            case (R.id.sonicSpeedHelp)  :
                helpIntent.putExtra("helpResources",R.string.fullTemperatureHelpString);
                startActivity(helpIntent);
                break;

        }
    }

    private String formatPresssure (double press) {
        DecimalFormat magnitudeFormat = new DecimalFormat("000.000");
        return magnitudeFormat.format(press);
    }

    private String formatTemp (double temp) {
        DecimalFormat magnitudeFormat = new DecimalFormat("000.00000");
        return magnitudeFormat.format(temp);
    }

    private String formatDens (double dens) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0000000");
        return magnitudeFormat.format(dens);
    }

    private String formatSonic (double temp) {
        DecimalFormat magnitudeFormat = new DecimalFormat("000.00000");
        return magnitudeFormat.format(temp);
    }


}


