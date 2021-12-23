package com.example.filewriterreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FileActivity";

    Button writeBtn, readBtn, pageBtn;
    EditText writeText;
    TextView readText;

    File fileDirectory;
    String fileName = "text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeBtn = (Button) findViewById(R.id.writeBtn);
        readBtn = (Button) findViewById(R.id.readBtn);
        pageBtn = (Button) findViewById(R.id.pageBtn);
        writeText = (EditText) findViewById(R.id.writeText);
        readText = (TextView) findViewById(R.id.readText);

        Context context = getApplication();
        fileDirectory = context.getDataDir();

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFile();
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputFile();
            }
        });

        pageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DatabaseActivity.class);
                startActivity(intent);
            }
        });
    }

    void inputFile() {
//        File file = new File(fileDirectory+"/"+fileName);
//        Log.v(TAG, file.getAbsolutePath());
//        Log.v(TAG, "write file");
//        FileWriter fileWriter = new FileWriter(file);
//        try {
//            String input = writeText.getText().toString();
//            if(writeText.length()>0)
//                writeText.getText().clear();
//            fileWriter.write(input);
//            fileWriter.flush();
//            fileWriter.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String receiveString = "";
        receiveString = writeText.getText().toString();
        Log.v(TAG, receiveString);
        try{
            File file = new File(fileDirectory+"/"+fileName);
            FileOutputStream fileWriter = new FileOutputStream(file);
            Log.v(TAG,"write file");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileWriter);

            outputStreamWriter.write(receiveString);

            outputStreamWriter.flush();
//            fileWriter.flush();
            fileWriter.close();

            writeText.setText("");

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            e.printStackTrace();
        }
    }

    void outputFile() {

//        FileReader fileReader = new FileReader(fileDirectory+"/"+fileName);
        String ret = "";
        try{
            File file = new File(fileDirectory+"/"+fileName);
            FileInputStream fileReader = new FileInputStream(file);
            Log.v(TAG,"read file");
            if ( fileReader != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileReader);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                fileReader.close();
                ret = stringBuilder.toString();
                readText.setText(ret);
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            e.printStackTrace();
        }

//        try {
//            int i;
//            char[] str = new char[0];
//            String output = new String();
//            readText.setText("");
//
//            while((i=fileReader.read())!=-1) {
//                fileReader.read(str);
//                output = output + str.toString();
//            }
//            Log.v(TAG, output);
//            readText.setText(output);
//            fileReader.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}