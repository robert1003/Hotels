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
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = sUriMather.match(uri);
        switch (match){
            case ORDERS:
                return insertOrder(uri, values);
        }
        return null;
    }
    private Uri insertOrder(Uri uri, ContentValues values){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(OrderEntry.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMather.match(uri);
        int rowsDeleted;
        switch (match) {
            case ORDERS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(OrderEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
