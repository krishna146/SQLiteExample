package com.example.sqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sqlite.R;
import com.example.sqlite.database.LocalData;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etName, etPhone;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);

    }

    public void submit(View view) {

        if (etName.getText().toString().isEmpty()) {

            Toast.makeText(this, " Please enter your name", Toast.LENGTH_SHORT).show();

        } else if (etPhone.getText().toString().isEmpty()) {

            Toast.makeText(this, " Please enter your Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (etPhone.getText().toString().length() < 10) {
            Toast.makeText(this, " Enter valid Mobile Number", Toast.LENGTH_SHORT).show();

        } else {
            insertData();
        }


    }

    private void insertData() {

        LocalData localData = new LocalData(context);
        SQLiteDatabase sqLiteDatabase = localData.getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("insert into student(name , phone)values('" + etName.getText().toString() + "','" + etPhone.getText().toString() + "')");
            Toast.makeText(this, " Data Inserted Successfully", Toast.LENGTH_SHORT).show();


        }catch (Exception ex){
        }
        if(sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }

    }

    public void fetchData(View view) {

        startActivity(new Intent(this, StudentDetailsActivity.class));
    }

    public void deleteData(View view) {

    }
}