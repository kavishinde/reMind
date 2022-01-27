package com.example.remind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homepage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference db1, db3;
    public Resources res;
    boolean b=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.homepage);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, login.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();


    }

    public void initForm(View v) {
        res = getResources();
        db1 = firebaseDatabase.getReference();
        String[] days = res.getStringArray(R.array.days);
        String[] timing = res.getStringArray(R.array.timings);
        String[] classs = res.getStringArray(R.array.classrooms);
        com.example.remind.Database DB = new Database(false, "",false,"");;
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < timing.length; j++) {
                for (int k = 0; k < classs.length; k++) {
                    db1.child("Weekly").child(days[i]).child(timing[j]).child(classs[k]).setValue(DB);
                    db1.child("Permanent").child(days[i]).child(timing[j]).child(classs[k]).setValue(DB);
                    Toast.makeText(this, "Database Reset", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



        public void timetableForm (View v){
            res = getResources();
            db1 = firebaseDatabase.getReference();
            final String[] days = res.getStringArray(R.array.days);
            final String[] timing = res.getStringArray(R.array.timings);
            final String[] classs = res.getStringArray(R.array.classrooms);

            for (int i = 0; i < days.length; i++) {
                for (int j = 0; j < timing.length; j++) {
                    for (int k = 0; k < classs.length; k++) {
                        final int finalI = i;
                        final int finalJ = j;
                        final int finalK = k;
                        db1.child("Permanent").child(days[i]).child(timing[j]).child(classs[k]).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String tbs=dataSnapshot.child("tbooked").getValue().toString();
                                String bs=dataSnapshot.child("booked").getValue().toString();
                                boolean tb=chekV(tbs);
                                boolean b=chekV(bs);
                                String t=dataSnapshot.child("teacher").getValue().toString();
                                String ba=dataSnapshot.child("batch").getValue().toString();
                                Database DB= new Database(b,t,tb,ba);
                                db1.child("Weekly").child(days[finalI]).child(timing[finalJ]).child(classs[finalK]).setValue(DB);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
            Toast.makeText(this, "Database Reset", Toast.LENGTH_SHORT).show();

        }
        public boolean chekV(String s)
        {
            if(s.equals("true"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public void bookingForm (View v){
            startActivity(new Intent(getApplicationContext(), booking.class));
        }

        public void cancellationForm (View view){
            startActivity(new Intent(getApplicationContext(), cancellation.class));
        }

        public void logForm (View view){
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), login.class));
        }

        public void viewForm (View view){
        startActivity(new Intent(getApplicationContext(), display.class));
        }
    }
