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
    private double mtempreture;
    private double mDensity;
    private static final String TAG = "AtmosphereClass";
    private double cp = 0;
    private int nInter = 1000;

    public Atmosphere() {
    }

    public Atmosphere (double Height, double machNumber){
        mHeight = Height;
        mMachNumber = machNumber;


    }

    private double findCp (double temp) {
        // @findCp function to calculate air Cp parameter
        // Definition of common aproximation values
        //double cp;
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
        for(int i=0 ; i <polynomDegree; i++) {
            cp = cp + a[i]*Math.pow(temp0,i);

        }
    return (cp);
    }

    private double findEnthalpy (double temp){
        double enthalpySum = 0;
        double dT = temp/nInter;
        double temp0 = 0;

        double cp1 = findCp(temp0);
        for(int i=0;i<nInter;i++){
            temp0 = temp0*i*dT ;
        }
        double enthalpy = enthalpySum;
        return(enthalpy);
    }


}
