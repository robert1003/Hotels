package com.example.android.hotels.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.net.Uri;
import com.example.android.hotels.data.OrderContract.OrderEntry;

public class OrderProvider extends ContentProvider {


    private OrderDbHelper mDbHelper;
    private static final UriMatcher sUriMather = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ORDERS = 100;
    private static final int ORDERS_ID = 101;

    @Override
    public boolean onCreate() {
        mDbHelper = new OrderDbHelper(getContext());
        sUriMather.addURI(OrderContract.CONTENT_AUTHORITY, "orders", ORDERS);
        sUriMather.addURI(OrderContract.CONTENT_AUTHORITY, "orders/#", ORDERS_ID);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sUriMather.match(uri);
        switch (match) {
            case ORDERS:
                cursor = database.query(OrderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ORDERS_ID:
                selection =
        }
    }

    @androidx.annotation.Nullable
    @Override
    public String getType(@androidx.annotation.NonNull Uri uri) {
        return null;
    }

    @androidx.annotation.Nullable
    @Override
    public Uri insert(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
        return 0;
    }
}
