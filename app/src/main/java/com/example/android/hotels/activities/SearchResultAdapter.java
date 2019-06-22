package com.example.android.hotels.activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.hotels.R;

import java.util.ArrayList;

public class SearchResultAdapter extends ArrayAdapter<SearchResult> {

    public SearchResultAdapter(Activity content, ArrayList<SearchResult> result) {
        super(content, 0, result);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_search_available_item, parent, false
            );
        }

        SearchResult currentHotel = getItem(position);
        TextView hotelIDTextView = (TextView) listItemView.findViewById(R.id.search_available_hotelID);
        hotelIDTextView.setText(String.valueOf(currentHotel.getHotel_id()));

        TextView priceTextView = (TextView) listItemView.findViewById(R.id.search_available_total_price);
        priceTextView.setText(String.valueOf(currentHotel.getPrice()));

        TextView starTextView = (TextView) listItemView.findViewById(R.id.search_available_star);
        starTextView.setText(String.valueOf(currentHotel.getStar()));

        return listItemView;
    }
}
