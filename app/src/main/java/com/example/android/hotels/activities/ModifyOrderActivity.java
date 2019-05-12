package com.example.android.hotels.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.hotels.MainActivity;
import com.example.android.hotels.R;

public class ModifyOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);

        // click listener on modify_order_room
        TextView roomOrder = (TextView) findViewById(R.id.modify_order_room);
        roomOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyOrderActivity.this, ModifyOrderRoomActivity.class);
                startActivity(intent);
            }
        });

        // click listener on modify_order_date
        TextView dateOrder = (TextView) findViewById(R.id.modify_order_date);
        dateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyOrderActivity.this, ModifyOrderDateActivity.class);
                startActivity(intent);
            }
        });

    }
}
