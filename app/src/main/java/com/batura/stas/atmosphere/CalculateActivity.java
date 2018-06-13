package com.batura.stas.atmosphere;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.R.layout.simple_spinner_item;
import static com.batura.stas.atmosphere.R.layout.support_simple_spinner_dropdown_item;

public class CalculateActivity extends AppCompatActivity {

    private static final String TAG = "CalcClass";
    public final static String USER = "stas.batura.myapp.USER";

    int mMachSpinnerMode = MACH_MODE;

    private TextView velocityTextView;

    private static final int MACH_MODE = 0;
    private static final int VEL_MODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_activity);

        velocityTextView = findViewById(R.id.velocityTitle);
        setupMachSpinner();

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
                        double sonicSpeed = atm.getSonicSpeed();

                        if (BuildConfig.DEBUG) {   Log.d(TAG, "atm pres " +pressure+density+temperature);}
                        TextView pressureTextView = findViewById(R.id.pressureValue);
                        TextView densityTextView = findViewById(R.id.densityValue);
                        TextView temperatureTextView = findViewById(R.id.temperatureValue);
                        TextView sonicSpeedTextView = findViewById(R.id.sonicSpeedValue);

                        pressureTextView.setText(formatPresssure(pressure));
                        densityTextView.setText(formatDens(density));
                        temperatureTextView.setText(formatTemp(temperature));
                        sonicSpeedTextView.setText(formatSonic(sonicSpeed));

                        Atmosphere atmFull = new Atmosphere(altitudeValue,machValue);

                        double velocity = atmFull.getmVelocity();

                        velocityTextView.setText(formatTemp(velocity));

                        double fullPressure = atmFull.getFullPressure();
                        TextView fullPressureTextView = findViewById(R.id.fullPressureValue);
                        fullPressureTextView.setText(formatPresssure(fullPressure));

                        TextView fullTemperatureTextView = findViewById(R.id.fullTempValue);
                        double fullTemp = atmFull.getFullTempreture();
                        fullTemperatureTextView.setText(formatTemp(fullTemp));

                        TextView dynamicPressTextView = findViewById(R.id.dynamicPressValue);
                        double dynamicPress = density * (machValue*sonicSpeed)*(machValue*sonicSpeed);
                        dynamicPressTextView.setText(formatPresssure(dynamicPress));
                    }
                }
            }
        });
    }

    private void setupMachSpinner() {

       Spinner machSpinner = findViewById(R.id.machSpinner);
       ArrayAdapter machAdapter = ArrayAdapter.createFromResource( this, R.array.mach_options,R.layout.mach_spinner_item );
       machAdapter.setDropDownViewResource(R.layout.mach_spinner_item);
       machSpinner.setAdapter(machAdapter);
       machSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
               String selection = (String) parent.getItemAtPosition(position);
               if (!TextUtils.isEmpty(selection)) {
                   if (selection.equals(getString(R.string.machTextView))) {
                       mMachSpinnerMode = MACH_MODE;
                       //mGender = PetContract.PetEntry.GENDER_MALE; // Male
                   } else if (selection.equals(getString(R.string.velocitySpinner))) {
                       mMachSpinnerMode = VEL_MODE;
                       //mGender = PetContract.PetEntry.GENDER_FEMALE; // Female
                   }
                   if (mMachSpinnerMode == VEL_MODE) {
                       velocityTextView.setText(R.string.machTextView);
                   }
                   if (mMachSpinnerMode == MACH_MODE) {
                       velocityTextView.setText(R.string.velocityTextView);
                   }
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
               mMachSpinnerMode = MACH_MODE;
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
            case (R.id.dynamicPressHelp)  :
                helpIntent.putExtra("helpResources",R.string.dynamicPressHelp);
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


