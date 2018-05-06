package com.batura.stas.atmosphere;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.batura.stas.atmosphere.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Stas Batura on 02.04.2018.
 */

public class GraphActivity extends AppCompatActivity{

    private final int PRESSURE_CASE = 1;
    private final int DENSITY_CASE = 2;
    private final int TEMPERTAURE_CASE = 3;
    private int       currentCase = 1;
    private double leftEdge;
    private double rightEdge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);

        RadioButton pressureRadioButton = (RadioButton)findViewById(R.id.pressureRadioButton);
        pressureRadioButton.setOnClickListener(radioButtonClickListner);
        RadioButton densityRadioButton = (RadioButton)findViewById(R.id.densityRadioButton);
        densityRadioButton.setOnClickListener(radioButtonClickListner);
        RadioButton temperatureRadioButton = (RadioButton)findViewById(R.id.temperatureRadioButton);
        temperatureRadioButton.setOnClickListener(radioButtonClickListner);

        leftEdge = 0;       // default Values in Km
        rightEdge = 50;

        Button okButton =(Button)findViewById(R.id.graphOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText leftEdgeValue = (EditText) findViewById(R.id.graphEditText1);
                EditText rightEdgeValue = (EditText) findViewById(R.id.graphEditText2);

                if( (leftEdgeValue.getText().length()!=0)&&(rightEdgeValue.getText().length()!=0)){

                    String leftEdgeString = leftEdgeValue.getText().toString();
                    String rightEdgeString = rightEdgeValue.getText().toString();
                    leftEdge = Double.parseDouble(leftEdgeString);
                    rightEdge = Double.parseDouble(rightEdgeString);
                    if (leftEdge>85) {
                        leftEdge = 85;
                        Toast toast01 = Toast.makeText(getApplicationContext(),"Maximum altitude exceeding",Toast.LENGTH_SHORT);
                        toast01.show();
                    }
                    if (rightEdge>85) {
                        rightEdge = 85;
                        Toast toast01 = Toast.makeText(getApplicationContext(),"Maximum altitude exceeding",Toast.LENGTH_SHORT);
                        toast01.show();
                    }
                    if (leftEdge > rightEdge) {
                        Toast toast01 = Toast.makeText(getApplicationContext(), "Wrong edge values", Toast.LENGTH_SHORT);
                        toast01.show();
                    }
                }
                else {
                    Toast toast01 = Toast.makeText(getApplicationContext(),"Default values was taken",Toast.LENGTH_SHORT);
                    toast01.show();
                }
                GraphView testGraph = (GraphView) findViewById(R.id.graph);
                testGraph.removeAllSeries();
                testGraph.getGridLabelRenderer().setHorizontalAxisTitle("Altitude, Кm");
                switch (currentCase){
                    case (PRESSURE_CASE):
                        //testGraph.getGridLabelRenderer().setVerticalAxisTitle("Pressure");
                        break;
                    case (DENSITY_CASE):
                        //testGraph.getGridLabelRenderer().setVerticalAxisTitle("Density");
                        break;
                    case (TEMPERTAURE_CASE):
                        //testGraph.getGridLabelRenderer().setVerticalAxisTitle("Temperature, K");
                        break;
                }

                LineGraphSeries <DataPoint> series = new LineGraphSeries<>(data(leftEdge,rightEdge,currentCase) );
                testGraph.addSeries(series);

                testGraph.getViewport().setScalable(true);
                testGraph.getViewport().setScalableY(true);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_graph,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.menuGraphSelectCalculate:
                Intent graphIntent = new Intent(GraphActivity.this,CalculateActivity.class);
                startActivity(graphIntent);
                return true;
            case R.id.menuGraphSelectAbout:
                Intent aboutIntent = new Intent(GraphActivity.this,AboutActivity.class);
                startActivity(aboutIntent);
                return true;
//            case R.id.menuGraphExit:
//                Intent closeIntent = new Intent(GraphActivity.this,GraphActivity.class);
//                closeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        }

    View.OnClickListener radioButtonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch(rb.getId()) {
                case(R.id.pressureRadioButton):
                    currentCase = 1;
                    break;
                case(R.id.densityRadioButton):
                    currentCase = 2;
                    break;
                case(R.id.temperatureRadioButton):
                    currentCase = 3;
                    break;
            }
        }
    };

    public DataPoint[] data(double leftEdge, double rightEdge, int paramCasein){
        final int numPoints = 500;
        DataPoint[] values = new DataPoint[numPoints];     //creating an object of type DataPoint[] of size 'n'
        DataPoint point1 = new DataPoint(0,0);
        for (int i=0;i<numPoints;i++){
            //DataPoint point1;
            double deltaX = (rightEdge-leftEdge)/numPoints;
            double xCoordinate = leftEdge + i*deltaX;
            Atmosphere atm = new Atmosphere(xCoordinate);
            switch (paramCasein){
                case(PRESSURE_CASE):
                    point1 = new DataPoint(xCoordinate,atm.getPressure());
                    break;
                case(DENSITY_CASE):
                    point1 = new DataPoint(xCoordinate,atm.getDensity());
                    break;
                case(TEMPERTAURE_CASE):
                    point1 = new DataPoint(xCoordinate,atm.getTempreture());
                    break;
            }
            values[i] = point1;
        }
        return values;
    }

    public void graphHelpOnClick(View view) {
        Intent helpIntent = new Intent(GraphActivity.this,HelpActivity.class);
        helpIntent.putExtra("helpResources",R.string.graphHelpString);
        startActivity(helpIntent);
    }
}
