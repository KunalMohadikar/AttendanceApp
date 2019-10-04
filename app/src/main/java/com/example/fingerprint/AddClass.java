package com.example.fingerprint;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddClass extends AppCompatActivity {

    // All EditTexts
    EditText add_class;
    EditText roll_no;
    EditText name;

    // All Buttons
    Button btnAddClass;
    Button btnViewClass;
    Button btnAddStudent;

    public String Current_Class;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class);

        add_class = findViewById(R.id.EnterClass);
        roll_no = findViewById(R.id.editText_RollNo);
        name = findViewById(R.id.editText_name);

        btnAddClass = findViewById(R.id.button_AddClass);
        btnViewClass = findViewById(R.id.button_viewClass);
        btnAddStudent = findViewById(R.id.button_AddStudent);

        AddNewData();
        ViewClasses();
        addStudent();
    }

    public void AddNewData(){
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted =FirstActivity.myDb.insertData(add_class.getText().toString());

                if(isInserted){
                    FirstActivity.ClassNames.add(add_class.getText().toString());
                    FirstActivity.myDb.create_table(add_class.getText().toString());
                    Current_Class = add_class.getText().toString();
                }
                else
                {
                    Toast.makeText(AddClass.this,"Error, not inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ViewClasses(){
        btnViewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                int i;
                for(i=0;i<FirstActivity.ClassNames.size();i++){
                    buffer.append(FirstActivity.ClassNames.get(i)+"\n");
                }
                Show("Classes",buffer.toString());

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

    public void addStudent(){
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = FirstActivity.myDb.Insert_data_in_class(Current_Class,roll_no.getText().toString(),name.getText().toString());
                if(isInserted){
                    Toast.makeText(AddClass.this,"Student data inserted in"+Current_Class,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddClass.this,"Error! Unable to insert data",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
