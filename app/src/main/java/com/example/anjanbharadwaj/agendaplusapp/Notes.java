package com.example.anjanbharadwaj.agendaplusapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;



public class Notes extends AppCompatActivity {
    String username;
    EditText note;
    Spinner spinner;
    String whichnote;
    Button save;
    Button add;
    EditText name;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> theirnotes = new ArrayList<>();
    DatabaseReference notesroot = FirebaseDatabase.getInstance().getReference().child("UserSide").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("NoteNames");
    //FirebaseStorage storage = FirebaseStorage.getInstance();
    //StorageReference storageRef = storage.getReferenceFromUrl("gs://agendaplusapp.appspot.com");
    //StorageReference notesRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        username = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        note = (EditText)findViewById(R.id.usernotes);
        spinner = (Spinner)findViewById(R.id.spinner);
        name = (EditText)findViewById(R.id.editText6);
        add = (Button)findViewById(R.id.add);
        save = (Button)findViewById(R.id.save);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, theirnotes);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                whichnote = adapterView.getItemAtPosition(i).toString();
                System.out.print(whichnote);
                notesroot.child(whichnote).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       note.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                /*notesRef = storageRef.child(username).child("Notes").child(whichnote);
                System.out.println(notesRef.toString());
                final long ONE_MEGABYTE = 1024 * 1024;
                notesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Data for "images/island.jpg" is returns, use this as needed
                        String string = new String(bytes);
                        note.setText(string);
                        System.out.println(string);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                /*
                try {


                    final File localFile = File.createTempFile(whichnote, "txt");
                    notesRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Local temp file has been created


                            try {
                                FileInputStream fstream = new FileInputStream(localFile.getPath());
                                InputStreamReader in = new InputStreamReader(fstream);
                                BufferedReader br = new BufferedReader(in);
                                String line = null;
                                StringBuilder sb = new StringBuilder();
                                try {
                                    while ((line = br.readLine()) != null) {
                                        sb.append(line);
                                    }
                                    note.setText(sb.toString());
                                }catch(IOException e){

                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        notesroot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());

                }
                theirnotes.clear();
                theirnotes.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setHint(R.string.search_hint);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(theirnotes.contains(name.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(), "The note" + name.getText().toString().trim() + "already exists. Please try a different name." , Toast.LENGTH_SHORT ).show();
                    return;
                }
                */
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(name.getText().toString().trim(), "");
                notesroot.updateChildren(map);

                notesroot.child(name.getText().toString()).setValue(note.getText().toString());
                name.setHint("");
                /*
                notesRef = storageRef.child(username).child("Notes").child(name.getText().toString().trim());

                byte[] data = note.toString().getBytes();

                UploadTask uploadTask = notesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        System.out.println("passed");
                    }
                });
                */

            }
        });





    }












    /*OLDSTUFF
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://agendaplusapp.appspot.com");
    //StorageReference imagesRef = storageRef.child("images");
    StorageReference notesRef;
    StorageReference notesRef2;
    StorageReference notesRefload;
    StorageReference pathReference;
    StorageReference httpsReference;
    byte[] returnedval;

    //firebase stuff ^^
    int fileName = 0;
    public String[] noteData = new String[10];
    Spinner spinneruse;
    ArrayAdapter<CharSequence> adapter;
    String currentFile = "note";
    File file;
    ArrayList<String> arraySpinner = new ArrayList<String>();
    String titleForNotes;
    String currentFileTitle = titleForNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        loadSpinnerData();
        boolean shouldCreateNote = true;
        for(int i = 0; i < arraySpinner.size(); i++) {
            if(arraySpinner.get(i) == "note") {
                shouldCreateNote = false;
            }
        }
        if(shouldCreateNote) {
            arraySpinner.add("note");
        }
        spinneruse = (Spinner) findViewById(R.id.spinner);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinneruse.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spinneruse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Toast.makeText(getBaseContext(),  adapterView.getItemAtPosition(position) + " selected", Toast.LENGTH_SHORT ).show();
                load(adapterView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        load("note");
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText) findViewById(R.id.titleView);
                titleForNotes = title.getText().toString();
                save(titleForNotes);
            }
        });

        Button newNote = (Button) findViewById(R.id.add);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText) findViewById(R.id.titleView);
                titleForNotes = title.getText().toString();
                Toast toast = Toast.makeText(getApplicationContext(), "New Note with title " + titleForNotes , Toast.LENGTH_SHORT);
                toast.show();
                EditText n = (EditText) findViewById(R.id.usernotes);
                n.setText("");
                EditText t = (EditText) findViewById(R.id.titleView);
                n.setText("");


            }
        });



       FileInputStream in = null;
       File file5 = getFileStreamPath("notes.txt");
       String FILENAME = "notes.txt";
       String string = "Diary: CHS hackathon..... EPIC. This project rocks!";
       try {
           in = openFileInput("notes.txt");
           InputStreamReader inputStreamReader = new InputStreamReader(in);
           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
           StringBuilder sb = new StringBuilder();
           String line;
           String text = "";
           try {
               while ((line = bufferedReader.readLine()) != null) {
                   text += line;
               }
               Toast toast = Toast.makeText(getApplicationContext(), "Data Retrieved", Toast.LENGTH_SHORT );
               EditText notes = (EditText)findViewById(R.id.usernotes);
               notes.setText(text);

           } catch (IOException e) {
               e.printStackTrace();
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

       FileOutputStream fos = null;
       try {
           fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       try {
           fos.write(string.getBytes());
       } catch (IOException e) {
           e.printStackTrace();
       }
       try {
           fos.close();
       } catch (IOException e) {
           e.printStackTrace();
       }

    }

    public void save(String title) {
        EditText titleOfNote = (EditText) findViewById(R.id.titleView);
        String isrepeat = titleOfNote.getText().toString();
        File file = new File(getApplicationContext().getFilesDir(), title);
        EditText notes = (EditText)findViewById(R.id.usernotes);
        String data = notes.getText().toString().trim();
        byte[] data1 = notes.getText().toString().trim().getBytes();
        notesRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(isrepeat + ".txt");
        //InputStream stream = new FileInputStream(new File(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + isrepeat));
        //pathReference = storageRef.child("images/" + titleForNotes + ".txt");
        //httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/notes%20" + titleForNotes + ".txt");


        try {
            FileOutputStream fou = openFileOutput(title + ".txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fou);
            try {
                osw.write(data);


                UploadTask uploadTask = notesRef.putBytes(data1);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });

                //write to firebase here
                osw.flush();
                osw.close();
                Toast toast = Toast.makeText(getApplicationContext(), "Data Saved in file " + title, Toast.LENGTH_SHORT );
                toast.show();

                InputStream stream = new FileInputStream(new File(title + ".txt"));
                UploadTask uploadTask;
                uploadTask = notesRef.putStream(stream);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });

                boolean shouldCreate = true;
                for(int i = 0; i < arraySpinner.size(); i++) {
                    if(arraySpinner.get(i) == isrepeat) {
                        shouldCreate = false;
                        break;
                    }
                }
                if(shouldCreate) {
                    arraySpinner.add(isrepeat);

                    if(adapter != null) adapter.notifyDataSetChanged();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void load(String title) {
        //File.absolutefilepath to find the file path
        FileInputStream in;
        notesRef = storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(title + ".txt");

        //download from firebase
        File localFile = null;
        try {
            localFile = File.createTempFile(title, "txt");
            notesRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

            try{
            in = openFileInput(title + ".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            String text = new String(returnedval);
            //while ((line = bufferedReader.readLine()) != null) {
            //text += line;
            //}

            //Toast toast = Toast.makeText(getApplicationContext(), "Data Retrieved from " + title , Toast.LENGTH_SHORT );
            //toast.show();
            EditText notes = (EditText)findViewById(R.id.usernotes);
            //notes.setText(text);
            //String thenote = readit(localFile);
            FileInputStream in2;
            String text = "";
            try {
                in = new FileInputStream(localFile.getPath().toString());//openFileInput(localFile.getPath());
                InputStreamReader inputStreamReader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;

                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        text += line;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
            notes.setText(text);
            EditText titleOfNote = (EditText) findViewById(R.id.titleView);
            titleOfNote.setText(title);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }





    public void add(View view) {


    }


    public void retrieve(View view) {


    }
   public void save(View view) {
       EditText notes = (EditText)findViewById(R.id.usernotes);
       String data = notes.getText().toString().trim();
       try {

           FileOutputStream fou = openFileOutput("notes.txt", MODE_PRIVATE);
           OutputStreamWriter osw = new OutputStreamWriter(fou);
           try {
               osw.write(data);
               osw.flush();
               osw.close();
               Toast toast = Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT );
               toast.show();

           } catch (IOException e) {
               e.printStackTrace();
           }

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
   }

    @Override
    protected void onStop() {
        super.onStop();
        Toast toast = Toast.makeText(getApplicationContext(), "Attempting to save files..... " , Toast.LENGTH_SHORT );
        toast.show();
        File file = new File(getApplicationContext().getFilesDir(), "SpinnerData");
        String toPut = "";
        for(int i = 0; i < arraySpinner.size(); i++) {
            toPut += arraySpinner.get(i) + " ";
        }

        try {
            FileOutputStream fou = openFileOutput("SpinnerData.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fou);
            try {
                osw.write(toPut);
                osw.flush();
                osw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Toast toast1 = Toast.makeText(getApplicationContext(), "Save Complete Happy Monkey " , Toast.LENGTH_SHORT );
        toast1.show();

    }


    public void loadSpinnerData() {
        FileInputStream in;
        String text = "";
        try {
            in = openFileInput("SpinnerData.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    text += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringTokenizer st = new StringTokenizer(text, " ");
        while(st.hasMoreTokens()) {
            arraySpinner.add(st.nextToken());
        }
        Toast toast1 = Toast.makeText(getApplicationContext(), "Files transfered from Cloud" , Toast.LENGTH_SHORT );
        toast1.show();

    }
    */
}
