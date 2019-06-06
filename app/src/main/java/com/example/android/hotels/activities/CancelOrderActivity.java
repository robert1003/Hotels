package com.example.android.hotels.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.data.OrderContract.OrderEntry;
public class CancelOrderActivity extends AppCompatActivity {
    private EditText mUser_id, mOrder_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        mUser_id = (EditText) findViewById(R.id.cancel_order_user_id);
        mOrder_id = (EditText) findViewById(R.id.cancel_order_order_id);

        Button cancel = (Button) findViewById(R.id.cancel_order_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder();
            }
        });
    }

    private void cancelOrder() {
        String user_id = mUser_id.getText().toString();
        String order_id = mOrder_id.getText().toString();

        // try to cancel order (erase order from database) here
        String whereClause = OrderEntry.COLUMN_USER_ID + " =? AND " + OrderEntry.COLUMN_ORDER_ID + " =?";
        String[] selectionArgs = new String[]{user_id, order_id};
        int rowsDeleted = getContentResolver().delete(OrderEntry.CONTENT_URI, whereClause, selectionArgs);
        if(rowsDeleted != 0) {
            Dialog dialog = new Dialog(CancelOrderActivity.this);
            dialog.setContentView(R.layout.success_cancel_order);
            dialog.show();
        } else {
            Dialog dialog = new Dialog(CancelOrderActivity.this);
            dialog.setContentView(R.layout.failed_cancel_order);
            dialog.show();
        }
    }
}
