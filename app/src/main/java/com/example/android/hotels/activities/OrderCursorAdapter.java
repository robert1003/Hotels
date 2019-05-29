package com.example.android.hotels.activities;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.data.OrderContract.OrderEntry;
public class OrderCursorAdapter extends CursorAdapter {

    public OrderCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.search_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView userIdTextView = (TextView) view.findViewById(R.id.user_id);
        TextView orderIdTextView = (TextView) view.findViewById(R.id.order_id);

        int userIdColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_USER_ID);
        int orderIdColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_ORDER_ID);

        Integer user_id = cursor.getInt(userIdColumnIndex);
        Integer order_id = cursor.getInt(orderIdColumnIndex);


        userIdTextView.setText(user_id);
        orderIdTextView.setText(order_id);
    }
}
