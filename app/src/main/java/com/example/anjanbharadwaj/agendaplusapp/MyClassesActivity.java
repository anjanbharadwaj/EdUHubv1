package com.example.anjanbharadwaj.agendaplusapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyClassesActivity extends AppCompatActivity {
    ArrayList<String> users = new ArrayList<>();
    String pin1;
    String username;
    Button add;
    EditText name;
    EditText pass;
    ListView listview;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> usersclasses = new ArrayList<>();
    DatabaseReference root;
    DatabaseReference userroot = FirebaseDatabase.getInstance().getReference().child("UserSide");
    DatabaseReference serverroot = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses");
    DatabaseReference serverrootpin;
    DatabaseReference serverrootpass;
    DatabaseReference serverrootuser;
    DatabaseReference root2;
    DatabaseReference root3;
    String namee;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_classes2);
        pass = (EditText)findViewById(R.id.classpass);
        add = (Button)findViewById(R.id.addclass);
        name = (EditText)findViewById(R.id.nameofclass);
        listview = (ListView)findViewById(R.id.classeslist);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Fire
        username = user.getUid();
        root = userroot.child(username).child("Classes");



        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersclasses);
        listview.setAdapter(arrayAdapter);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                    int pin = (int) (Math.floor(Math.random() * 9999) + 1);
                    pin1 = String.valueOf(pin);
                    namee = name.getText().toString().trim();

                    Map<String, Object> map = new HashMap<String, Object>();
                    //Map<String, Object> map2 = new HashMap<String, Object>();
                    //Map<String, Object> map3 = new HashMap<String, Object>();
                root.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(name.getText().toString().trim()).exists()){
                            Toast.makeText(MyClassesActivity.this, "Sorry, this class already exists. Please choose another name",
                                    Toast.LENGTH_SHORT).show();
                            namee = null;

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                    if(namee.equals(null)) return;
                    map.put(name.getText().toString().trim(), "");
                    root.updateChildren(map);
                    root2 = root.child(name.getText().toString().trim()).child("PIN");
                    //map2.put(pin1, "");
                    //root2.updateChildren(map2);
                    root2.setValue(pin1);

                    //map3.put(pass.getText().toString().trim(), "");
                    root3 = root.child(name.getText().toString().trim()).child("Password");
                    //root3.updateChildren(map3);
                    root3.setValue(pass.getText().toString().trim());
                    serverroot.child(name.getText().toString().trim()).setValue(name.getText().toString().trim());
                    serverrootpin = serverroot.child(name.getText().toString().trim()).child("PIN");
                    //serverrootpin.updateChildren(map2);
                    serverrootpin.setValue(pin1);//another way?
                    register();
                    serverrootpass = serverroot.child(name.getText().toString().trim()).child("Password");
                    //serverrootpass.updateChildren(map3);
                    serverrootpass.setValue(pass.getText().toString().trim());//another way



            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());

                }
                usersclasses.clear();
                usersclasses.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), ClassFilesActivity.class);
            intent.putExtra("ClassName", ((TextView)view).getText().toString().trim());
            intent.putExtra("Username", username);
            //intent.putExtra("Pin", );
            //intent.putExtra("Password", pass.getText().toString().trim());
            startActivity(intent);
        }
    });
    }
    public void register(){
        System.out.println("registerentered");
        serverrootuser = serverroot.child(name.getText().toString().trim()).child("Creator");
        //Map<String, Object> map = new HashMap<String, Object>();
        //map.put(username, "");
        //serverrootuser.updateChildren(map);
        serverrootuser.setValue(username);
        users.add(username);
        serverroot.child(name.getText().toString().trim()).child("Users").setValue(users);
    }
}
