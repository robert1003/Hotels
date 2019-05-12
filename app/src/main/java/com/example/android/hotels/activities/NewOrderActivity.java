package com.example.android.hotels.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.R;

public class NewOrderActivity extends AppCompatActivity {
    private EditText mUser_id, mHotel_id, mCheck_in_date, mCheck_out_date, mNumber_of_people, mNumber_of_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        mUser_id = (EditText) findViewById(R.id.new_order_user_id);
        mHotel_id = (EditText) findViewById(R.id.new_order_hotel_id);
        mCheck_in_date = (EditText) findViewById(R.id.new_order_check_in_date);
        mCheck_out_date = (EditText) findViewById(R.id.new_order_check_out_date);
        mNumber_of_people = (EditText) findViewById(R.id.new_order_number_of_people);
        mNumber_of_room = (EditText) findViewById(R.id.new_order_number_of_room);

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
        String number_of_people = mNumber_of_people.getText().toString();
        String number_of_room = mNumber_of_room.getText().toString();

        // try to create new order here
        if(Integer.parseInt(user_id) <= 100) {
            LayoutInflater factory = getLayoutInflater();
            View view = factory.inflate(R.layout.success_new_order, null);
            TextView textView = (TextView) view.findViewById(R.id.success_new_order_text);
            textView.setText("new order!!!!!!!");
            Dialog dialog = new Dialog(NewOrderActivity.this);
            dialog.setContentView(view);
            dialog.show();
        } else {
            LayoutInflater factory = getLayoutInflater();
            View view = factory.inflate(R.layout.failed_new_order, null);
            TextView textView = (TextView) view.findViewById(R.id.failed_new_order_text);
            textView.setText("failed order!!!!!!!");
            Dialog dialog = new Dialog(NewOrderActivity.this);
            dialog.setContentView(view);
            dialog.show();
        }
    }
}
