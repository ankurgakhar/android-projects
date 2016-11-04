package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;  //int with lower case i because it is a primitive data type means it is one of basic java data types

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked. S in String is capitalized here because it is an object
     */
    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String customerName = nameField.getText().toString();

        //Figure out if the user wants Chocolate topping
      //The reason we can cast the return value of findViewById to Checkbox because CheckBox is a subclass of View.
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if the user wants Chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);
        //displayMessage(priceMessage);

        String orderReceipientMailAddress[] = {"ankur.gakhar112@gmail.com"};
        composeEmail(orderReceipientMailAddress, "JustJava order for " + customerName, priceMessage);

//        //Calls an Intent to open Map app with a given geoLocation
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    /**
     *
     * @param addresses contains a string array of all "To" recipient email addresses.
     * @param subject is a string with the email subject.
     * @param bodyText is a string with the body of the email.
     */
    public void composeEmail(String[] addresses, String subject, String bodyText) {
        Intent intent = new Intent(Intent.ACTION_SENDTO); //all capital letters shows that ACTION_SENDTO is a constant in Intent class
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, bodyText);

        //It's possible that a user won't have any apps that handle the implicit intent you send to startActivity().
        //If that happens, the call will fail and your app will crash. To verify that an activity will receive the
        //intent, call resolveActivity() on your Intent object. If the result is non-null, then there is at least
        //one app that can handle the intent and it's safe to call startActivity(). If the result is null, you
        //should not use the intent and, if possible, you should disable the feature that issues the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            //Show an error message as a toast
            Context context = getApplicationContext();
            String text = "You cannot have more than 100 coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
     }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Context context = getApplicationContext();
            String text = "You cannot have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
           //The above Toast logic can be written in a single line as below
           //Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();

            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method is for calculating the total price.
     * @ param price is the total ordered price
     * @ addWhippedCream is to check whether or not to add the Whipped Cream price
     * @ addChocolate is to check whether or not to add the Chocolate price
     * @ return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffee
        int basePrice = 5;

        // Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // Add $2 if the user wants chocolate topping
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by quantity
        return basePrice * quantity;
    }

    /**
     * Create summary of the order
     *
     * This method is for calculating the total price.
     * @ param addCustomerName is to display the customer name
     * @ param price is the total ordered price
     * @ param addWhippedCream is whether or not the user wants whipped cream topping
     * @ param addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String addCustomerName, int price, boolean addWhippedCream, boolean addChocolate) {
        String message = getString(R.string.order_summary_name, addCustomerName);
        message += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        message += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        message += "\n" + getString(R.string.order_summary_quantity, quantity);
        //to get the local currency we'll use a Utility function that formats the input price into the current
        //currency for the current local.
        message +=  "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        message +=  "\n" + getString(R.string.thank_you);
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

     /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * This method displays the given price on the screen.
     */
    /**private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/
}