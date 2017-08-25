package com.andoid.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class JoinChoosePage extends AppCompatActivity {

    Button button;
    ArrayList arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_choose_page);

        arrayList = new ArrayList();
        arrayList.add("Korean");
        arrayList.add("English");
        arrayList.add("Spanish");
        arrayList.add("Freaking!");

        final String[] select_item = {""};

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button4);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                select_item[0] = String.valueOf(arrayList.get(arg2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0){

            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String str_nation = spinner.getSelectedItem().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("ID1/nation");

                myRef.setValue(str_nation);

                if (select_item[0].equals("Korean")){
                                        Intent intent = new Intent(JoinChoosePage.this, JoinPage.class);
                    intent.putExtra("selected_nation", str_nation);
                    startActivity(intent);
                }
                else {
                    Intent i =new Intent(JoinChoosePage.this, JoinPage.class);

                    i.putExtra("selected_nation", str_nation);
                    startActivity(i);
                }
            }
        });
    }
}