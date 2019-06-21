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
    private EditText mUser_id, mOrder_id;

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

    private void searchOrder() {
        String user_id = mUser_id.getText().toString();
        String order_id = mOrder_id.getText().toString();

        Intent intent = new Intent(SearchOrderActivity.this, SearchListActivity.class);
        intent.putExtra("User_id", user_id);
        intent.putExtra("Order_id", order_id);
        startActivity(intent);
    }
}
