package com.example.android.hotels.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class OrderContract {
    private OrderContract(){}
    public static final String CONTENT_AUTHORITY = "com.example.android.hotels";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ORDERS = "orders";
    public static final class OrderEntry implements BaseColumns {

        /** Name of database table for orders */
        public final static String TABLE_NAME = "orders";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ORDERS);

        public final static String COLUMN_ORDER_ID = _ID;
        public final static String COLUMN_USER_ID = "user_id";
        public final static String COLUMN_HOTEL_ID = "hotel_id";
        //public final static String COLUMN_SINGLE = "single";
        public final static String COLUMN_NUMBER_OF_SINGLE = "number_of_single";
        //public final static String COLUMN_DUAL = "dual";
        public final static String COLUMN_NUMBER_OF_DUAL = "number_of_dual";
        //public final static String COLUMN_QUAD = "quad";
        public final static String COLUMN_NUMBER_OF_QUAD = "number_of_quad";
        public final static String COLUMN_CHECK_IN_DATE = "check_in_date";
        public final static String COLUMN_CHECK_OUT_DATE = "check_out_date";
        public final static String COLUMN_TOTAL_PRICE = "total_price";
    }

}
