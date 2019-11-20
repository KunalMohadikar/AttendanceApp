package com.example.fingerprint;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendence extends AppCompatActivity {

    EditText class_name,txt;
    Button view_student,mark_student,submit;
    ArrayList<Integer> attendance;
    ArrayList<Button> btn1;
    String classname1;
    ArrayList<Integer> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markattendence);

        class_name = findViewById(R.id.editText_classname);
        view_student = findViewById(R.id.button_viewStudents);
        mark_student = findViewById(R.id.button_markStudents);
        submit = findViewById(R.id.submit);
        btn1 = new ArrayList<Button>();
        list = new ArrayList<Integer>();
        ViewAllStudents();
        markAllStudents();
//        Submit_attendance();
        submission();
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

    public void markAllStudents(){
        mark_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((LinearLayout) findViewById(R.id.buttonLayout)).getChildCount() > 0)
                    ((LinearLayout) findViewById(R.id.buttonLayout)).removeAllViews();
                Cursor res = FirstActivity.myDb.getClassData(class_name.getText().toString());
                classname1 = class_name.getText().toString();
                attendance = new ArrayList<Integer>();
                if (res.getCount() == 0) {
                    Show("Error", "No data found!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    list.add(0);
                    int x = list.size()-1;
                    addRadioButtons(Integer.parseInt(res.getString(0)),res.getString(1),x);
                }

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

//    public void mark(){
//        int i = 0;
//        Cursor res = FirstActivity.myDb.getClassData(class_name.getText().toString());
//        if (res.getCount() == 0) {
//            Show("Error", "No data found!");
//            return;
//        }
//        while(res.moveToNext()){
//            final int j = i;
//            final Cursor res1 = res;
//            btn1.get(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String x = Integer.toString(Integer.parseInt(res1.getString(2))+1);
//                    FirstActivity.myDb.updateData(classname1,res1.getString(0),res1.getString(1),x);
//                }
//            });
//            i++;
//        }
//    }

    public void addRadioButtons(int id,String name,int x) {
        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);


            TextView textView1 = new TextView(this);
            textView1.setText(name);
            textView1.setWidth(300);
            ll.addView(textView1);


            RadioButton rdbtn = new RadioButton(this);
            rdbtn.setId(id);
            rdbtn.setWidth(300);
            ll.addView(rdbtn);

            RadioButton rdbtn1 = new RadioButton(this);
            rdbtn1.setId(0);
            ll.addView(rdbtn1);
            ll.setGravity(Gravity.CENTER_HORIZONTAL);
            final int x1 = x;
            final int id1 = id;
            ll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb=(RadioButton)findViewById(checkedId);
                    if(checkedId == id1){
                        list.set(x1,1);
                        Toast.makeText(MarkAttendence.this,Integer.toString(x1)+list.get(x1),Toast.LENGTH_SHORT).show();
                    }
                    else{
                        list.set(x1,0);
                        Toast.makeText(MarkAttendence.this,Integer.toString(x1)+list.get(x1),Toast.LENGTH_SHORT).show();
                    }

                }
            });
            ((ViewGroup) findViewById(R.id.buttonLayout)).addView(ll);
        }
    }

    public void Submit_attendance(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                for(i=0;i<attendance.size();i++){
                    txt.append(attendance.get(i).toString()+" ");
                }
            }
        });
    }

    public void submission(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = FirstActivity.myDb.getClassData(class_name.getText().toString());
                classname1 = class_name.getText().toString();
                attendance = new ArrayList<Integer>();
                if (res.getCount() == 0) {
                    Show("Error", "No data found!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                int i = 0;
                while(res.moveToNext()){
                    if(list.get(i) == 1){
                        Toast.makeText(MarkAttendence.this,"marked",Toast.LENGTH_SHORT);
                        boolean isUpdate = FirstActivity.myDb.updateData(classname1,res.getString(0),res.getString(1),Integer.toString(Integer.parseInt(res.getString(2))+1));
                        if(!isUpdate){
                            Toast.makeText(MarkAttendence.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                    i++;
                }

            }
        });
    }





}
