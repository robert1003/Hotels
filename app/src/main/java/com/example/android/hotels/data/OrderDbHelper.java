package com.example.android.hotels.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.hotels.data.OrderContract.OrderEntry;

public class OrderDbHelper extends SQLiteOpenHelper {
    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "Order.db";

    /**
     * Database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link OrderDbHelper}.
     *
     * @param context of the app
     */
    public OrderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the orders table
        String SQL_CREATE_ORDERS_TABLE =  "CREATE TABLE " + OrderEntry.TABLE_NAME + " ("
                + OrderEntry.COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderEntry.COLUMN_USER_ID + " INTEGER NOT NULL, "
                + OrderEntry.COLUMN_HOTEL_ID + " INTEGER DEFAULT 0, "
                + OrderEntry.COLUMN_NUMBER_OF_SINGLE + " INTEGER DEFAULT 0, "
                + OrderEntry.COLUMN_NUMBER_OF_DUAL + " INTEGER DEFAULT 0, "
                + OrderEntry.COLUMN_NUMBER_OF_QUAD + " INTEGER DEFAULT 0, "
                + OrderEntry.COLUMN_CHECK_IN_DATE + " TEXT NOT NULL, "
                + OrderEntry.COLUMN_CHECK_OUT_DATE + " TEXT NOT NULL, "
                + OrderEntry.COLUMN_TOTAL_PRICE + " INTEGER );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ORDERS_TABLE);
    }

    /**
     * Upgrade database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}

