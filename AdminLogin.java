package com.example.arshia.giftme;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminLogin extends Fragment {
    Toolbar t;  MaterialEditText email,pwd;
    FirebaseAuth auth; android.widget.Button logbtn;


    public AdminLogin() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_blank2, container, false);

        email = v.findViewById(R.id.admin_input_email);
        pwd = v.findViewById(R.id.admin_input_password);
        logbtn = v.findViewById(R.id.loginbtnadmin);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String pass = pwd.getText().toString();


                if (TextUtils.isEmpty(em)||TextUtils.isEmpty(pass))
                {
                    Toast.makeText(getContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(em).matches()))
                {
                    Toast.makeText(getContext(), "Invalid Email!", Toast.LENGTH_SHORT).show();
                }
                else if (em.equals("admin@giftme.com") && pass.equals("adminpass"))
                {
                    auth.signInWithEmailAndPassword(em,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        Intent in = new Intent(getContext(), AdminHome.class);
                                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(in);
                                        getActivity().finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

                else
                {
                    Toast.makeText(getContext(), "Not Authorised!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        auth = FirebaseAuth.getInstance();

        return  v;


    }


}


