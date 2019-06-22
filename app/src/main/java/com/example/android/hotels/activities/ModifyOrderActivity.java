package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.Utils;
import com.example.android.hotels.data.HotelList;
import com.example.android.hotels.data.OrderContract;

import java.text.ParseException;

public class ModifyOrderActivity extends AppCompatActivity {
    /**
     * Variable for user inputs
     */
    private EditText mUser_id, mOrder_id, mNumber_of_single, mNumber_of_dual, mNumber_of_quad,
            mCheck_in_date, mCheck_out_date;

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);

        mUser_id = (EditText) findViewById(R.id.modify_order_user_id);
        mOrder_id = (EditText) findViewById(R.id.modify_order_order_id);
        mNumber_of_single = (EditText) findViewById(R.id.modify_order_single);
        mNumber_of_dual = (EditText) findViewById(R.id.modify_order_dual);
        mNumber_of_quad = (EditText) findViewById(R.id.modify_order_quad);
        mCheck_in_date = (EditText) findViewById(R.id.modify_order_check_in_date);
        mCheck_out_date = (EditText) findViewById(R.id.modify_order_check_out_date);
        Button button = (Button) findViewById(R.id.modify_order_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
            }
        });
    }

    /**
     * Display error message to the user
     *
     * @param message the message to show
     */
    private void showMessage(String message) {
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.failed_modify_order, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_modify_order_text);
        textView.setText(message);
        Dialog dialog = new Dialog(ModifyOrderActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    /**
     * Modify order
     */
    private void modify() {
        int user_id, order_id, single, dual, quad, total_price = 0, hotel_id;
        String check_in_date = "", check_out_date = "";

        // try to parse userID
        try {
            user_id = Integer.parseInt(mUser_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_userID_format));
            return;
        }
        // try to parse orderID
        try {
            order_id = Integer.parseInt(mOrder_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_orderID_format));
            return;
        }
        // try to parse check_in_date
        try {
            check_in_date = Utils.parseDate(mCheck_in_date.getText().toString());
        } catch (ParseException e) {
            showMessage(getString(R.string.invalid_check_in_date_format));
            return;
        }
        // try to parse check_out_date
        try {
            check_out_date = Utils.parseDate(mCheck_out_date.getText().toString());
        } catch (ParseException e) {
            showMessage(getString(R.string.invalid_check_out_date_format));
            return;
        }
        // try to parse number_of_single
        try {
            single = Integer.parseInt(mNumber_of_single.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_single_format));
            return;
        }
        // try to parse number_of_double
        try {
            dual = Integer.parseInt(mNumber_of_dual.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_dual_format));
            return;
        }
        // try to parse number_of_quad
        try {
            quad = Integer.parseInt(mNumber_of_quad.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_quad_format));
            return;
        }

        // get the corresponding order out from database
        String selection = OrderContract.OrderEntry.COLUMN_USER_ID + " =? AND "
                + OrderContract.OrderEntry.COLUMN_ORDER_ID + " =?";
        String[] selectionArgs = new String[]{
                Integer.toString(user_id),
                Integer.toString(order_id)
        };
        String[] projection = {
                OrderContract.OrderEntry.COLUMN_USER_ID,
                OrderContract.OrderEntry.COLUMN_ORDER_ID,
                OrderContract.OrderEntry.COLUMN_HOTEL_ID,
                OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE,
                OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE,
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_SINGLE,
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_DUAL,
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_QUAD
        };
        Cursor cursor = getContentResolver().query(OrderContract.OrderEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        cursor.moveToFirst();

        // if getCount() == 0, this means that the order doesn't exist
        if (cursor.getCount() == 0) {
            showMessage(getString(R.string.failed_modify_order_id_not_exist));
            return;
        }

        // assign value to rooms and put them in content
        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NUMBER_OF_SINGLE, single);
        values.put(OrderContract.OrderEntry.COLUMN_NUMBER_OF_DUAL, dual);
        values.put(OrderContract.OrderEntry.COLUMN_NUMBER_OF_QUAD, quad);
        int single_column = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NUMBER_OF_SINGLE);
        int old_single = cursor.getInt(single_column);
        int double_column = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NUMBER_OF_DUAL);
        int old_double = cursor.getInt(double_column);
        int quad_column = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NUMBER_OF_QUAD);
        int old_quad = cursor.getInt(quad_column);
        int check_in_column = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE);
        String old_check_in_date = cursor.getString(check_in_column);
        int check_out_column = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE);
        String old_check_out_date = cursor.getString(check_out_column);
        int hotel_column = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_HOTEL_ID);
        hotel_id = cursor.getInt(hotel_column);

        // check validness of change
        // room must be lesser
        if (single > old_single || dual > old_double || quad > old_quad) {
            showMessage(getString(R.string.failed_modify_order_room_too_many));
            return;
        }
        // date range must be shorter
        if(check_in_date.compareTo(old_check_in_date) < 0 || check_out_date.compareTo(old_check_out_date) > 0){
            showMessage(getString(R.string.failed_modify_order_date_longer));
            return;
        }
        // check the validness of two dates (check_in_date < check_out_date)
        if(check_in_date.compareTo(check_out_date) >= 0){
            showMessage(getString(R.string.invalid_date_range));
            return;
        }

        // calculate new price
        total_price += single * HotelList.hotels.get(hotel_id).singlePrice;
        total_price += dual * HotelList.hotels.get(hotel_id).dualPrice;
        total_price += quad * HotelList.hotels.get(hotel_id).quadPrice;
        total_price *= Utils.dateDiff(check_in_date, check_out_date);

        // put change into database
        values.put(OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE, check_in_date);
        values.put(OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE, check_out_date);
        values.put(OrderContract.OrderEntry.COLUMN_TOTAL_PRICE, total_price);
        int rowsUpdated = getContentResolver().update(
                OrderContract.OrderEntry.CONTENT_URI,
                values,
                selection,
                selectionArgs
        );
        if(rowsUpdated != 0) {
            showMessage(getString(R.string.success_modify_order));
            return;
        }
    }
}
