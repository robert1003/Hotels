package com.example.android.hotels;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.android.hotels.data.OrderContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private Utils() {}

    public static int[] getOrdersInATimeRange(Context context, int hotel_id, String start_date, String end_date) {
        int[] room_cnt = new int[3];
        room_cnt[0] = room_cnt[1] = room_cnt[2] = 0;

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
