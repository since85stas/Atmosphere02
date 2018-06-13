package com.batura.stas.atmosphere;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

/**
 * Created by seeyo on 25.04.2018.
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        RatingBar ratingBar =(RatingBar) findViewById(R.id.ratingBar);
        Button    rateButton = findViewById(R.id.button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.batura.stas.atmosphere"));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.menuAboutSelectCalculate:
                Intent graphIntent = new Intent(AboutActivity.this,CalculateActivity.class);
                startActivity(graphIntent);
                return true;
            case R.id.menuAboutSelectGraph:
                Intent calcIntent = new Intent(AboutActivity.this,GraphActivity.class);
                startActivity(calcIntent);
                return true;
//            case R.id.menuAboutExit:
//                Intent closeIntent = new Intent(AboutActivity.this,AboutActivity.class);
//                closeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
