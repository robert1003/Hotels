package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.Utils;
import com.example.android.hotels.data.HotelList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SearchAvailableListActivity extends AppCompatActivity {
    public void onCreate(Bundle saveInstanceState) {
        Log.i("fuck", "success jump");
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_available_list);
        // Instanciating an array list (you don't need to do this,
        // you already have yours).

        ArrayList<SearchResult> results = (ArrayList<SearchResult>) getIntent().getSerializableExtra("Hotel_list");

        Log.i("abc", Integer.toString(results.size()));

        for (SearchResult i : results) {
            Log.i("abc", i.toString());
        }

        ListView resultListView = (ListView) findViewById(R.id.search_available_list);

        SearchResultAdapter adapter = new SearchResultAdapter(this, results);

        resultListView.setAdapter(adapter);

    }
}
