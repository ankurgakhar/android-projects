package com.example.android.quakereport;

/**
 * Created by ankur.gakhar on 05/10/2016.
 */
public class Earthquake {

    /**
     * Magnitude of the earthquake
     */
    private double mMagnitude;

    /**
     * Location where earthquake occurred
     */
    private String mLocation;

    /**
     * Time of the earthquake in milliseconds
     */

    private long mTimeInMilliseconds;

    /**
     * URL to know more about the earthquake
     */

    private String mURL;


    /**
     * Constructs a new {@link Earthquake} object
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location   is the city location of the earthquake
     * @param timeInMillis  timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *                       earthquake happened
     * @param URL   website URL to find more info about the earthquake
     */

    public Earthquake(double magnitude, String location, long timeInMillis, String URL) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMillis;
        mURL = URL;
    }

    /**
    * Get the Magnitude of the earthquake
    */
    public double getMagnitudeOfEarthquake() {
        return mMagnitude;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getLocationOfEarthquake() {
        return mLocation;
    }

    /**
     * Get the Time of the earthquake
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /**
     * Get the website URL to find more info about the earthquake
     */
    public String getURL() {
        return mURL;
    }
}
