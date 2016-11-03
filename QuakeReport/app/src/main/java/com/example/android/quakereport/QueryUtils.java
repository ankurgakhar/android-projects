package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    /**
     * Query the USGS dataset and return an ArrayList {@link Earthquake} object to represent a si
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl){
        //We are forcing the background thread to pause execution and wait for 2 seconds (which is 2000
        // milliseconds), before proceeding to execute the rest of lines of code in this method.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(requestUrl == null){
            return null;
        }

        // Create URL object
          URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
          String jsonResponse = null;

        try {
            jsonResponse  = makeHttpRequest(url);
        } catch (IOException exception) {
            Log.e(LOG_TAG,"Error executing HTTP request", exception);
        }

        //Extract relevant features from the JSON response and create an List of {@link Earthquake}s
        List<Earthquake> earthquakes = extractFeaturesFromJSON(jsonResponse);

        //Return the list of {@link Earthquake}s
        return earthquakes;
    }

    /**
     * Returns new URL object from the given string URL.
     */

    private static URL createUrl(String stringURL)  {
         URL url = null;
        try {
             url = new URL(stringURL);
            return url;
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG,"Problem building the URL", exception);
        }
        return null;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null){
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException exception) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", exception);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */

    private static String readFromStream(InputStream inputStream) throws IOException {
         StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */

    public static List<Earthquake> extractFeaturesFromJSON(String earthquakeJSON) {

        // If the JSON string is empty or null, then return early.
        if(TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
           List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of Earthquake objects with the corresponding data.

            //JSONObject class will parse the whole GeoJSON string we got back from the USGS API
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            if(earthquakeArray.length() > 0 ) {

                for (int i = 0; i < earthquakeArray.length(); i++) {
                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                    JSONObject properties = currentEarthquake.getJSONObject("properties");

                    double magnitudeOfEarthquake = properties.getDouble("mag");
                    String placeOfEarthquake = properties.getString("place");
                    long timeOfEarthquake = properties.getLong("time");
                    String url = properties.getString("url");

                    earthquakes.add(new Earthquake(magnitudeOfEarthquake, placeOfEarthquake, timeOfEarthquake, url));
                }

                // Return the list of earthquakes
                return earthquakes;
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }


}
