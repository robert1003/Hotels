package com.example.android.hotels.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.ScriptC;
import android.util.Log;
import android.widget.TextView;

import com.example.android.hotels.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HotelList {
    public static ArrayList<Hotel> hotels = new ArrayList<>();

    private static boolean isInit = false;

    // helper function
    private static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("HotelList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static int orderCount(Context context, int hotelID) {
        String whereClause = OrderContract.OrderEntry.COLUMN_HOTEL_ID + " =?";
        String[] selectionArgs = new String[]{
                String.valueOf(hotelID)
        };
        String[] projection = {
                OrderContract.OrderEntry.COLUMN_ORDER_ID,
        };

        // the cursor from query
        Cursor cursor = context.getContentResolver().query(
                OrderContract.OrderEntry.CONTENT_URI,
                projection,
                whereClause,
                selectionArgs,
                null
        );

        return cursor.getCount();
    }

    // init hotels
    public static void init(Context context) {
        if(isInit) return;
        isInit = true;
        try {
            JSONArray hotelArray = new JSONArray(loadJSONFromAsset(context));
            for(int i =  0 ; i < hotelArray.length() ; ++i) {
                JSONObject parser = hotelArray.getJSONObject(i);
                int hotelID = Integer.parseInt(parser.getString("HotelID"));
                int hotelStar = Integer.parseInt(parser.getString("HotelStar"));
                String locality = parser.getString("Locality");
                String streetAddress = parser.getString("Street-Address");
                JSONArray roomArray = parser.getJSONArray("Rooms");
                int singlePrice = Integer.parseInt(roomArray.getJSONObject(0).getString("RoomPrice"));
                int singleCount = Integer.parseInt(roomArray.getJSONObject(0).getString("Number"));
                int dualPrice = Integer.parseInt(roomArray.getJSONObject(1).getString("RoomPrice"));
                int dualCount = Integer.parseInt(roomArray.getJSONObject(1).getString("Number"));
                int quadPrice = Integer.parseInt(roomArray.getJSONObject(2).getString("RoomPrice"));
                int quadCount = Integer.parseInt(roomArray.getJSONObject(2).getString("Number"));



                Hotel hotel = new Hotel(hotelID, hotelStar, locality, streetAddress,
                        singlePrice, singleCount, dualPrice, dualCount, quadPrice, quadCount, orderCount(context, i));
                hotels.add(hotel);
            }

        } catch (JSONException e) {
            Log.e("HotelList", "Problem parsing the hotel JSON results", e);
        }
    }
    // private constructor
    private HotelList() {}

}