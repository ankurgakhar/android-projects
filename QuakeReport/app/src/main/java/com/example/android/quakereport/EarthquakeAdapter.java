package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ankur.gakhar on 05/10/2016.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * The part of the location string from the USGS service that we use to determine
     * whether or not there is a location offset present ("5km N of Cairo, Egypt").
     */
    private static final String LOCATION_SEPARATOR = " of ";


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param earthquakeObjects    A List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakeObjects ) {
        // By using "super" keyword we are calling constructor of SuperClass ArrayAdapter
        // ArrayAdapter(Context context, int resource, List<T> objects)
        super(context, 0, earthquakeObjects);
    }

    /**
     * Returns a list item view that displays information about the earthquake at the given position
     * in the list of earthquakes.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if the existing view is being reused, otherwise inflate the view
         View listItemView = convertView;
        //Check if there is any View that can be reused by checking whether it is NULL or not. The View
        // being NULL is common when you first open up the activity, and you're creating item in the list
        // for the first time to fill up the screen. Now, once the screen is filled up, then it's likely
        // that there will be a valid view that we can reuse.
        if(listItemView == null) {
            //LayoutInflater Instantiates a layout XML file into its corresponding View objects. It is never used
            // directly.
            // from(Context context) Obtains the LayoutInflater from the given context
            //           return type: static LayoutInflator
            //getContext(): Return the context we are running in, for access to resources, class loader, etc.
            //            return type: Context
            //	inflate(int resource, ViewGroup root, boolean attachToRoot)
            //            return type: View
            // we are manually inflating the view which is why we don't need to pass the layout resource ID to the
            // superclass in the constructor
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);

        }

        // Get the {@link Earthquake} object located at this position in the list
        //getItem() is originally defined in ArrayAdapter class
        Earthquake currentEarthquakeObject = getItem(position);

        // Find the TextView in the earthquake_list_item_item.settings_main layout with the ID mag_text_view
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquakeObject.getMagnitudeOfEarthquake());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Get the Magnitude from the current Earthquake object and
        // set this text on the Magnitude TextView
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquakeObject.getMagnitudeOfEarthquake());

        // Display the magnitude of the current earthquake in that TextView
        magnitudeView.setText(formattedMagnitude);

        // Get the original location string from the Earthquake object,
        // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
        String originalLocation = currentEarthquakeObject.getLocationOfEarthquake();

        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
        // then store the primary location separately from the location offset in 2 Strings,
        // so they can be displayed in 2 TextViews.
        String locationOffset;
        String primaryLocation;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            // Split the string into different parts (as an array of Strings)
            // based on the " of " text. We expect an array of 2 Strings, where
            // the first String will be "5km N" and the second String will be "Cairo, Egypt".
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            // Location offset should be "5km N " + " of " --> "5km N of"
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            // Primary location should be "Cairo, Egypt"
            primaryLocation = parts[1];
        } else {
            // Otherwise, there is no " of " text in the originalLocation string.
            // Hence, set the default location offset to say "Near the".
            locationOffset = getContext().getString(R.string.near_the);
            // The primary location will be the full location string "Pacific-Antarctic Ridge".
            primaryLocation = originalLocation;
        }

        // Another way of achieving the objective

//        if (originalLocation.contains("of")){
//            //String[] locationParts = location.split("of");
//                   locationOffset  = originalLocation.substring(0, originalLocation.indexOf("of") + 2);
//                   primaryLocation = originalLocation.substring(originalLocation.indexOf("of") + 3, originalLocation.length());
//        } else {
//            locationOffset  = "Near the ";
//            primaryLocation = originalLocation;
//        }


        // Find the TextView in the earthquake list item layout with the ID location_offset
        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);

        // Get the Location Offset details from Location manipulation and
        // set this text on the Location Offset TextView
        locationOffsetView.setText(locationOffset);

        // Find the TextView in the earthquake list item layout with the ID primary_location
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);

        // Get the Primary Location details from Location manipulation and
        // set this text on the Primary Location TextView
        primaryLocationView.setText(primaryLocation);

        //When the EarthquakeAdapter creates list items for each earthquake, the adapter must convert
        // the time in milliseconds into a properly formatted date and time

        // Create a new Date object from the time in milliseconds of the earthquake which is a long type
        Date dateObject = new Date(currentEarthquakeObject.getTimeInMilliseconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        //we can initialize a SimpleDateFormat instance and configure it to provide a more readable
        //representation according to the given format
        //Format the date string (i.e. "Mar 3, 1984")

        String formattedDate = formatDate(dateObject);

        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);

        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        //Return the list item view that is now showing the appropriate date
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
    * from a decimal magnitude value.
    */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }


    /**
     * Return the color for Magnitude background circle based on the intensity of the
     * earthquake.
     *
     * @param magnitude of the earthquake
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

}

