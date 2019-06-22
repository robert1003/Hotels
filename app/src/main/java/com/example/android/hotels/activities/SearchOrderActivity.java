package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.R;
import com.example.android.hotels.data.OrderContract.OrderEntry;
public class SearchOrderActivity extends AppCompatActivity {
    /**
     * Variable for user inputs
     */
    private EditText mUser_id, mOrder_id;

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);

        mUser_id = (EditText) findViewById(R.id.search_order_user_id);
        mOrder_id = (EditText) findViewById(R.id.search_order_order_id);

        Button cancel = (Button) findViewById(R.id.search_order_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchOrder();
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
        View view = factory.inflate(R.layout.failed_search_order, null);
        TextView textView = (TextView) view.findViewById(R.id.failed_search_order_text);
        textView.setText(message);
        Dialog dialog = new Dialog(SearchOrderActivity.this);
        dialog.setContentView(view);
        dialog.show();
        return;
    }

    private void searchOrder() {
        /**
         *  Variable for user id and order id
         */
        int user_id, order_id;

        // try to parse userID
        try {
            user_id = Integer.parseInt(mUser_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_userID_format));
            return;
        }

        // try to parse orderID
        try {
            order_id = Integer.parseInt(mOrder_id.getText().toString());
        } catch (NumberFormatException e) {
            showMessage(getString(R.string.invalid_orderID_format));
            return;
        }

        // check if order is in dataBase


        // search order in dataBase
        Intent intent = new Intent(SearchOrderActivity.this, SearchListActivity.class);
        intent.putExtra("User_id", Integer.toString(user_id));
        intent.putExtra("Order_id", Integer.toString(order_id));
        startActivity(intent);
    }
}
