package com.batura.stas.atmosphere;
import android.util.Log ;
import  java.lang.Math;


/**
 * Created by seeyou85@gmail.com on 20.03.2018.
 * Class for common information about atosphere properties
 */

public class Atmosphere  {
    private double mHeight;
    private double mMachNumber;
    private double mPressure;
    private double mTempreture;
    private double mDensity;
    private static final String TAG = "AtmosphereClass";
    private final int nInter = 1000; //


    public Atmosphere() {
    }

    public Atmosphere (double height, double machNumber){
        mHeight = height;
        mMachNumber = machNumber;
        /* Defining parametres of earth atmosphere*/
        final double gravity = 9.807;
        final double airConst = 287;
        final double radiusPlanet = 6356767;
        double heightZones [] = {0,11019,20063,32162,47350,51412,71802,86152};
        double pressureZones [] = {101325,22632,5474.9,858.01,110.91,66.938,3.9564,0.37338};
        double tempretureZones[]={288.15,216.65,216.65,228.65,270.65,270.65,214.65,186.65};
        double bParamZones[]    = {-0.0065,-0.0065,0,0.001,0.0028,-0.0028,-0.002,0};

        int i = 0;
        while (height >= heightZones[i] )i++ ;
        i--;
        double heightG0 = radiusPlanet*heightZones[i]/(radiusPlanet+heightZones[i]);
        double heightG = radiusPlanet*height/(radiusPlanet+height);

        mTempreture = tempretureZones[i] +bParamZones[i+1]*(heightG-heightG0);
        mPressure = pressureZones[i] * Math.exp(-gravity*(heightG-heightG0)/airConst/tempretureZones[i]);
        mDensity  = pressureZones[i]/airConst/tempretureZones[i];

    }

    private double findCp (double temp) {
        // @findCp function to calculate air Cp parameter
        // Definition of common aproximation values
        double cpFunction;
        int polynomDegree = 8;
        double tempLimit1 = 100;
        double tempLimit2 = 1000;
        double tempLimit3 = 3000;
        double [] a1 = {1161.482,-2.368819,0.01485511,-5.034909e-05,9.928569e-08,-1.111097e-10,6.540196e-14,-1.573588e-17};
        double [] a2 = {-7069.814,33.70605,-0.0581276,5.421615e-05,-2.936679e-08,9.237533e-12,-1.565553e-15,1.112335e-19};
        double [] a  =new double[polynomDegree];

        double temp0 = temp ;
        if(temp < tempLimit1) temp0 = tempLimit1;
        if(temp > tempLimit3) temp0 = tempLimit3;
        if(temp0 <= tempLimit2) {
             System.arraycopy(a1,0,a,0,a1.length);
        }
        else {
             System.arraycopy(a2,0,a,0,a1.length);
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "findCp: polynom array "+a);
        }
        cpFunction = 0;
        for(int i=0 ; i <polynomDegree; i++) {
            cpFunction = cpFunction + a[i]*Math.pow(temp0,i);
        }
        return (cpFunction);
    }

    public double findEnthalpy (double temp){
        double enthalpyFunction = 0;
        double dT = temp/nInter;
        double temp0 = 0;

        double cp1 = findCp(temp0);
        for(int i=0;i<nInter;i++){
            temp0 = temp0+dT ;
            double cp2 = findCp(temp0);
            enthalpyFunction = enthalpyFunction + (cp1 +cp2)*dT/2;
            cp1 = cp2;
        }
        //double enthalpy = enthalpySum;
        return(enthalpyFunction);
    }


    public double getPressure() {
        return mPressure;
    }

    public double getTempreture() {
        return mTempreture;
    }

    public double getDensity() {
        return mDensity;
    }
}
