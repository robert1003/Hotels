package com.example.android.hotels.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.hotels.R;

import org.w3c.dom.Text;

public class NewOrderList extends AppCompatActivity {
    private TextView mOrder_id, mUser_id, mHotel_id, mCheck_in_date, mCheck_out_date, mNumber_of_single, mNumber_of_dual, mNumber_of_quad, mTotal_price;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_list);
        String order_id = getIntent().getStringExtra("Order_id");
        String user_id = getIntent().getStringExtra("User_id");
        String hotel_id = getIntent().getStringExtra("Hotel_id");
        String check_in = getIntent().getStringExtra("Check_in");
        String check_out = getIntent().getStringExtra("Check_out");
        String single = getIntent().getStringExtra("Single");
        String dual = getIntent().getStringExtra("Dual");
        String quad = getIntent().getStringExtra("Quad");
        String price = getIntent().getStringExtra("Price");
        mOrder_id = (TextView) findViewById(R.id.new_order_list_order_id);
        mUser_id = (TextView) findViewById(R.id.new_order_list_user_id);
        mHotel_id = (TextView) findViewById(R.id.new_order_list_hotel_id);
        mCheck_in_date = (TextView) findViewById(R.id.new_order_list_check_in_date);
        mCheck_out_date = (TextView) findViewById(R.id.new_order_list_check_out_date);
        mNumber_of_single = (TextView) findViewById(R.id.new_order_list_number_of_single);
        mNumber_of_dual = (TextView) findViewById(R.id.new_order_list_number_of_dual);
        mNumber_of_quad = (TextView) findViewById(R.id.new_order_list_number_of_quad);
        mTotal_price = (TextView) findViewById(R.id.new_order_list_total_price);
        mOrder_id.setText(order_id);
        mUser_id.setText(user_id);
        mHotel_id.setText(hotel_id);
        mCheck_in_date.setText(check_in);
        mCheck_out_date.setText(check_out);
        mNumber_of_single.setText(single);
        mNumber_of_dual.setText(dual);
        mNumber_of_quad.setText(quad);
        mTotal_price.setText(price);
    }
}
