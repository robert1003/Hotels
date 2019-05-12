package com.example.android.hotels.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.hotels.R;

public class ModifyOrderRoomActivity extends AppCompatActivity {
    private EditText mUser_id, mOrder_id, mSingle, mDual, mQuad;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order_room);

        mUser_id = (EditText) findViewById(R.id.modify_order_room_user_id);
        mOrder_id = (EditText) findViewById(R.id.modify_order_room_order_id);
        mSingle = (EditText) findViewById(R.id.modify_order_room_single);
        mDual = (EditText) findViewById(R.id.modify_order_room_dual);
        mQuad = (EditText) findViewById(R.id.modify_order_room_quad);

        Button button = (Button) findViewById(R.id.modify_order_room_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyRoom();
            }
        });
    }

    private void modifyRoom() {
        String user_id = mUser_id.getText().toString();
        String order_id = mOrder_id.getText().toString();
        String single = mSingle.getText().toString();
        String dual = mDual.getText().toString();
        String quad = mQuad.getText().toString();

        // try to modify room here
        if(Integer.parseInt(user_id) <= 100) {
            LayoutInflater factory = getLayoutInflater();
            View view = factory.inflate(R.layout.success_modify_order_room, null);
            TextView textView = (TextView) view.findViewById(R.id.success_modify_order_room_text);
            textView.setText("Yoooooooooooooo");
            Dialog dialog = new Dialog(ModifyOrderRoomActivity.this);
            dialog.setContentView(view);
            dialog.show();
        } else if(Integer.parseInt(user_id) <= 200) {
            Dialog dialog = new Dialog(ModifyOrderRoomActivity.this);
            dialog.setContentView(R.layout.failed_modify_order_id_not_exist);
            dialog.show();
        } else {
            Dialog dialog = new Dialog(ModifyOrderRoomActivity.this);
            dialog.setContentView(R.layout.failed_modify_order_room_too_many);
            dialog.show();
        }
    }

}
