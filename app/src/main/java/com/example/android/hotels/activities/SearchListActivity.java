package com.example.android.hotels.activities;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.android.hotels.R;
import com.example.android.hotels.data.OrderContract;
import com.example.android.hotels.data.OrderContract.OrderEntry;

public class SearchListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private OrderCursorAdapter mCursorAdapter;
    private static final int ORDER_LOADER = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        ListView OrderListView = (ListView) findViewById(R.id.list);

        //PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);

        //petListView.setAdapter(adapter);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        //View emptyView = findViewById(R.id.empty_view);
        //OrderListView.setEmptyView(emptyView);

        mCursorAdapter = new OrderCursorAdapter(this, null);
        OrderListView.setAdapter(mCursorAdapter);
        getSupportLoaderManager().initLoader(ORDER_LOADER, null, this);
        //Setup the item click listener

    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                OrderEntry.COLUMN_USER_ID,
                OrderEntry.COLUMN_ORDER_ID };
        return new CursorLoader(this,
                OrderEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
