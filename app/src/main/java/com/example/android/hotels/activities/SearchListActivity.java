package com.example.android.hotels.activities;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.android.hotels.R;
import com.example.android.hotels.data.OrderContract;
import com.example.android.hotels.data.OrderContract.OrderEntry;

public class SearchListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private OrderCursorAdapter mCursorAdapter;
    private static final int ORDER_LOADER = 0;

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        String user_id = getIntent().getStringExtra("User_id");
        String order_id = getIntent().getStringExtra("Order_id");

        ListView OrderListView = (ListView) findViewById(R.id.list);

        String whereClause = OrderEntry.COLUMN_USER_ID + " =? AND " + OrderEntry.COLUMN_ORDER_ID + " =?";
        String[] selectionArgs = new String[]{user_id, order_id};
        String[] projection = {
                OrderEntry.COLUMN_HOTEL_ID,
                OrderEntry.COLUMN_USER_ID,
                OrderEntry.COLUMN_ORDER_ID,
                OrderEntry.COLUMN_NUMBER_OF_SINGLE,
                OrderEntry.COLUMN_NUMBER_OF_DUAL,
                OrderEntry.COLUMN_NUMBER_OF_QUAD,
                OrderEntry.COLUMN_CHECK_IN_DATE,
                OrderEntry.COLUMN_CHECK_OUT_DATE,
                OrderEntry.COLUMN_TOTAL_PRICE
        };
        Cursor cursor = getContentResolver().query(OrderEntry.CONTENT_URI, projection , whereClause, selectionArgs, null);
        mCursorAdapter = new OrderCursorAdapter(this, cursor);
        OrderListView.setAdapter(mCursorAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                OrderEntry.COLUMN_HOTEL_ID,
                OrderEntry.COLUMN_USER_ID,
                OrderEntry.COLUMN_ORDER_ID,
                OrderEntry.COLUMN_NUMBER_OF_SINGLE,
                OrderEntry.COLUMN_NUMBER_OF_DUAL,
                OrderEntry.COLUMN_NUMBER_OF_QUAD,
                OrderEntry.COLUMN_CHECK_IN_DATE,
                OrderEntry.COLUMN_CHECK_OUT_DATE,
                OrderEntry.COLUMN_TOTAL_PRICE
        };
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
