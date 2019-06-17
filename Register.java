package com.example.arshia.giftme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    MaterialEditText name,phone,email,pwd,add;
    Button b;
    Toolbar t;
    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("Registration Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        b = findViewById(R.id.button);
        add = findViewById(R.id.addr);

        auth = FirebaseAuth.getInstance();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = name.getText().toString();
                String e = email.getText().toString();
                String p = pwd.getText().toString();
                String num = phone.getText().toString();
                String address = add.getText().toString();

                if (TextUtils.isEmpty(n)||TextUtils.isEmpty(e)||TextUtils.isEmpty(p)||TextUtils.isEmpty(num)||TextUtils.isEmpty(address))
                {
                    Toast.makeText(Register.this, "All fields required!", Toast.LENGTH_SHORT).show();
                }
                else if (!(TextUtils.isDigitsOnly(num)) || num.length()!=10)
                {
                    Toast.makeText(Register.this, "Invalid Phone Number!", Toast.LENGTH_SHORT).show();
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(e).matches()))
                {
                    Toast.makeText(Register.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                }
                else if (p.length()<6)
                {
                    Toast.makeText(Register.this, "Password should consist at least 6 characters!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(e,p,n,num,address);
                }
            }
        });

    }

    private void register(final String email, String password, final String name, final String phone, final String add)
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String,String> hm = new HashMap<String, String>();
                            hm.put("id",userid);
                            hm.put("username", name);
                            hm.put("phone", phone);
                            hm.put("address", add);
                            hm.put("email",email);
                            reference.setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(Register.this, Login.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(in);
                                        finish();
                                    }
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(Register.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
