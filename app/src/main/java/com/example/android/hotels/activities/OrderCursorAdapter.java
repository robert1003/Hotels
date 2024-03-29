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
    /**
     *
     * @param context for the activity that called this method
     * @param c cursor for the list view
     */
    public OrderCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 );
    }

    /**
     * Put cursor into list view
     *
     * @param context for the activity that called this method
     * @param cursor cursor for the list view
     * @param parent the parent viewgroup
     * @return a View
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.search_list_item, parent, false);
    }

    /**
     * Bind view with cursor
     *
     * @param view the view to put in
     * @param context what to put in the view
     * @param cursor cursor for the list view
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Variables for text view
        TextView hotelIdTextView = (TextView) view.findViewById(R.id.search_list_item_hotel_id);
        TextView numberOfSingle = (TextView) view.findViewById(R.id.search_list_item_number_of_single);
        TextView numberOfDouble = (TextView) view.findViewById(R.id.search_list_item_number_of_double);
        TextView numberOfQuad = (TextView) view.findViewById(R.id.search_list_item_number_of_quad);
        TextView checkInDate = (TextView) view.findViewById(R.id.search_list_item_check_in_date);
        TextView checkOutDate = (TextView) view.findViewById(R.id.search_list_item_check_out_date);
        TextView totalPrice = (TextView) view.findViewById(R.id.search_list_item_total_price);

        // Variables for index of columns
        int hotelIdColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_HOTEL_ID);
        int singleColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_NUMBER_OF_SINGLE);
        int doubleColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_NUMBER_OF_DUAL);
        int quadColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_NUMBER_OF_QUAD);
        int checkInColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_CHECK_IN_DATE);
        int checkOutColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_CHECK_OUT_DATE);
        int totalPriceColumnIndex = cursor.getColumnIndex(OrderEntry.COLUMN_TOTAL_PRICE);

        // Variables for content
        Integer hotel_id = cursor.getInt(hotelIdColumnIndex);
        Integer number_of_single = cursor.getInt(singleColumnIndex);
        Integer number_of_double = cursor.getInt(doubleColumnIndex);
        Integer number_of_quad = cursor.getInt(quadColumnIndex);
        String check_in_date = cursor.getString(checkInColumnIndex);
        String check_out_date = cursor.getString(checkOutColumnIndex);
        Integer total_price = cursor.getInt(totalPriceColumnIndex);

        // Set text into list
        hotelIdTextView.setText(String.valueOf(hotel_id));
        numberOfSingle.setText(String.valueOf(number_of_single));
        numberOfDouble.setText(String.valueOf(number_of_double));
        numberOfQuad.setText(String.valueOf(number_of_quad));
        checkInDate.setText(check_in_date);
        checkOutDate.setText(check_out_date);
        totalPrice.setText(String.valueOf(total_price));
    }
}
