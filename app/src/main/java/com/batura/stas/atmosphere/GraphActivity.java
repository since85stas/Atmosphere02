package com.batura.stas.atmosphere;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by seeyou on 02.04.2018.
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

        leftEdge = 1;       // default Values in Km
        rightEdge = 10;

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
                testGraph.getGridLabelRenderer().setHorizontalAxisTitle("Altitude, m");
                switch (currentCase){
                    case (PRESSURE_CASE):
                        testGraph.getGridLabelRenderer().setVerticalAxisTitle("Pressure");
                        break;
                    case (DENSITY_CASE):
                        testGraph.getGridLabelRenderer().setVerticalAxisTitle("Density");
                        break;
                    case (TEMPERTAURE_CASE):
                        testGraph.getGridLabelRenderer().setVerticalAxisTitle("Temperature, K");
                        break;
                }
                // value getting in function in m , so *1000
                LineGraphSeries <DataPoint> series = new LineGraphSeries<>(data(leftEdge*1000,rightEdge*1000,currentCase) );
                testGraph.addSeries(series);
            }
        });
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
        final int numPoints = 30;
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

}
