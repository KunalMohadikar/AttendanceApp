package com.example.fingerprint;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayAttendence extends AppCompatActivity {

    EditText class_name;
    Button view_student;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayattendence);

        class_name = findViewById(R.id.editText_classname1);
        view_student = findViewById(R.id.button_viewStudents1);

        ViewAllStudents();
    }

    public void ViewAllStudents(){
        view_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = FirstActivity.myDb.getClassData(class_name.getText().toString());
                if (res.getCount() == 0) {
                    Show("Error", "No data found!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(res.getString(0) + " ");
                    buffer.append(res.getString(1) + " ");
                    buffer.append(res.getString(2) + " \n");
                }
                Show(class_name.getText().toString(),buffer.toString());
            }
        });
    }

    public void Show(String title,String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}