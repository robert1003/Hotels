package com.example.android.hotels.activities;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.data.Hotel;
import com.example.android.hotels.data.HotelList;
import com.example.android.hotels.data.OrderContract.OrderEntry;

public class CancelOrderActivity extends AppCompatActivity {
    /**
     * Variable for user inputs
     */
    private EditText mUser_id, mOrder_id;

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        mUser_id = (EditText) findViewById(R.id.cancel_order_user_id);
        mOrder_id = (EditText) findViewById(R.id.cancel_order_order_id);

        Button cancel = (Button) findViewById(R.id.cancel_order_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
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
        View view = factory.inflate(R.layout.failed_cancel_order, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_cancel_order_text);
        textView.setText(message);
        Dialog dialog = new Dialog(CancelOrderActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    /**
     * Cancel order
     */
    private void cancelOrder() {
        int user_id, order_id, hotel_id;

        // try to parse userID
        try {
            user_id = Integer.parseInt(mUser_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_userID_format));
            return;
        }

        // try to parse orderID
        try {
            order_id = Integer.parseInt(mOrder_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_orderID_format));
            return;
        }

        // find hotelID
        String whereClause = OrderEntry.COLUMN_USER_ID + " =? AND " + OrderEntry.COLUMN_ORDER_ID + " =?";
        String[] selectionArgs = new String[]{String.valueOf(user_id), String.valueOf(order_id)};
        String[] projection = {
                OrderEntry.COLUMN_HOTEL_ID,
        };

        // the cursor from query
        Cursor cursor = getContentResolver().query(OrderEntry.CONTENT_URI, projection , whereClause, selectionArgs, null);

        // data doesn't exist
        if (cursor.getCount() == 0) {
            showMessage(getString(R.string.failed_cancel_order));
            return;
        } else {
            cursor.moveToFirst();
            hotel_id = cursor.getInt(0);
        }

        // cancel order (erase order from database) here
        whereClause = OrderEntry.COLUMN_USER_ID + " =? AND "
                + OrderEntry.COLUMN_ORDER_ID + " =?";
        selectionArgs = new String[] {
                Integer.toString(user_id),
                Integer.toString(order_id)
        };
        getContentResolver().delete(
                OrderEntry.CONTENT_URI,
                whereClause,
                selectionArgs
        );

        // update and show message
        HotelList.hotels.get(hotel_id).count -= 1;
        showMessage(getString(R.string.success_cancel_order));
        return;
    }
}
