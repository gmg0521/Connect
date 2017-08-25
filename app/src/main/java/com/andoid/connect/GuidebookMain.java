package com.andoid.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class GuidebookMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guidebook_main);

        ImageButton but1 = (ImageButton) findViewById(R.id.owg_but);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(GuidebookMain.this, OlympicWG.class);
                startActivity(i1);
            }
        });
    }
}