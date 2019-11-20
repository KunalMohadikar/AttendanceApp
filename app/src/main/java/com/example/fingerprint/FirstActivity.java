package com.example.fingerprint;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    static DatabaseHelper myDb;

    // Creating Buttons
    Button button_markAttendance;
    Button button_addClass;
    Button button_displayclass;

    //All Class Names
    static ArrayList<String> ClassNames = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        myDb = new DatabaseHelper(this);

        // declaring buttons
        button_markAttendance = findViewById(R.id.button_mark);
        button_addClass = findViewById(R.id.button_addClass);
        button_displayclass = findViewById(R.id.button_showclass);

        button_markAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("mark_attendance");
                startActivity(i);
            }
        });

        button_addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("Add_Class");
                startActivity(i);
            }
        });

        button_displayclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("Display_Attendence");
                startActivity(i);
            }
        });

        Cursor res = FirstActivity.myDb.getAllData();
        if(res.getCount() != 0)
        {
            ClassNames.clear();
            while(res.moveToNext()){
                ClassNames.add(res.getString(0));
            }
        }


    }
}
