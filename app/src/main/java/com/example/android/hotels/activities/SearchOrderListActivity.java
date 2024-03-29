package com.example.android.hotels.activities;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.data.OrderContract.OrderEntry;

public class SearchOrderListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private OrderCursorAdapter mCursorAdapter;
    private static final int ORDER_LOADER = 0;

    /**
     * Display error message to the user
     *
     * @param message the message to show
     */
    private void showMessage(String message) {
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.failed_search_order, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_search_order_text);
        textView.setText(message);
        Dialog dialog = new Dialog(SearchOrderListActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order_list);

        // Get user id and order id from SearchOrderActivity.
        String user_id = getIntent().getStringExtra("User_id");
        String order_id = getIntent().getStringExtra("Order_id");

        // variable for order list.
        ListView OrderListView = (ListView) findViewById(R.id.search_order_list);

        // description for query.
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

        // the cursor from query and set it to the list view
        Cursor cursor = getContentResolver().query(OrderEntry.CONTENT_URI, projection , whereClause, selectionArgs, null);
        mCursorAdapter = new OrderCursorAdapter(this, cursor);
        OrderListView.setAdapter(mCursorAdapter);
    }

    /**
     * Initialize loader
     *
     * @param id loader's id
     * @param bundle for activity's data
     * @return new cursor loader for
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
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

    /**
     * Finish onload
     *
     * @param loader the loader of cursor
     * @param cursor new cursor
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    /**
     * Reset loader
     *
     * @param loader the loader of cursor
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
