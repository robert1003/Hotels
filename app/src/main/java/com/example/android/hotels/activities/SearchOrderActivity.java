package com.example.android.hotels.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.R;

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

        // try to search order
        if(Integer.parseInt(user_id) <= 100) {
            LayoutInflater factory = getLayoutInflater();
            View view = factory.inflate(R.layout.success_search_order, null);
            TextView textView = (TextView) view.findViewById(R.id.success_search_order_text);
            textView.setText("Searchhhhhhhhhh");
            Dialog dialog = new Dialog(SearchOrderActivity.this);
            dialog.setContentView(view);
            dialog.show();
        } else {
            Dialog dialog = new Dialog(SearchOrderActivity.this);
            dialog.setContentView(R.layout.failed_search_order);
            dialog.show();
        }

    }
}
