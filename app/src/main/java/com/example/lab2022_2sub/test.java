package com.example.lab2022_2sub;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class test extends AppCompatActivity {

    public Firebase firebase;
    private TextView appdata;
    private String arduino_str;

    private DatabaseReference mDB;
    private DatabaseReference app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        appdata = findViewById(R.id.txt_date);

        mDB = FirebaseDatabase.getInstance().getReference("test");

        String getappdata_1 ="감지되었음";
        String getappdata_2 ="감지되지않음";


        firebase.arduino = arduino_str;
        if(arduino_str == "1"){
            Firebase comment = new Firebase();
            comment.appdata = getappdata_1;
            FirebaseDatabase.getInstance().getReference().child("test").child("appdata").push().setValue(comment);
        }else {
            Firebase comment = new Firebase();
            comment.appdata = getappdata_2;
            FirebaseDatabase.getInstance().getReference().child("test").child("appdata").push().setValue(comment);
        }




    }

    protected void onStart() {
        super.onStart();
        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp = snapshot.getValue(String.class);
                appdata.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("test").child("arduino").addValueEventListener(new ValueEventListener() {
           List<Firebase> firebase;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firebase.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                     firebase.add(dataSnapshot.getValue(Firebase.class));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
