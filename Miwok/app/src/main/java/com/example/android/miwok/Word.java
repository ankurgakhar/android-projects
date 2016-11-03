package com.example.android.miwok;

/**
 * Created by ankur.gakhar on 23/09/2016.
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word
 */
public class Word {

    /**
     * Default translation for the word
     */
    private String mDefaultTranslation;  //m stands for Member Variable

    /**
     * Miwok translation for the word
     */
    private String mMiwokTranslation;

    /**
     * Drawable resource ID for the word
     * Initial value is assigned as a CONSTANT NO_IMAGE_PROVIDED
     */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    // A CONSTANT for checking whether an Image is provided or not
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Audio resource ID for the word
     */
    private int mAudioResourceId;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     * @param audioResourceId   is the resource ID for the audio file associated with this word
     */

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation   is the word in the Miwok language
     * @param imageResourceId    is the drawable resource ID for the Image associated with the word
     * @param audioResourceId   is the resource ID for the audio file associated with this word
     */

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * Return the image resource ID of the word.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Return whether or not there is an image for this word.
     */

    public boolean hasImage(){
        // Check whether an image is provided or not. If an image is provided then value of the variable
        // mImageResourceId would not be equal to CONTANT 'NO_IMAGE_PROVIDED' and the condition will
        // evaluate to True, else if no image is provided then the condition will evalute to false
       return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * Returns the audio resource ID of the word.
     * @return
     */
    public int getAudioResourceId(){
        return mAudioResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}
