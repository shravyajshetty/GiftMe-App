package com.example.arshia.giftme;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class Electronics extends AppCompatActivity {

    android.support.v7.widget.Toolbar t; ListView l; int pending=0;
    long count; String quer="";
    String items[]; String ids[]; String[] price; Integer cost[]; String iname=""; String iprice=""; String i_imgid="";
    Button sort,filter; android.widget.SearchView sv; SimpleAdapter sa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);
        t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("Electronics");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        l = findViewById(R.id.list1);
        sort =  findViewById(R.id.sort);
        filter = findViewById(R.id.filter);
        sv= findViewById(R.id.search);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference messagesRef = rootRef.child("Electronics");
        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = dataSnapshot.getChildrenCount();
                //Toast.makeText(Electronics.this, ""+dataSnapshot.getChildrenCount(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        items = new String[(int)count]; price = new String[(int)count]; ids = new String[(int)count];

        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        pending +=1;

                         iname = iname + ds.child("name").getValue(String.class)+",";
                         iprice = iprice + ds.child("price").getValue(String.class)+",";
                         i_imgid = i_imgid + ds.child("imgid").getValue(String.class)+",";

                }



                if (count==pending)

                {
                    items = iname.split(",");
                    price = iprice.split(",");
                    ids = i_imgid.split(",");

                    final ArrayList<String> list1 = new ArrayList<>();
                    for(int j=0;j<items.length;j++){
                        list1.add(items[j].split(" ")[0].toLowerCase());
                    }


                    cost = new Integer[price.length];
                    int k=0;

                    for(int i=0;i<price.length;i++)
                    {
                        if (price[i].equals(""))
                            continue;

                        cost[k] = Integer.parseInt(price[i]);
                        k++;
                    }

                    List<HashMap<String,String>> alist = new ArrayList<HashMap<String, String>>();
                    //int resid = getResId("iphone", R.mipmap.class);

                    for (int i=0;i<items.length;i++)
                    {
                        HashMap<String,String> hm = new HashMap<String, String>();
                        hm.put("name", items[i]);
                        hm.put("price", "Rs. "+price[i]);
                        int resid = getResId(ids[i], R.mipmap.class);
                        hm.put("ids", resid+"");
                        alist.add(hm);
                    }




                    String[] from = {"name","price","ids"};
                    int[] to = {R.id.pn, R.id.pp, R.id.imageView};

                     sa = new SimpleAdapter(Electronics.this, alist, R.layout.listimage, from, to);
                    l.setAdapter(sa);

                    sort.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            showPopup1(view);


                        }
                    });


                    sv.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            quer=s;
                            Toast.makeText(Electronics.this, s,Toast.LENGTH_LONG).show();
                            if(list1.contains(s.toLowerCase())){
                                sa.getFilter().filter(s);
                                return true;
                            }
                            else
                                {
                                Toast.makeText(Electronics.this, "No Match found",Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            return false;
                        }
                    });

                    l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int ind=-1;
                            if(!quer.equals("")){
                                for(int i=0;i<items.length;i++){
                                    if(items[i].split(" ")[0].toLowerCase().equals(quer.toLowerCase())){
                                        ind=i;
                                        break;
                                    }
                                }}
                            else{ ind =position; }
                            String sel=items[ind];
                            int idx = getResId(ids[ind], R.mipmap.class);
                            int prc = cost[ind];
                            Intent in = new Intent(Electronics.this,ProductInfo.class);
                            Bundle b = new Bundle();
                            b.putString("sel",sel);
                            b.putInt("idx",idx);
                            b.putInt("prc",prc);
                            sel="";idx=0;prc=0;
                            in.putExtras(b);
                            startActivity(in);
                        }
                    });

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void showPopup1(View v)
    {
        PopupMenu pop = new PopupMenu(this,v);
        pop.inflate(R.menu.sortmenu);
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.asc:

                    {

                        Arrays.sort(cost);
                        String[] sortedprice = new String[items.length];
                        final String[] sortedname = new String[items.length];
                        final String[] sortedid = new String[items.length];
                        int m = 0;
                        int i = 0;
                        while (i < cost.length) {
                            int c = cost[i];
                            for (int j = 0; j < items.length; j++) {
                                if (price[j].equals(c + "")) {
                                    sortedprice[m] = c + "";
                                    sortedname[m] = items[j];
                                    sortedid[m] = ids[j];
                                    m++;
                                    break;

                                }
                            }
                            i++;
                        }

                        List<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();


                        for (int k = 0; k < items.length; k++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("name", sortedname[k]);
                            hm.put("price", "Rs. " + sortedprice[k]);
                             int resid = getResId(sortedid[k], R.mipmap.class);
                            hm.put("ids", resid+"");
                            alist.add(hm);
                        }

                        String[] from = {"name","price","ids"};
                        int[] to = {R.id.pn, R.id.pp, R.id.imageView};

                        final SimpleAdapter sa = new SimpleAdapter(Electronics.this, alist, R.layout.listimage, from, to);
                        l.setAdapter(sa);

                        final ArrayList<String> list1 = new ArrayList<>();
                        for(int j=0;j<items.length;j++){
                            list1.add(items[j].split(" ")[0].toLowerCase());
                        }


                        sv.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                quer=s;
                                Toast.makeText(Electronics.this, s,Toast.LENGTH_LONG).show();
                                if(list1.contains(s.toLowerCase())){
                                    sa.getFilter().filter(s);
                                    return true;
                                }
                                else
                                {
                                    Toast.makeText(Electronics.this, "No Match found",Toast.LENGTH_LONG).show();
                                }
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                return false;
                            }


                        });

                        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int ind=-1;
                                if(!quer.equals("")){
                                    for(int i=0;i<items.length;i++){
                                        if(items[i].split(" ")[0].toLowerCase().equals(quer.toLowerCase())){
                                            ind=i;
                                            break;
                                        }
                                    }}
                                else{ ind =position; }
                                String sel=sortedname[ind];
                                int idx = getResId(sortedid[ind], R.mipmap.class);
                                int prc = cost[ind];
                                Intent in = new Intent(Electronics.this,ProductInfo.class);
                                Bundle b = new Bundle();
                                b.putString("sel",sel);
                                b.putInt("idx",idx);
                                b.putInt("prc",prc);
                                sel="";idx=0;prc=0;
                                in.putExtras(b);
                                startActivity(in);
                            }
                        });

                        return true;
                    }

                    case R.id.desc: {

                        Arrays.sort(cost, Collections.<Integer>reverseOrder());
                        String[] sortedprice = new String[items.length];
                        final String[] sortedname = new String[items.length];
                        final String[] sortedid = new String[items.length];
                        int m = 0;
                        int i = 0;
                        while (i < cost.length) {
                            int c = cost[i];
                            for (int j = 0; j < items.length; j++) {
                                if (price[j].equals(c + "")) {
                                    sortedprice[m] = c + "";
                                    sortedname[m] = items[j];
                                    sortedid[m] = ids[j];
                                    m++;
                                    break;

                                }
                            }
                            i++;
                        }

                        List<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();
                       // int resid = getResId("iphone", R.mipmap.class);

                        for (int k = 0; k < items.length; k++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("name", sortedname[k]);
                            hm.put("price", "Rs. " + sortedprice[k]);
                             int resid = getResId(sortedid[k], R.mipmap.class);
                            hm.put("ids", resid+"");
                            alist.add(hm);
                        }

                        String[] from = {"name","price","ids"};
                        int[] to = {R.id.pn, R.id.pp, R.id.imageView};

                        final SimpleAdapter sa = new SimpleAdapter(Electronics.this, alist, R.layout.listimage, from, to);
                        l.setAdapter(sa);


                        final ArrayList<String> list1 = new ArrayList<>();
                        for(int j=0;j<items.length;j++){
                            list1.add(items[j].split(" ")[0].toLowerCase());
                        }


                        sv.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String s) {
                                quer=s;
                                Toast.makeText(Electronics.this, s,Toast.LENGTH_LONG).show();
                                if(list1.contains(s.toLowerCase())){
                                    sa.getFilter().filter(s);
                                    return true;
                                }
                                else
                                {
                                    Toast.makeText(Electronics.this, "No Match found",Toast.LENGTH_LONG).show();
                                }
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                return false;
                            }


                        });

                        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int ind=-1;
                                if(!quer.equals("")){
                                    for(int i=0;i<items.length;i++){
                                        if(items[i].split(" ")[0].toLowerCase().equals(quer.toLowerCase())){
                                            ind=i;
                                            break;
                                        }
                                    }}
                                else{ ind =position; }
                                String sel=sortedname[ind];
                                int idx = getResId(sortedid[ind], R.mipmap.class);
                                int prc = cost[ind];
                                Intent in = new Intent(Electronics.this,ProductInfo.class);
                                Bundle b = new Bundle();
                                b.putString("sel",sel);
                                b.putInt("idx",idx);
                                b.putInt("prc",prc);
                                sel="";idx=0;prc=0;
                                in.putExtras(b);
                                startActivity(in);
                            }
                        });

                        return true;
                    }
                }
                return true;
            }


        });
        pop.show();
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
                Intent in = new Intent(Electronics.this, HomePage.class);
                return true;
            }
            case R.id.mycart:
            {
                Intent in = new Intent(Electronics.this, Cart.class);
                startActivity(in);
                return true;
            }
            case R.id.myprofile:
            {
                Intent in = new Intent(Electronics.this, Profile.class);
                startActivity(in);
                return true;
            }
            case R.id.logout:
            {
                Intent in = new Intent(Electronics.this, Login.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
