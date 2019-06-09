package com.example.android.hotels;

import android.content.Context;
import android.database.Cursor;

import com.example.android.hotels.data.OrderContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private Utils() {}

    public static int[] getOrdersInATimeRange(Context context, int hotel_id, int start_date, int end_date) {
        int[] room_cnt = new int[3];
        room_cnt[0] = room_cnt[1] = room_cnt[2] = 0;

        String whereClause = OrderContract.OrderEntry.COLUMN_HOTEL_ID + " =? AND " +
                OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE + " <=? AND " +
                OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE + " >?";
        String[] selectionArgs = new String[]{};
        String[] projection = {
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_SINGLE,
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_DUAL,
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_QUAD,
        };
        Cursor cursor = context.getContentResolver().query(OrderContract.OrderEntry.CONTENT_URI, projection , whereClause, selectionArgs, null);
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                room_cnt[0] += cursor.getInt(0);
                room_cnt[1] += cursor.getInt(1);
                room_cnt[2] += cursor.getInt(2);
            } while (cursor.moveToNext());
        }

        whereClause = OrderContract.OrderEntry.COLUMN_HOTEL_ID + " =? AND " +
                OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE + " <? AND " +
                OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE + " >=?";
        selectionArgs = new String[]{};
        cursor = context.getContentResolver().query(OrderContract.OrderEntry.CONTENT_URI, projection , whereClause, selectionArgs, null);
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                room_cnt[0] += cursor.getInt(0);
                room_cnt[1] += cursor.getInt(1);
                room_cnt[2] += cursor.getInt(2);
            } while (cursor.moveToNext());
        }

        whereClause = OrderContract.OrderEntry.COLUMN_HOTEL_ID + " =? AND " +
                OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE + " <=? AND " +
                OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE + " >=?";
        selectionArgs = new String[]{};
        cursor = context.getContentResolver().query(OrderContract.OrderEntry.CONTENT_URI, projection , whereClause, selectionArgs, null);
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                room_cnt[0] -= cursor.getInt(0);
                room_cnt[1] -= cursor.getInt(1);
                room_cnt[2] -= cursor.getInt(2);
            } while (cursor.moveToNext());
        }

        return room_cnt;
    }
    public static int parseDate(String raw_date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = formatter.parse(raw_date);
            return (int)(date.getTime() / 1000 / 60 / 60 / 24);
        } catch(ParseException e) {
            throw e;
        }
    }
}
