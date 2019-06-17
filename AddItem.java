package com.example.arshia.giftme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class AddItem extends AppCompatActivity {
    Spinner s; Button a; Toolbar t;
    MaterialEditText pid,imgid,pname,price,agegrp;
    String cat[] = {"Electronics","Clothing","Footwear","Jewelery","Toys","Confectionery"};
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        t = findViewById(R.id.toolbar);
        s = findViewById(R.id.spinner);
        a = findViewById(R.id.add);
        pid = findViewById(R.id.pid);
        pname = findViewById(R.id.pname);
        imgid = findViewById(R.id.imgid);
        price = findViewById(R.id.price);
        agegrp = findViewById(R.id.age);

        setSupportActionBar(t);
        getSupportActionBar().setTitle("Add Products");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ArrayAdapter ar = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cat);
        ar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(ar);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = s.getSelectedItem().toString();
                String i_pid = pid.getText().toString();
                String i_pname = pname.getText().toString();
                String i_imgid = imgid.getText().toString();
                String i_price = price.getText().toString();
                String agegr = agegrp.getText().toString();

                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                databaseReference=FirebaseDatabase.getInstance().getReference(category).child(i_pid);

                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("name", i_pname);
                hm.put("imgid", i_imgid);
                hm.put("price", i_price);
                hm.put("agegroup", agegr);

                databaseReference.setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(AddItem.this, "Added!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AddItem.this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });


    }
}
