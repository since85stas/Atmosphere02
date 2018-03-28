package com.batura.stas.atmosphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //open main activity

        //Atmosphere atm = new Atmosphere();
        //double cpTest = atm.findEnthalpy(300);
        TextView calculateTextView = (TextView) findViewById(R.id.calculate_text);
        calculateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calculateIntent = new Intent(MainActivity.this,CalculateActivity.class);
                startActivity(calculateIntent);
            }
        });
        //Atmosphere atm = new Atmosphere();
        //atm.findCp(300);
    }
}


