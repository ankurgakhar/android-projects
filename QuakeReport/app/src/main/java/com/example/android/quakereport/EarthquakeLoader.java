package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>>{

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
      private String mUrl;

    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */

    public EarthquakeLoader(Context context,String url){
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    /**
     * This method is invoked (or called) on a background thread, so we can perform long-running
     * operations like making a network request.
     *
     * It is NOT okay to update the UI from a background thread, so we just
     * return a list of {@link Earthquake}s as the result
     */

    @Override
    public List<Earthquake> loadInBackground() {
        // Don't perform the request if there is no URL.
        if (mUrl == null) {
            return null;
        }

        // Perform the HTTP request for earthquake data, process the response, and extract a list of earthquakes
        List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);

        return earthquakes;
    }
}
