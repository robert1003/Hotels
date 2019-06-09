package com.example.android.hotels.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

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
                Log.i("database", OrderEntry.TABLE_NAME.toString() + " " + projection.toString() + " " + selection.toString() + " " + selectionArgs.toString());
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

    /**
     * Update three types of rooms
     * specified in the selection and selection arguments (which could be 0 or 1 or more rooms).
     * Return the number of rows that were successfully updated.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) throws IllegalArgumentException {
        if (values.containsKey(OrderEntry.COLUMN_NUMBER_OF_SINGLE)) {
            Integer num_single = values.getAsInteger(OrderEntry.COLUMN_NUMBER_OF_SINGLE);
            if (num_single == null || num_single < 0) {
                throw new IllegalArgumentException("Numbers of Singles not valid");
            }
        }

        if (values.containsKey(OrderEntry.COLUMN_NUMBER_OF_DUAL)) {
            Integer num_dual = values.getAsInteger(OrderEntry.COLUMN_NUMBER_OF_DUAL);
            if (num_dual == null || num_dual < 0) {
                throw new IllegalArgumentException("Numbers of Duals not valid");
            }
        }

        if (values.containsKey(OrderEntry.COLUMN_NUMBER_OF_QUAD)) {
            Integer num_quad = values.getAsInteger(OrderEntry.COLUMN_NUMBER_OF_QUAD);
            if (num_quad == null || num_quad < 0) {
                throw new IllegalArgumentException("Numbers of Quads not valid");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(OrderEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }
}
