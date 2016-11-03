package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * {@link CategoryAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of {@link Word} objects.
 */
public class CategoryAdapter extends FragmentPagerAdapter {

    /** Context of the app
     *
     * we don’t want to restrict our app to only support the English language. Instead, we should use the
     * string resource for those category names. That also means we need a Context object
     * in order to turn the string resource ID into an actual String.
     * So we modify the CategoryAdapter constructor to also require a Context input
     * so that we can get the proper text string.
     *
     * */
    private Context mContext;


    /**
     * Create a new {@link CategoryAdapter} object.
     *
     *  @param context is the context of the app
     *  @param fm is the fragment manager that will keep each fragment's state in the adapter
     *            across swipes
     */
    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1) {
            return new FamilyFragment();
        } else if (position == 2) {
            return new ColorsFragment();
        } else {
            return new PhrasesFragment();
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
         //return tabTitles[position];
        if (position == 0) {
            return mContext.getString(R.string.category_numbers);
        } else if (position == 1){
            return mContext.getString(R.string.category_family);
        } else if (position == 2) {
            return mContext.getString(R.string.category_colors);
        } else {
            return mContext.getString(R.string.category_phrases);
        }
    }
}
