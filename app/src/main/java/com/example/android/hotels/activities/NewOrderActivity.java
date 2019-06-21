package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.Utils;
import com.example.android.hotels.data.Hotel;
import com.example.android.hotels.data.HotelList;
import com.example.android.hotels.data.OrderContract;
import com.example.android.hotels.data.OrderContract.OrderEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewOrderActivity extends AppCompatActivity {
    /**
     * Variable for user inputs
     */
    private EditText mUser_id, mHotel_id, mCheck_in_date, mCheck_out_date, mNumber_of_single,
            mNumber_of_dual, mNumber_of_quad;
    private final int max_number_of_hotel = HotelList.hotels.size();

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        mUser_id = (EditText) findViewById(R.id.new_order_user_id);
        mHotel_id = (EditText) findViewById(R.id.new_order_hotel_id);
        mCheck_in_date = (EditText) findViewById(R.id.new_order_check_in_date);
        mCheck_out_date = (EditText) findViewById(R.id.new_order_check_out_date);
        mNumber_of_single = (EditText) findViewById(R.id.new_order_room_single);
        mNumber_of_dual = (EditText) findViewById(R.id.new_order_room_dual);
        mNumber_of_quad = (EditText) findViewById(R.id.new_order_room_quad);

        Button button = (Button) findViewById(R.id.new_order_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOrder();
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
        View view = factory.inflate(R.layout.failed_new_order, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_new_order_text);
        textView.setText(message);
        Dialog dialog = new Dialog(NewOrderActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    /**
     * Create new order
     */
    private void newOrder() {
        boolean ok = true;

        int user_id, hotel_id = max_number_of_hotel, single, dual, quad, total_price = 0;
        String check_in_date = "", check_out_date = "";


        // try to parse userID
        try {
            user_id = Integer.parseInt(mUser_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_userID_format));
            return;
        }

        // try to parse hotelID
        try {
            hotel_id = Integer.parseInt(mHotel_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_hotelID_format));
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

        // check the validness of hotel_id
        if (hotel_id >= max_number_of_hotel) {
            showMessage(getString(R.string.failed_hotel_id));
            return;
        }

        // check the validness of two dates (check_in_date < check_out_date)
        if (check_in_date.compareTo(check_out_date) >= 0) {
            showMessage(getString(R.string.invalid_date_range));
            return;
        }

        // check if rooms are enough; if enough, calculate the price
        int[] available_rooms_count = Utils.getAvailableRoomInATimeRange(this, hotel_id,
                check_in_date, check_out_date);
        Log.i("available", Integer.toString(available_rooms_count[0]) + " " +
                Integer.toString(available_rooms_count[1]) + " " +
                Integer.toString(available_rooms_count[2]));
        if(single + dual + quad == 0) {
            showMessage(getString(R.string.invalid_date_range));
            return;
        } else if (single <= available_rooms_count[0] && dual <= available_rooms_count[1] &&
                quad <= available_rooms_count[2]) {
            total_price += single * HotelList.hotels.get(hotel_id).singlePrice;
            total_price += dual * HotelList.hotels.get(hotel_id).dualPrice;
            total_price += quad * HotelList.hotels.get(hotel_id).quadPrice;
            total_price *= Utils.dateDiff(check_in_date, check_out_date);
            Log.i("date", Integer.toString(Utils.dateDiff(check_in_date, check_out_date)));
        } else {
            showMessage(getString(R.string.failed_order_room));
            return;
        }

        // create new order
        ContentValues values = new ContentValues();
        values.put(OrderEntry.COLUMN_USER_ID, user_id);
        values.put(OrderEntry.COLUMN_HOTEL_ID, hotel_id);
        values.put(OrderEntry.COLUMN_CHECK_IN_DATE, check_in_date);
        values.put(OrderEntry.COLUMN_CHECK_OUT_DATE, check_out_date);
        values.put(OrderEntry.COLUMN_NUMBER_OF_SINGLE, single);
        values.put(OrderEntry.COLUMN_NUMBER_OF_DUAL, dual);
        values.put(OrderEntry.COLUMN_NUMBER_OF_QUAD, quad);
        values.put(OrderEntry.COLUMN_TOTAL_PRICE, total_price);

        Uri newuri = getContentResolver().insert(OrderEntry.CONTENT_URI, values);
        Intent intent = new Intent(NewOrderActivity.this, NewOrderList.class);
        intent.putExtra("Order_id", newuri.getLastPathSegment());
        intent.putExtra("User_id", Integer.toString(user_id));
        intent.putExtra("Hotel_id", Integer.toString(hotel_id));
        intent.putExtra("Check_in", check_in_date);
        intent.putExtra("Check_out", check_out_date);
        intent.putExtra("Single", Integer.toString(single));
        intent.putExtra("Dual", Integer.toString(dual));
        intent.putExtra("Quad", Integer.toString(quad));
        intent.putExtra("Price", Integer.toString(total_price));
        startActivity(intent);
        finish();
    }
}
