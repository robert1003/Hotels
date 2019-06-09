package com.example.android.hotels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private Utils() {}

    public static int[] getOrdersInATimeRange(int hotel_id, int start_date, int end_date) {
        int[] room_cnt = new int[3];
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
