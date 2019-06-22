package com.example.android.hotels;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.hotels.activities.CancelOrderActivity;
import com.example.android.hotels.activities.ModifyOrderActivity;
import com.example.android.hotels.activities.NewOrderActivity;
import com.example.android.hotels.activities.SearchAvailableActivity;
import com.example.android.hotels.activities.SearchOrderActivity;
import com.example.android.hotels.data.OrderDbHelper;
import com.example.android.hotels.data.OrderContract.OrderEntry;

import com.example.android.hotels.data.Hotel;
import com.example.android.hotels.data.HotelList;
public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();

    /**
     * Initialize the screen
     *
     * @param savedInstanceState reload saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init HotelList
        HotelList.init(this);

        // click listener on cancel_order
        TextView cancelOrder = (TextView) findViewById(R.id.cancel_order);
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent cancelOrderIntent = new Intent(MainActivity.this, CancelOrderActivity.class);
                startActivity(cancelOrderIntent);
            }
        });

        // click listener on modify_order
        TextView modifyOrder = (TextView) findViewById(R.id.modify_order);
        modifyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modifyOrderIntent = new Intent(MainActivity.this, ModifyOrderActivity.class);
                startActivity(modifyOrderIntent);
            }
        });

        // click listener on new_order
        TextView newOrder = (TextView) findViewById(R.id.new_order);
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newOrderIntent = new Intent(MainActivity.this, NewOrderActivity.class);
                startActivity(newOrderIntent);
            }
        });

        // click listener on search_available
        TextView searchAvailable = (TextView) findViewById(R.id.search_available);
        searchAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchAvailableIntent = new Intent(MainActivity.this, SearchAvailableActivity.class);
                startActivity(searchAvailableIntent);
            }
        });

        // click listener on search_order
        TextView searchOrder = (TextView) findViewById(R.id.search_order);
        searchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchOrderIntent = new Intent(MainActivity.this, SearchOrderActivity.class);
                startActivity(searchOrderIntent);
            }
        });
    }
}
