package com.andoid.connect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Picasso.with(this)
                .load("http://i.imgur.com/z9kmJWq.png")
                .into((ImageView)findViewById(R.id.up));

        Picasso.with(this)
                .load("http://i.imgur.com/g0dZ83N.png")
                .into((ImageView)findViewById(R.id.down));

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-Generated Method stub
                Intent i = new Intent(MainActivity.this, MainMenu.class);

                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
