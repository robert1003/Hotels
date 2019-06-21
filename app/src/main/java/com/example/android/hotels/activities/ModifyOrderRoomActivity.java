package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.Utils;
import com.example.android.hotels.data.HotelList;
import com.example.android.hotels.data.OrderContract.OrderEntry;
import com.example.android.hotels.data.OrderDbHelper;

public class ModifyOrderRoomActivity extends AppCompatActivity {
    private EditText mUser_id, mOrder_id, mSingle, mDual, mQuad, mCheck_in, mCheck_out;
    private OrderDbHelper mDbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order_room);

        mUser_id = (EditText) findViewById(R.id.modify_order_room_user_id);
        mOrder_id = (EditText) findViewById(R.id.modify_order_room_order_id);
        mSingle = (EditText) findViewById(R.id.modify_order_room_single);
        mDual = (EditText) findViewById(R.id.modify_order_room_dual);
        mQuad = (EditText) findViewById(R.id.modify_order_room_quad);
        mCheck_in = findViewById(R.id.modify_order_date_check_in_date);
        mCheck_out = findViewById(R.id.modify_order_date_check_out_date);
        Button button = (Button) findViewById(R.id.modify_order_room_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyRoom();
            }
        });
    }

    /**
     * Display error message to the user
     *
     * @param message the message to show
     */
    private void showMessage(String message) {
        LayoutInflater factory = getLayoutInflater();
        View view = factory.inflate(R.layout.failed_modify_order, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_modify_order_text);
        textView.setText(message);
        Dialog dialog = new Dialog(ModifyOrderRoomActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    private void modifyRoom() {
        String user_id = mUser_id.getText().toString();
        String order_id = mOrder_id.getText().toString();
        String single = mSingle.getText().toString();
        String dual = mDual.getText().toString();
        String quad = mQuad.getText().toString();
        String check_in_date = mCheck_in.getText().toString();
        String check_out_date = mCheck_out.getText().toString();
        // try to modify room here
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = OrderEntry.COLUMN_USER_ID + " =? AND " + OrderEntry.COLUMN_ORDER_ID + " =?";
        String[] selectionArgs = new String[]{user_id, order_id};
        String[] projection = {
            OrderEntry.COLUMN_USER_ID,
            OrderEntry.COLUMN_ORDER_ID,
            OrderEntry.COLUMN_CHECK_IN_DATE,
            OrderEntry.COLUMN_CHECK_OUT_DATE,
            OrderEntry.COLUMN_NUMBER_OF_SINGLE,
            OrderEntry.COLUMN_NUMBER_OF_DUAL,
            OrderEntry.COLUMN_NUMBER_OF_QUAD
        };

        Cursor cursor = getContentResolver().query(OrderEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            Dialog dialog = new Dialog(ModifyOrderRoomActivity.this);
            dialog.setContentView(R.layout.failed_modify_order_id_not_exist);
            dialog.show();
            return;
        }

        ContentValues values = new ContentValues();
        // assign value to rooms and put them in content
        int i_single = 0;
        if (!TextUtils.isEmpty(single))
            i_single = Integer.parseInt(single);
        values.put(OrderEntry.COLUMN_NUMBER_OF_SINGLE, i_single);
        int i_double = 0;
        if (!TextUtils.isEmpty(dual))
            i_double = Integer.parseInt(dual);
        values.put(OrderEntry.COLUMN_NUMBER_OF_DUAL, i_double);
        int i_quad = 0;
        if (!TextUtils.isEmpty(quad))
            i_quad = Integer.parseInt(quad);
        values.put(OrderEntry.COLUMN_NUMBER_OF_QUAD, i_quad);
        int single_column = cursor.getColumnIndex(OrderEntry.COLUMN_NUMBER_OF_SINGLE);
        int old_single = cursor.getInt(single_column);
        int double_column = cursor.getColumnIndex(OrderEntry.COLUMN_NUMBER_OF_DUAL);
        int old_double = cursor.getInt(double_column);
        int quad_column = cursor.getColumnIndex(OrderEntry.COLUMN_NUMBER_OF_QUAD);
        int old_quad = cursor.getInt(quad_column);
        int check_in_column = cursor.getColumnIndex(OrderEntry.COLUMN_CHECK_IN_DATE);
        String old_check_in_date = cursor.getString(check_in_column);
        int check_out_column = cursor.getColumnIndex(OrderEntry.COLUMN_CHECK_OUT_DATE);
        String old_check_out_date = cursor.getString(check_out_column);
        //Log.i("fuck", quad);
        if (i_single > old_single || i_double > old_double || i_quad > old_quad) {
            showMessage("Cannot Order More Room");
            return;
        }
        if(check_in_date.compareTo(old_check_in_date) < 0 || check_out_date.compareTo(old_check_out_date) > 0){
            showMessage("fuck");
            return;
        }
        if(check_in_date.compareTo(check_out_date) >= 0){
            showMessage("fuck");
            return;
        }
        values.put(OrderEntry.COLUMN_CHECK_IN_DATE, check_in_date);
        values.put(OrderEntry.COLUMN_CHECK_OUT_DATE, check_out_date);
        int rowsUpdated = getContentResolver().update(
                OrderEntry.CONTENT_URI,
                values,
                selection,
                selectionArgs
        );
        if(rowsUpdated != 0) {
            Dialog dialog = new Dialog(ModifyOrderRoomActivity.this);
            dialog.setContentView(R.layout.success_modify_order_room);
            dialog.show();
        }
        return;
    }

}
