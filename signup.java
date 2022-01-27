package com.example.remind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    public FirebaseAuth auth;
    EditText f,e,p;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        auth= FirebaseAuth.getInstance();

        f=findViewById(R.id.fullname);
        e=findViewById(R.id.Email);
        p=findViewById(R.id.Password);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }
    public void register(View v)
    {
        final String full_name = f.getText().toString().trim();
        String pass = p.getText().toString().trim();
        String mail= e.getText().toString().trim();
        if (full_name.length()==0){
            Toast.makeText(signup.this, "Please enter your name.",
                Toast.LENGTH_SHORT).show();
        }else if(mail.length()==0){
            Toast.makeText(signup.this, "Please enter an email.",
                    Toast.LENGTH_SHORT).show();
        }else if (pass.length()<6){
            Toast.makeText(signup.this, "Password needs to be at least 6 characters.",
                    Toast.LENGTH_SHORT).show();
        }else if(full_name!="") {
            auth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                String user_id = user.getUid();
                                db = firebaseDatabase.getInstance().getReference();
                                db.child("Users").child(user_id).child("Name").setValue(full_name);
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(full_name).build();
                                user.updateProfile(profileUpdates);
                                updateUI(user);
                            } else {
                                Toast.makeText(signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
        else{
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i=new Intent(signup.this,homepage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else {

        }
    }

    public void logForm(View view) {
        startActivity(new Intent(getApplicationContext(),login.class));
    }

}

