package com.example.anjanbharadwaj.agendaplusapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;




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


public class Notes extends AppCompatActivity {

    int fileName = 0;
    public String[] noteData = new String[10];
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    
//SREEHARI'S CODE UNDER HERE
    String currentFile = "notes";



    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);




        //-------------------------------------------
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


        //--------------------------------------------------






        //-------------------------------------------
        FileInputStream in2 = null;
        try {
            in2 = openFileInput("notes2.txt");
            InputStreamReader inputStreamReader2 = new InputStreamReader(in2);
            BufferedReader bufferedReader2 = new BufferedReader(inputStreamReader2);
            StringBuilder sb2 = new StringBuilder();
            String line;
            String text = "";


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        File file = getFileStreamPath("notes2.txt");
        String FILENAME2 = "notes2.txt";
        String string2 = "Blank number 2";


        FileOutputStream fos2 = null;
        try {
            fos2 = openFileOutput(FILENAME2, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos2.write(string2.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




        //--------------------------------------------------


        //-------------------------------------------
        FileInputStream in3 = null;
        try {
            in3 = openFileInput("notes3.txt");
            InputStreamReader inputStreamReader3 = new InputStreamReader(in3);
            BufferedReader bufferedReader3 = new BufferedReader(inputStreamReader3);
            StringBuilder sb3 = new StringBuilder();
            String line;
            String text = "";


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        File file3 = getFileStreamPath("notes3.txt");
        String FILENAME3 = "notes3.txt";
        String string3 = "Blank number 3";


        FileOutputStream fos3 = null;
        try {
            fos3 = openFileOutput(FILENAME3, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos3.write(string3.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //--------------------------------------------------


        FileInputStream in4 = null;
        try {
            in4 = openFileInput("notes4.txt");
            InputStreamReader inputStreamReader4 = new InputStreamReader(in4);
            BufferedReader bufferedReader4 = new BufferedReader(inputStreamReader4);
            StringBuilder sb4 = new StringBuilder();
            String line;
            String text = "";


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        File file4 = getFileStreamPath("notes4.txt");
        String FILENAME4 = "notes4.txt";
        String string4 = "Blank number 4";


        FileOutputStream fos4 = null;
        try {
            fos4 = openFileOutput(FILENAME4, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos4.write(string4.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos4.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //--------------------------------------------------




        FileInputStream in5 = null;
        try {
            in5 = openFileInput("notes5.txt");
            InputStreamReader inputStreamReader5 = new InputStreamReader(in5);
            BufferedReader bufferedReader4 = new BufferedReader(inputStreamReader5);
            StringBuilder sb5 = new StringBuilder();
            String line;
            String text = "";


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File f5 = getFileStreamPath("notes5.txt");
        String FILENAME5 = "notes5.txt";
        String string5 = "Blank number 5";


        FileOutputStream fos5 = null;
        try {
            fos5 = openFileOutput(FILENAME4, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos5.write(string5.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos5.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //--------------------------------------------------








        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.country_names, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                Toast.makeText(getBaseContext(),  parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG ).show();
                String strFile = parent.getItemAtPosition(position).toString();
                if(strFile == null) strFile = "notes";
                Toast.makeText(getBaseContext(),  "The name of the file is " + strFile, Toast.LENGTH_LONG ).show();
                FileInputStream in = null;
                String line;
                try {
                    in = openFileInput(strFile + ".txt");
                    InputStreamReader inputStreamReader = new InputStreamReader(in);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    line = "Empty";
                    String text = "";
                    try {
                        while ((line = bufferedReader.readLine()) != null) {
                            text += line;
                        }
                        Toast toast = Toast.makeText(getApplicationContext(), "Data Retrieved from " + strFile, Toast.LENGTH_SHORT );
                        EditText notes = (EditText)findViewById(R.id.usernotes);
                        notes.setText(text);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }




            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }






        });


















    }




    public void add(View view) {




    }




    public void access(View view) {




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




    public void delete(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_SHORT );
        toast.show();




        EditText notes = (EditText)findViewById(R.id.usernotes);
        String data = notes.getText().toString().trim();;




        File file = getFileStreamPath("notes.txt");
        String FILENAME = "notes.txt";
        String string = "";




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








}


