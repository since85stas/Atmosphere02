package com.batura.stas.atmosphere;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private static final String TAG = CalculateActivity.class.toString();

    private static final int MACH_MODE = 0;
    private static final int VEL_MODE = 1;

    // это будут ключи файла настроек
    private static final String APP_PREFERENCES = "mysettings";
    public static final String VELOCITY_SPINNER_MODE = "Mode"; // положение переключателя
    public static final String NUMBER_OF_OPENS = "Opens"; // количество запусков приложения
    public static final String IS_RATED = "Rated" ;       // прошли ли по ссылке в маркет
    public static final int  numberOfOpensDel = 10;
    public int mNumberOfOpens ;
    public int mRated ;
    SharedPreferences mSettings;

    int mMachSpinnerMode = MACH_MODE;
    private Atmosphere atm;

    private TextView velocityTextView;

    private void loadPreferences() {
        mMachSpinnerMode = mSettings.getInt(VELOCITY_SPINNER_MODE,MACH_MODE);
        mNumberOfOpens   = mSettings.getInt(NUMBER_OF_OPENS,1);
        mRated           = mSettings.getInt(IS_RATED,0);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(VELOCITY_SPINNER_MODE, mMachSpinnerMode);
        editor.putInt(NUMBER_OF_OPENS, mNumberOfOpens);
        editor.putInt(IS_RATED, mRated);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        loadPreferences();

        setContentView(R.layout.calculate_activity);

        velocityTextView = findViewById(R.id.velocityTitle);
        setupMachSpinner();

        Button calculateButton = findViewById(R.id.calculateButtonIn);

        //Atmosphere atm;
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
                            Toast toast01 = Toast.makeText(getApplicationContext(),R.string.negat_altitude,Toast.LENGTH_SHORT);
                            toast01.show();
                        }
                        if (altitudeValue > 85) {
                            Toast toast01 = Toast.makeText(getApplicationContext(),R.string.high_altitude,Toast.LENGTH_SHORT);
                            toast01.show();
                        }
                        else{
                            atm = new Atmosphere(altitudeValue);
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
                }  else {
                    String altitudeStr = altitudeValueText.getText().toString();
                    double altitudeValue = Double.parseDouble(altitudeStr);
                    if (altitudeValue < 0) {
                        Toast toast01 = Toast.makeText(getApplicationContext(), R.string.negat_altitude, Toast.LENGTH_SHORT);
                        toast01.show();
                    } else if (altitudeValue > 85) {
                        Toast toast01 = Toast.makeText(getApplicationContext(), R.string.high_altitude, Toast.LENGTH_SHORT);
                        toast01.show();
                    } else {
                        atm = new Atmosphere(altitudeValue);
                        double machValue = -1;
                        if (mMachSpinnerMode == MACH_MODE) {
                            String machStr = machValueText.getText().toString();
                            machValue = Double.parseDouble(machStr);
                        } else if (mMachSpinnerMode == VEL_MODE) {
                            String velStr = machValueText.getText().toString();
                            double velValue = Double.parseDouble(velStr);
                            machValue = velValue / atm.getSonicSpeed();
                        }
                        if (machValue < 0) {
                            Toast toast01 = Toast.makeText(getApplicationContext(), R.string.negative_mach, Toast.LENGTH_SHORT);
                            toast01.show();
                        } else if (machValue > 21) {
                            Toast toast01 = Toast.makeText(getApplicationContext(), R.string.high_mach, Toast.LENGTH_SHORT);
                            toast01.show();
                        } else {
                            atm = new Atmosphere(altitudeValue);
                            double pressure = atm.getPressure();
                            double density = atm.getDensity();
                            double temperature = atm.getTempreture();
                            double sonicSpeed = atm.getSonicSpeed();

                            if (BuildConfig.DEBUG) {
                                Log.d(TAG, "atm pres " + pressure + density + temperature);
                            }
                            TextView pressureTextView = findViewById(R.id.pressureValue);
                            TextView densityTextView = findViewById(R.id.densityValue);
                            TextView temperatureTextView = findViewById(R.id.temperatureValue);
                            TextView sonicSpeedTextView = findViewById(R.id.sonicSpeedValue);

                            pressureTextView.setText(formatPresssure(pressure));
                            densityTextView.setText(formatDens(density));
                            temperatureTextView.setText(formatTemp(temperature));
                            sonicSpeedTextView.setText(formatSonic(sonicSpeed));

                            Atmosphere atmFull = new Atmosphere(altitudeValue, machValue);

                            TextView velocityValueText = findViewById(R.id.velociryValue);
                            if (mMachSpinnerMode == MACH_MODE) {
                                double velocity = atmFull.getmVelocity();
                                velocityValueText.setText(formatTemp(velocity));
                            }
                            if (mMachSpinnerMode == VEL_MODE) {
                                velocityValueText.setText(formatDens(machValue));
                            }

                            double fullPressure = atmFull.getFullPressure();
                            TextView fullPressureTextView = findViewById(R.id.fullPressureValue);
                            fullPressureTextView.setText(formatPresssure(fullPressure));

                            TextView fullTemperatureTextView = findViewById(R.id.fullTempValue);
                            double fullTemp = atmFull.getFullTempreture();
                            fullTemperatureTextView.setText(formatTemp(fullTemp));

                            TextView dynamicPressTextView = findViewById(R.id.dynamicPressValue);
                            double dynamicPress = density * (machValue * sonicSpeed) * (machValue * sonicSpeed);
                            dynamicPressTextView.setText(formatPresssure(dynamicPress));
                        }
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
       machSpinner.setSelection(mMachSpinnerMode);
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
               //mMachSpinnerMode = MACH_MODE;
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
            case (R.id.velocityHelp)  :
                helpIntent.putExtra("helpResources",R.string.velocityHelpString);
                startActivity(helpIntent);
                break;

        }
    }

    @Override
    protected void onPause() {
        savePreferences();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mNumberOfOpens++;  // после закрытия кол запусков увел на 1
        savePreferences();
        super.onStop();
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


