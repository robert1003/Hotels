package com.example.android.hotels.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.android.hotels.R;

import java.util.ArrayList;

public class SearchAvailableListActivity extends AppCompatActivity {

    /**
     * Initialize the screen
     *
     * @param saveInstanceState reload saved instance
     */
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_search_available_list);

        ArrayList<SearchResult> results = (ArrayList<SearchResult>) getIntent().getSerializableExtra("Hotel_list");
        ListView resultListView = (ListView) findViewById(R.id.search_available_list);
        SearchResultAdapter adapter = new SearchResultAdapter(this, results);
        resultListView.setAdapter(adapter);
    }
}
