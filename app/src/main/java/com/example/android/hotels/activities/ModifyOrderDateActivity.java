package com.example.android.hotels.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.R;

public class ModifyOrderDateActivity extends AppCompatActivity  {
    private EditText mUser_id, mOrder_id, mCheck_in_date, mCheck_out_date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order_date);

        mUser_id = (EditText) findViewById(R.id.modify_order_date_user_id);
        mOrder_id = (EditText) findViewById(R.id.modify_order_date_order_id);
        mCheck_in_date = (EditText) findViewById(R.id.modify_order_date_check_in_date);
        mCheck_out_date = (EditText) findViewById(R.id.modify_order_date_check_out_date);

        Button button = (Button) findViewById(R.id.modify_order_date_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDate();
            }
        });

    }

    private void modifyDate() {
        String user_id = mUser_id.getText().toString();
        String order_id = mOrder_id.getText().toString();
        String check_in_date = mCheck_in_date.getText().toString();
        String check_out_date = mCheck_out_date.getText().toString();

        // try to modify room here
        if(Integer.parseInt(user_id) <= 100) {
            LayoutInflater factory = getLayoutInflater();
            View view = factory.inflate(R.layout.success_modify_order_date, null);
            TextView textView = (TextView) view.findViewById(R.id.success_modify_order_date_text);
            textView.setText("Dateeeeeeeeeeeee");
            Dialog dialog = new Dialog(ModifyOrderDateActivity.this);
            dialog.setContentView(view);
            dialog.show();
        } else if(Integer.parseInt(user_id) <= 200) {
            Dialog dialog = new Dialog(ModifyOrderDateActivity.this);
            dialog.setContentView(R.layout.failed_modify_order_id_not_exist);
            dialog.show();
        } else {
            Dialog dialog = new Dialog(ModifyOrderDateActivity.this);
            dialog.setContentView(R.layout.failed_modify_order_date_longer);
            dialog.show();
        }
    }

}
