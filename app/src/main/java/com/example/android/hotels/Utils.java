package com.example.android.hotels;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.example.android.hotels.data.Hotel;
import com.example.android.hotels.data.HotelList;
import com.example.android.hotels.data.OrderContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.lang.Math;

import static java.lang.Math.max;

public class Utils {

    private Utils() {}

    /**
     * a hepler function to use to show context of the query result
     *
     * @param cursor the query result
     */
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

    /**
     * A helper class
     */
    private static class Mytuple implements Comparable<Mytuple> {
        String date;
        int id;
        boolean is_check_in;

        public Mytuple(String date, int id, boolean is_check_in) {
            this.date = date;
            this.id = id;
            this.is_check_in = is_check_in;
        }

        @Override
        public int compareTo(Mytuple p) {
            return date.compareTo(p.date);
        }
    }

    /**
     * A helper class
     */
    private static class Myclass {
        String check_in_date, check_out_date;
        int single, dual, quad;

        public Myclass(String check_in_date, String check_out_date, int single, int dual, int quad) {
            this.check_in_date = check_in_date;
            this.check_out_date = check_out_date;
            this.single = single;
            this.dual = dual;
            this.quad = quad;
        }
    }

    /**
     * Get available rooms in a time range
     *
     * @param context the activity that called this method
     * @param hotel_id which hotel to ask
     * @param start_date start from
     * @param end_date end at
     * @return int array, id 0 for single, id 1 for dual, id 2 for quad
     */
    public static int[] getAvailableRoomInATimeRange(Context context, int hotel_id,
                                                     String start_date, String end_date) {
        int[] room_cnt = new int[3];
        room_cnt[0] = room_cnt[1] = room_cnt[2] = 0;

        Log.i("utils", start_date + " " + end_date);

        // get all orders that intersect with this time range
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
        // showCursor(cursor);

        // find the max number of occupied room
        Log.i("max", Integer.toString(cursor.getCount()));
        ArrayList<Mytuple> segments = new ArrayList<>();
        ArrayList<Myclass> results = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                segments.add(new Mytuple(cursor.getString(3), results.size(), true));
                segments.add(new Mytuple(cursor.getString(4), results.size(), false));
                results.add(new Myclass(
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2)
                ));
            } while (cursor.moveToNext());
        }
        Collections.sort(segments);

        int sum0 = 0, sum1 = 0, sum2 = 0, max0 = 0, max1 = 0, max2 = 0;
        for (int i = 0 ; i < segments.size() ; ++i) {
            do {
                if(segments.get(i).is_check_in) {
                    Log.i("max", Integer.toString(i) + " " + segments.get(i).date + " in");
                    sum0 += results.get(segments.get(i).id).single;
                    sum1 += results.get(segments.get(i).id).dual;
                    sum2 += results.get(segments.get(i).id).quad;
                } else {
                    Log.i("max", Integer.toString(i) + " " + segments.get(i).date + " out");
                    sum0 -= results.get(segments.get(i).id).single;
                    sum1 -= results.get(segments.get(i).id).dual;
                    sum2 -= results.get(segments.get(i).id).quad;
                }
                i++;
            }
            while (i < segments.size() &&
                    segments.get(i).date.compareTo(segments.get(i - 1).date) == 0);
            i--;

            max0 = max(max0, sum0);
            max1 = max(max1, sum1);
            max2 = max(max2, sum2);

            Log.i("max", Integer.toString(i));
            Log.i("max", Integer.toString(max0) + " " + Integer.toString(max1) + " " + Integer.toString(max2));
        }

        Log.i("max", Integer.toString(max0) + " " + Integer.toString(max1) + " " + Integer.toString(max2));

        room_cnt[0] = HotelList.hotels.get(hotel_id).singleCount - max0;
        room_cnt[1] = HotelList.hotels.get(hotel_id).dualCount - max1;
        room_cnt[2] = HotelList.hotels.get(hotel_id).quadCount - max2;

        return room_cnt;
    }

    /**
     * Check if date is valid
     *
     * @param raw_date the date to check
     * @return formatted date
     * @throws ParseException if fail, it will throw exception
     */
    public static String parseDate(String raw_date) throws ParseException {
        try {
            // parse date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            Date date = formatter.parse(raw_date);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(date);
            return strDate;
        } catch(ParseException e) {
            throw e;
        }
    }

    /**
     * Return raw_date_2 -  raw_date_1
     *
     * @param raw_date_1 date first
     * @param raw_date_2 date second
     * @return result
     */
    public static int dateDiff(String raw_date_1, String raw_date_2) {
        try {
            // parse date
            Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(raw_date_1);
            Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(raw_date_2);

            // calculate diff
            long diff = d2.getTime() - d1.getTime();

            // change it into day and return
            return (int) (diff / (1000 * 60 * 60 * 24));
        } catch(ParseException e) { return 0; }
    }
}
