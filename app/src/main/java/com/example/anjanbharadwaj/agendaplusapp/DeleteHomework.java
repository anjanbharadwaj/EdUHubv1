package com.example.anjanbharadwaj.agendaplusapp;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by anjanbharadwaj on 12/24/16.
 */
public class DeleteHomework extends AppCompatActivity{
    String username;
    String homework;
    TextView text;
    Button delete;
    String[] s;
    String onlyhw;
    //ArrayList<String> thelist;

    DatabaseReference root;
    DatabaseReference root2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletehw);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.5));
        username = (String) (getIntent().getExtras().get("Username"));
        homework = (String) (getIntent().getExtras().get("Homework"));
        //thelist = (ArrayList<String>) (getIntent().getExtras().get("Listofhw"));
        text = (TextView)findViewById(R.id.textview);
        delete = (Button)findViewById(R.id.button4);
        text.setText("Homework: '" + homework + "'");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(ClassFilesActivity.thename).child("Homework");
                root2 = FirebaseDatabase.getInstance().getReference().child("ServerSide").child("ClientClasses").child(ClassFilesActivity.thename).child("Creator");

                root2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(username.equals(dataSnapshot.getValue().toString())) {
                            s = homework.split(" due ");
                            onlyhw = s[0];
                            System.out.println(onlyhw);
                            ClassFilesActivity.classhomework.remove(homework);
                            root.child(onlyhw).removeValue();
                            ClassFilesActivity.arrayAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(DeleteHomework.this, "Sorry, but you aren't an admin. Ask an admin for permission. ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

    }
}
