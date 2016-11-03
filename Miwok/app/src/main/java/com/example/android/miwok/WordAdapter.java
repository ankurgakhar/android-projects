package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ankur.gakhar on 23/09/2016.
 *
 * {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link Word} objects.
 *
 */
public class WordAdapter extends ArrayAdapter<Word>{

    /** Resource ID for the background color for this list of words */
    private int mColorResourceId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param wordObjects    A List of Word objects to display in a list
     */
    public WordAdapter(Activity context, ArrayList<Word> wordObjects, int colorResourceId ) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument(which is for resource Id), so it can be any value. Here, we used 0.
        // Here we are passing 0 for second argument i.e. for resource because we don't need to rely on the
        // superclass ArrayAdapter inflating or creating a List Item View for us. Instead our getView () method
        // wil manually handle the inflating the layout from the layout resource id ourselves.
        // By using "super" keyword we are calling constructor of SuperClass ArrayAdapter
        // ArrayAdapter(Context context, int resource, List<T> objects)
        super(context, 0, wordObjects);
        mColorResourceId = colorResourceId;
    }


    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    /** position: is the position in the list of data(in our case List of Word Objects) this layout should represent
    * convertView: is the RecycleView that needs to be populated
    * parent: parent ViewGroup for all the list items which is the ListView itself, in this case.
     * This method gets called when the ListView(or GridView) is trying to display a list of items at a
     * given position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        //Check if there is any View that can be reused by checking whether it is NULL or not. The View being NULL
        //is common when you first open up the activity, and you're creating item in the list for the first time
        //to fill up the screen. Now, once the screen is filled up, then it's likely that there will be a valid
        //view that we can reuse.
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
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        // Get the {@link Word} object located at this position in the list
        //getItem () is originally defined in ArrayAdapter class
        Word currentWordObject = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        // Get the Miwok word from the current Word object and
        // set this text on the miwok TextView
        miwokTextView.setText(currentWordObject.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        // Get the Default(English) from the current Word object and
        // set this text on the default TextView
        defaultTextView.setText(currentWordObject.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID image
          ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        if(currentWordObject.hasImage()) {
            // Set the ImageView to the image resource specified in the current Word object
            imageView.setImageResource(currentWordObject.getImageResourceId());

            //Make sure the view is Visible. We do this logic because Views get reused. So if the View
            //was previously hidden, we want to make sure that it's made Visible again when we set a
            //new image on the ImageView.
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set Visibility to GONE)
            // 3 possible CONSTANT VALUES for Visibility are:
            // a) VISIBLE: The View is visible
            // b) INVISIBLE: The view is invisible, and it doesn't take any space for layout purposes
            // c) GONE: The view is invisible, but it still takes space for layout purposes.

            imageView.setVisibility(View.GONE);
        }

        //Set the theme color for the list item
        //Get the resource id of the Linear Layout that holds the two text views in list_item layout
         View textContainer = listItemView.findViewById(R.id.text_container);
        //Find the color that the resource ID maps to
         int color = ContextCompat.getColor(getContext(), mColorResourceId);
        //Set the background color of the text container View
         textContainer.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
