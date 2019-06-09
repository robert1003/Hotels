package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class NewOrderActivity extends AppCompatActivity {
    private EditText mUser_id, mHotel_id, mCheck_in_date, mCheck_out_date, mNumber_of_single, mNumber_of_dual, mNumber_of_quad;
    private final int max_number_of_hotel = 1500;
    private  boolean ok = true;
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

    private void newOrder() {
        String user_id = mUser_id.getText().toString();
        String hotel_id = mHotel_id.getText().toString();
        String check_in_date = mCheck_in_date.getText().toString();
        String check_out_date = mCheck_out_date.getText().toString();
        String number_of_single = mNumber_of_single.getText().toString();
        String number_of_dual = mNumber_of_dual.getText().toString();
        String number_of_quad = mNumber_of_quad.getText().toString();
        // try to create new order here
        ContentValues values = new ContentValues();
        values.put(OrderEntry.COLUMN_USER_ID, Integer.parseInt(user_id));
        values.put(OrderEntry.COLUMN_HOTEL_ID, Integer.parseInt(hotel_id));
        values.put(OrderEntry.COLUMN_CHECK_IN_DATE, Integer.parseInt(check_in_date));
        values.put(OrderEntry.COLUMN_CHECK_OUT_DATE, Integer.parseInt(check_out_date));
        values.put(OrderEntry.COLUMN_NUMBER_OF_SINGLE, Integer.parseInt(number_of_single));
        values.put(OrderEntry.COLUMN_NUMBER_OF_DUAL, Integer.parseInt(number_of_dual));
        values.put(OrderEntry.COLUMN_NUMBER_OF_QUAD, Integer.parseInt(number_of_quad));
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.failed_new_order, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_new_order_text);
        Integer hotel_index = Integer.parseInt(hotel_id);
        int total = 0;
        try {
            int check_in = Utils.parseDate(mCheck_in_date.getText().toString());
            int check_out = Utils.parseDate(mCheck_out_date.getText().toString());
        } catch(ParseException e) {
            textView.setText(getString(R.string.invalid_date_format));
            ok = false;
        }
        if(hotel_index >= max_number_of_hotel){
            textView.setText(R.string.failed_hotel_id);
            ok = false;
        }else{
            Integer number_single = Integer.parseInt(number_of_single);
            Integer number_dual = Integer.parseInt(number_of_dual);
            Integer number_quad = Integer.parseInt(number_of_quad);
            int hotel_single = HotelList.hotels.get(hotel_index).singleCount;
            int hotel_dual = HotelList.hotels.get(hotel_index).dualCount;
            int hotel_quad = HotelList.hotels.get(hotel_index).quadCount;
            if(number_single < hotel_single && number_dual < hotel_dual && number_quad < hotel_quad){
                total += number_single * HotelList.hotels.get(hotel_index).singlePrice;
                total += number_dual * HotelList.hotels.get(hotel_index).dualPrice;
                total += number_quad * HotelList.hotels.get(hotel_index).quadPrice;
            }else{
                textView.setText(R.string.failed_order_room);
                ok = false;
            }
        }
        values.put(OrderEntry.COLUMN_TOTAL_PRICE, total);
        if(ok){
            Uri newuri = getContentResolver().insert(OrderEntry.CONTENT_URI, values);
            Intent intent = new Intent(NewOrderActivity.this, NewOrderList.class);
            intent.putExtra("Order_id", newuri.getLastPathSegment());
            intent.putExtra("User_id", user_id);
            intent.putExtra("Hotel_id", hotel_id);
            intent.putExtra("Check_in", check_in_date);
            intent.putExtra("Check_out", check_out_date);
            intent.putExtra("Single", number_of_single);
            intent.putExtra("Dual", number_of_dual);
            intent.putExtra("Quad", number_of_quad);
            startActivity(intent);
            //textView.setText(newuri.getLastPathSegment());
        }
        Dialog dialog = new Dialog(NewOrderActivity.this);
        dialog.setContentView(view);
        dialog.show();
    }
}
