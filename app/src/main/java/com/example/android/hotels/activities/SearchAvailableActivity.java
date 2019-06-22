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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SearchAvailableActivity extends AppCompatActivity {
    /** Variable for user inputs */
    private EditText mCheck_in_date, mCheck_out_date, mNumber_of_single,
            mNumber_of_dual, mNumber_of_quad;

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
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

    /**
     * Display error message to the user
     *
     * @param message the message to show
     */
    private void showMessage(String message) {
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.failed_search_available, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_search_available_text);
        textView.setText(message);
        Dialog dialog = new Dialog(SearchAvailableActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    /**
     * Search available orders
     */
    private void searchOrder() {
        int single, dual, quad;
        String check_in_date = "", check_out_date = "";

        // parse user inputs
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

        // check validness
        // check the validness of two dates (check_in_date < check_out_date)
        if (check_in_date.compareTo(check_out_date) >= 0) {
            showMessage(getString(R.string.invalid_date_range));
            return;
        }
        // check the validness of rooms
        if(single + dual + quad == 0) {
            showMessage(getString(R.string.invalid_room_numbers));
            return;
        }

        // get all results and put it into result
        ArrayList<SearchResult> result = new ArrayList<>();
        for(int i = 0 ; i < HotelList.hotels.size() ; ++i) {
            int price = ((single * HotelList.hotels.get(i).singlePrice) +
                    (dual * HotelList.hotels.get(i).dualPrice) +
                    (quad * HotelList.hotels.get(i).quadPrice)) * Utils.dateDiff(check_in_date, check_out_date);

            if (HotelList.hotels.get(i).count == 0) {
                if ((single <= HotelList.hotels.get(i).singleCount) &&
                        (dual <= HotelList.hotels.get(i).dualCount) &&
                        (quad <= HotelList.hotels.get(i).quadCount)) {
                    result.add(new SearchResult(HotelList.hotels.get(i).hotelStar, price, i));
                }
            } else {
                int[] available_rooms = Utils.getAvailableRoomInATimeRange(this, i, check_in_date, check_out_date);
                if ((single <= available_rooms[0]) && (dual <= available_rooms[1]) && (quad <= available_rooms[2])) {
                    result.add(new SearchResult(HotelList.hotels.get(i).hotelStar, price, i));
                }
            }
        }
        Collections.sort(result);

        // if no result
        if (result.size() == 0) {
            showMessage(getString(R.string.no_available_rooms));
            return;
        }

        // pass the result to SearchAvailableActivity
        Intent intent = new Intent(SearchAvailableActivity.this, SearchAvailableListActivity.class);
        intent.putExtra("Hotel_list", result);
        startActivity(intent);
    }
}
