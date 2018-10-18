package com.example.android.quakereport;

public class Cyclone {
    private double probability;

    /**
     * Location of the earthquake
     */
    private int mlongitude;
    private int mlatitude;
    private String name;

    /**
     * Time of the earthquake
     */
    private long mTimeInMilliseconds;

    /**
     * Website URL of the earthquake
     */
    private String mBasin;

    public Cyclone(String name, String basin, long time, int longi, int lati) {
        this.name = name;
        mBasin = basin;
        mTimeInMilliseconds = time;
        mlongitude = longi;
        mlatitude = lati;
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
