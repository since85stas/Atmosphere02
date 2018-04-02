package com.batura.stas.atmosphere;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by seeyou85 on 30.03.2018.
 */

public class HelpActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        int helpResourseId = getIntent().getIntExtra("helpResources", 0);
        TextView helpOut = (TextView) findViewById(R.id.helpContainer);
        helpOut.setText(helpResourseId);
    }
}
