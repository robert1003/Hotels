package com.example.android.hotels;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.example.android.hotels.data.OrderContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Utils {

    private Utils() {}

    private static void showCursor(Cursor cursor) {
        try {
            String s = "";

            s += cursor.getColumnName(0) + " " +
                    cursor.getColumnName(1) + " " +
                    cursor.getColumnName(2) + " " +
                    cursor.getColumnName(3) + " " +
                    cursor.getColumnName(4) + " ";
            s += "\n";


            if (cursor.moveToFirst()) {
                //Loop through the table rows
                do {
                    s += cursor.getInt(0) + " ";
                    s += cursor.getInt(1) + " ";
                    s += cursor.getInt(2) + " ";
                    s += cursor.getString(3) + " ";
                    s += cursor.getString(4) + " ";
                    s += "\n";
                } while (cursor.moveToNext());
            }

            Log.i("utils", s);
        } finally {
            cursor.close();
        }
    }

    private class Mypair implements Comparable<Mypair> {
        String first, second;

        public Mypair(String first, String second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Mypair p) {
            int a = first.compareTo(p.first), b = second.compareTo(p.second);
            if (a < 0) return -1;
            else if (a == 0) {
                if (b < 0) return -1;
                else if (b == 0) return 0;
                else return 1;
            }
            else return 1;
        }
    }

    public static int[] getAvailableRoomInATimeRange(Context context, int hotel_id, String start_date, String end_date) {
        int[] room_cnt = new int[3];
        room_cnt[0] = room_cnt[1] = room_cnt[2] = 0;

        Log.i("utils", start_date + " " + end_date);

        String whereClause = OrderContract.OrderEntry.COLUMN_HOTEL_ID + " =? AND "
                + OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE + " <? AND "
                + OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE + " >?";

        String[] selectionArgs = new String[]{
                Integer.toString(hotel_id),
                end_date,
                start_date
        };
        String[] projection = {
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_SINGLE,
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_DUAL,
                OrderContract.OrderEntry.COLUMN_NUMBER_OF_QUAD,
                OrderContract.OrderEntry.COLUMN_CHECK_IN_DATE,
                OrderContract.OrderEntry.COLUMN_CHECK_OUT_DATE
        };
        Cursor cursor = context.getContentResolver().query(
                OrderContract.OrderEntry.CONTENT_URI,
                projection,
                whereClause,
                selectionArgs,
                null
        );
        showCursor(cursor);

        ArrayList<Mypair> segments = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                segments.add(new Mypair(cursor.getString(3),
                                cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        Collections.sort(segments);

        for (Pair<String, String> a : segments) {
            Log.i("arraylistttt", a.first + " " + a.second);
        }


        room_cnt[0] = room_cnt[1] = room_cnt[2] = 9999;

        return room_cnt;
    }

    public static String parseDate(String raw_date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatter.parse(raw_date);
            return raw_date;
        } catch(ParseException e) {
            throw e;
        }
    }
}
