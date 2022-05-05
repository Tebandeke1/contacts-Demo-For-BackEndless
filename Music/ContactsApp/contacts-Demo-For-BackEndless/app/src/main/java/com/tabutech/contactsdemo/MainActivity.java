package com.tabutech.contactsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnContactList,btnNewContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContactList = findViewById(R.id.btnList);
        btnNewContacts = findViewById(R.id.btnCreate);


        btnNewContacts.setOnClickListener( v ->{
            startActivity(new Intent(this,NewContacts.class));
        });

        btnContactList.setOnClickListener( v ->{
            startActivity(new Intent(this,ContactsList.class));
        });
    }
}