package com.example.arshia.giftme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    Toolbar t;
    MaterialEditText phn,nam,em,addr;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Button up;
    String n,e,p,a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        phn = findViewById(R.id.phn);
        em = findViewById(R.id.em);
        addr = findViewById(R.id.addr);
        nam = findViewById(R.id.nam);
        up = findViewById(R.id.up);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot ds) {

                       n = ds.child("username").getValue(String.class);
                       e = ds.child("email").getValue(String.class);
                       p = ds.child("phone").getValue(String.class);
                       a = ds.child("address").getValue(String.class);

                       phn.setText(p);
                       em.setText(e);
                       addr.setText(a);
                       nam.setText(n);

                       up.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                               n = nam.getText().toString();
                                e = em.getText().toString();
                                p = phn.getText().toString();
                                a = addr.getText().toString();

                               HashMap<String,String> hm = new HashMap<String, String>();
                               hm.put("id",firebaseUser.getUid());
                               hm.put("username", n);
                               hm.put("phone", p);
                               hm.put("address", a);
                               hm.put("email",e);
                               databaseReference.setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if (task.isSuccessful())
                                       {
                                           Toast.makeText(Profile.this, "Updated!", Toast.LENGTH_SHORT).show();
                                           Intent in = new Intent(Profile.this, Profile.class);
                                           startActivity(in);
                                       }

                                   }
                               });

                           }
                       });




               }




           @Override
           public void onCancelled(DatabaseError databaseError) {

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
                Intent in = new Intent(Profile.this, HomePage.class);
                startActivity(in);
                return true;
            }
            case R.id.mycart:
            {
                Intent in = new Intent(Profile.this, Cart.class);
                startActivity(in);
                return true;
            }
            case R.id.myprofile:
            {
                Toast.makeText(Profile.this, "Already on Profile Page!", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.logout:
            {
                Intent in = new Intent(Profile.this, Login.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
