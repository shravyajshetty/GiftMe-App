package com.example.arshia.giftme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {
    Toolbar t; TextView name;
    FirebaseUser firebaseUser;
    ImageView elec,toy,cloth,foot,jewel,conf;
    DatabaseReference databaseReference, d1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("Home Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        name = findViewById(R.id.fname);
        elec = findViewById(R.id.electronic);
        cloth = findViewById(R.id.fashion);
        jewel = findViewById(R.id.jewelery);
        toy = findViewById(R.id.toys);
        conf = findViewById(R.id.confectionery);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("username");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nam  = dataSnapshot.getValue(String.class);
                name.setText("Hello " + nam);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       elec.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent in = new Intent(HomePage.this, Electronics.class);
               startActivity(in);
           }
       });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myopmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.myhome:

            {
                Toast.makeText(HomePage.this, "Already on Home Page!", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.mycart:
            {
                Intent in = new Intent(HomePage.this, Cart.class);
                startActivity(in);
                return true;
            }
            case R.id.myprofile:
            {
                Intent in = new Intent(HomePage.this, Profile.class);
                startActivity(in);
                return true;
            }
            case R.id.logout:
            {
                Intent in = new Intent(HomePage.this, Login.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
