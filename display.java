package com.example.remind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class display extends AppCompatActivity {
    public Spinner spinnerday,spinnertimings;
    public FirebaseDatabase firebaseDatabase;
    public FirebaseAuth firebaseAuth;
    public DatabaseReference db1,db2;
    public Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_display);
        spinnerday=(Spinner)findViewById(R.id.day);
        spinnertimings=(Spinner)findViewById(R.id.timings);
    }
    public void Check(View v)
    {
        String day=spinnerday.getSelectedItem().toString().trim();
        String time=spinnertimings.getSelectedItem().toString().trim();
        final String[] bookings=new String[6];
        final TextView []tv=new TextView[6];
        res=getResources();
        tv[0]=findViewById(R.id.booking1);
        tv[1]=findViewById(R.id.booking2);
        tv[2]=findViewById(R.id.booking3);
        tv[3]=findViewById(R.id.booking4);
        tv[4]=findViewById(R.id.booking5);
        tv[5]=findViewById(R.id.booking6);
        final String[] booking_id=new String[6];
        final String[] classs=res.getStringArray(R.array.classrooms);
        firebaseDatabase=FirebaseDatabase.getInstance();
        db2 = firebaseDatabase.getReference().child("Weekly").child(day).child(time);
        ValueEventListener x=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(int i=0;i<6;i++) {
                    bookings[i] = dataSnapshot.child(classs[i]).child("tbooked").getValue().toString();
                    booking_id[i] = dataSnapshot.child(classs[i]).child("teacher").getValue().toString();
                    if (bookings[i].equals("true")) {
                        tv[i].setTextColor(Color.parseColor("#BA3E5D"));
                        tv[i].setText("Booked by "+booking_id[i]+"");
                    } else {
                        tv[i].setTextColor(Color.parseColor("#58C780"));
                        tv[i].setText("Vacant");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        db2.addValueEventListener(x);
    }
    public void homeForm(View view){
        startActivity(new Intent(getApplicationContext(),homepage.class));
    }
}
