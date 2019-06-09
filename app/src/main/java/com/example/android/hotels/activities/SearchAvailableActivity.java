package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.ContentValues;
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

import java.text.ParseException;
import java.util.ArrayList;

public class SearchAvailableActivity extends AppCompatActivity {
    private EditText mCheck_in_date, mCheck_out_date, mNumber_of_single,
            mNumber_of_dual, mNumber_of_quad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_available);

        mCheck_in_date = (EditText) findViewById(R.id.search_available_check_in_date);
        mCheck_out_date = (EditText) findViewById(R.id.search_available_check_out_date);
        mNumber_of_single = (EditText) findViewById(R.id.search_available_room_single);
        mNumber_of_dual = (EditText) findViewById(R.id.search_available_room_dual);
        mNumber_of_quad = (EditText) findViewById(R.id.search_available_room_quad);

        Button button = (Button) findViewById(R.id.search_available_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchOrder();
            }
        });
    }

    private void searchOrder() {
        int number_of_single, number_of_dual, number_of_quad;
        String check_in_date, check_out_date;

        try {
            check_in_date = Utils.parseDate(mCheck_in_date.getText().toString());
            check_out_date = Utils.parseDate(mCheck_out_date.getText().toString());
            number_of_single = Integer.parseInt(mNumber_of_single.getText().toString());
            number_of_dual = Integer.parseInt(mNumber_of_dual.getText().toString());
            number_of_quad = Integer.parseInt(mNumber_of_quad.getText().toString());
        } catch(ParseException e) {
            LayoutInflater factory = getLayoutInflater();
            View view = factory.inflate(R.layout.failed_search_available, null);
            TextView textView = (TextView) view.findViewById(R.id.failed_search_available_text);
            textView.setText(getString(R.string.invalid_date_format));
            Dialog dialog = new Dialog(SearchAvailableActivity.this);
            dialog.setContentView(view);
            dialog.show();

            return;
        } catch(NumberFormatException e) {
            LayoutInflater factory = getLayoutInflater();
            View view = factory.inflate(R.layout.failed_search_available, null);
            TextView textView = (TextView) view.findViewById(R.id.failed_search_available_text);
            textView.setText(getString(R.string.invalid_number_format));
            Dialog dialog = new Dialog(SearchAvailableActivity.this);
            dialog.setContentView(view);
            dialog.show();

            return;
        }

        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0 ; i < HotelList.hotels.size() ; ++i) {
            int[] occupied_rooms = Utils.getOrdersInATimeRange(this, i, check_in_date, check_out_date);
            Log.i("hotels", Integer.toString(i) + " " + Integer.toString(occupied_rooms[0]));
            if((number_of_single + occupied_rooms[0] <= HotelList.hotels.get(i).singleCount) &&
                    (number_of_dual + occupied_rooms[1] <= HotelList.hotels.get(i).dualCount) &&
                    (number_of_quad + occupied_rooms[2] <= HotelList.hotels.get(i).quadCount)) {
                result.add(i);
            }
        }

        for(int i : result) {
            Log.i("fuck", Integer.toString(i));
        }
    }
}
