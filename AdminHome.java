package com.example.arshia.giftme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AdminHome extends AppCompatActivity {
    Button b;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        b = findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(AdminHome.this, AddItem.class);
                startActivity(in);
                /*firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference("Orders").child("O02");
                HashMap<String, String> hm = new HashMap<String, String>();

                c = Calendar.getInstance();
                int day, month, year;

                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                String date = day + "/" + month + "/" + year;

                hm.put("CustID", "02");
                hm.put("Amount", "300");
                hm.put("PlacedOn", date);
                databaseReference.setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminHome.this, "Added!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdminHome.this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }


        });

    }
}
