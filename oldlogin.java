package com.example.arshia.giftme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class oldlogin extends AppCompatActivity {
    Toolbar t; TextView reg; MaterialEditText email,pwd;
    FirebaseAuth auth; android.widget.Button logbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("oldlogin Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        reg = findViewById(R.id.textview2);
        email = findViewById(R.id.input_email);
        pwd = findViewById(R.id.input_password);
        logbtn = findViewById(R.id.loginbtn);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String pass = pwd.getText().toString();


                if (TextUtils.isEmpty(em)||TextUtils.isEmpty(pass))
                {
                    Toast.makeText(oldlogin.this, "All fields required!", Toast.LENGTH_SHORT).show();
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(em).matches()))
                {
                    Toast.makeText(oldlogin.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    auth.signInWithEmailAndPassword(em,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        Intent in = new Intent(oldlogin.this, HomePage.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(in);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(oldlogin.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        auth = FirebaseAuth.getInstance();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(oldlogin.this, Register.class);
                startActivity(in);
            }
        });


    }


    private class Button {
    }
}
