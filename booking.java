package com.example.remind;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.res.Resources;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class booking extends AppCompatActivity {
    public Spinner spinnerday,spinnerclass,spinnertime,spinnerdept,spinneryear,spinnerbookingstate;
    public FirebaseDatabase firebaseDatabase;
    public FirebaseAuth firebaseAuth;
    public DatabaseReference db1,db2;
    public FirebaseUser firebaseUser;
    public String day,classs, time, dept, year, bookingstate;
    public Resources res;
    public int flag=9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().hide();
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        spinnerday=(Spinner)findViewById(R.id.day);
        spinnerclass=(Spinner)findViewById(R.id.classroom);
        spinnertime=(Spinner)findViewById(R.id.timings);
        spinnerdept=(Spinner)findViewById(R.id.dept);
        spinneryear=(Spinner)findViewById(R.id.year);
        spinnerbookingstate=(Spinner)findViewById(R.id.booking_state);

    }
    public void Book(View view)
    {
        day=spinnerday.getSelectedItem().toString().trim();
        classs=spinnerclass.getSelectedItem().toString().trim();
        time=spinnertime.getSelectedItem().toString().trim();
        dept=spinnerdept.getSelectedItem().toString().trim();
        year=spinneryear.getSelectedItem().toString().trim();
        bookingstate=spinnerbookingstate.getSelectedItem().toString().trim();
        db2 = firebaseDatabase.getReference();
            db2.child("Weekly").child(day).child(time).child(classs).child("tbooked").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String booking = dataSnapshot.getValue().toString();

                    if (booking.equals("false")) {
                        flag = 1;
                    } else {
                        firebaseUser = firebaseAuth.getCurrentUser();
                        String n = firebaseUser.getDisplayName();
                        Toast.makeText(booking.this, "Booked by " + n, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        if(flag==1)
        {
            goAhead();
        }


    }
    public  void goAhead()
    {

        if (bookingstate.equals("Permanent")) {
            Toast.makeText(booking.this, bookingstate, Toast.LENGTH_SHORT).show();
                firebaseUser = firebaseAuth.getCurrentUser();
                String n = firebaseUser.getDisplayName();
                String batchb = year + " " + dept;
                Toast.makeText(booking.this, "Inside Permanent", Toast.LENGTH_SHORT).show();
                com.example.remind.Database DB1 = new Database(true, n,true, batchb);
                db2.child("Weekly").child(day).child(time).child(classs).setValue(DB1);
                db2.child("Permanent").child(day).child(time).child(classs).setValue(DB1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(booking.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                    }
                });
        }else if(bookingstate.equals("Temporary"))
        {
            Temp();
        }
    }
    public  void Temp()
    {
            firebaseUser = firebaseAuth.getCurrentUser();
            String n = firebaseUser.getDisplayName();
            String batchb = year + " " + dept;
            Toast.makeText(booking.this, "Inside Temporary", Toast.LENGTH_SHORT).show();
            com.example.remind.Database DB2 = new Database(false, n,true, batchb);
            db2.child("Weekly").child(day).child(time).child(classs).setValue(DB2).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(booking.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                }
            });
    }
    public void homeForm(View view){
        startActivity(new Intent(getApplicationContext(),homepage.class));
    }
}