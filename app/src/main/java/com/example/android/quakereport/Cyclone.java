package com.example.android.quakereport;

public class Cyclone {
    private double probability;

    /**
     * Location of the earthquake
     */
    private int mlongitude;
    private int mlatitude;
    private String name;
    private String mWindspeed;
    private int mCurr_windspeed;
    private int pressure;

    /**
     * Start Time of the Cyclone
     */
    private long mTimeInMilliseconds;

    /**
     * Basin of the earthquake
     */
    private String mBasin;

    public Cyclone(String name, String basin, long time, int longi, int lati) {
        this.name = name;
        mBasin = basin;
        mTimeInMilliseconds = time;
        mlongitude = longi;
        mlatitude = lati;
    }
    public Cyclone(String name, String basin, long time, int longi, int lati, String windspeed, int curr_windspeed,int pressure) {
        this.name = name;
        mBasin = basin;
        mTimeInMilliseconds = time;
        mlongitude = longi;
        mlatitude = lati;
        mCurr_windspeed=curr_windspeed;
        mWindspeed=windspeed;
        this.pressure=pressure;
    }
    public Cyclone(String name, String basin, long time, int longi, int lati, int windspeed,int pressure) {
        this.name = name;
        mBasin = basin;
        mTimeInMilliseconds = time;
        mlongitude = longi;
        mlatitude = lati;
        mCurr_windspeed=windspeed;
        this.pressure=pressure;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getmCurr_windspeed() {
        return mCurr_windspeed;
    }

    public String getmWindspeed() {
        return mWindspeed;
    }

    public void setmCurr_windspeed(int mCurr_windspeed) {
        this.mCurr_windspeed = mCurr_windspeed;
    }

    public void setMlongitude(int mlongitude) {
        this.mlongitude = mlongitude;
    }

    public void setmWindspeed(String mWindspeed) {
        this.mWindspeed = mWindspeed;
    }


    public void setLongitude(int longitude) {
        mlongitude = longitude;
    }

    public int getMlatitude() {
        return mlatitude;
    }

    public void setMlatitude(int latitude) {
        mlatitude = latitude;
    }

    public int getMlongitude() {
        return mlongitude;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public void setmTimeInMilliseconds(long mTimeInMilliseconds) {
        this.mTimeInMilliseconds = mTimeInMilliseconds;
    }

    public String getmBasin() {
        return mBasin;
    }

    public void setmBasin(String basin) {
        this.mBasin = basin;
    }

    public double getProbability() {

        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getName() {
        return name;
    }

    public void setName(String mname) {
        name = mname;
    }


}
