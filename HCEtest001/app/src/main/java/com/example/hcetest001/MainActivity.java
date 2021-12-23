package com.example.hcetest001;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;


import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.nfc.NfcAdapter;
import android.nfc.NdefMessage;
import android.nfc.cardemulation.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    //Button Bar declaration
    Button homeBtn, payBtn, chartBtn;
    TextView state;

    Button weatherBtn;
    String weatherURL;
    String TAG;
    File fileDirectory;
    String directory;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button Bar definition
        homeBtn = (Button) findViewById(R.id.homeBtn);
        payBtn = (Button) findViewById(R.id.payBtn);
        chartBtn = (Button) findViewById(R.id.chartBtn);

        state = (TextView) findViewById(R.id.state);
        TAG = MainActivity.class.getSimpleName();
        weatherBtn = (Button) findViewById(R.id.weatherBtn);

        weatherURL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/";
//        https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-007?Authorization=CWB-A78BDE64-AEBA-4C17-BB26-DF01D492EF41&format=JSON

        directory = "/account";
        Context context = getBaseContext();
        fileDirectory = context.getDataDir();
        Log.v(TAG, fileDirectory.getAbsolutePath());
//        fileDirectory = new File("/internal storage"+directory);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PayActivity.class);
                startActivity(intent);
            }
        });

        chartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catchWeatherData();
            }
        });
    }

    private void catchWeatherData() {
        weatherURL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-007?Authorization=CWB-A78BDE64-AEBA-4C17-BB26-DF01D492EF41&format=JSON";
        new Thread(()-> {
            try {

//                ActivityCompat.requestPermissions(this, new String[]{
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                }, 1);

                if (!fileDirectory.exists()) {
                    Log.v(TAG,"create directory");
                    //Toast.makeText(getBaseContext(),"make directory",Toast.LENGTH_LONG).show();
                    fileDirectory.mkdirs();
                }

                String fileName = "/test.txt";
                File file = new File(fileDirectory.getAbsolutePath()+fileName);
                Log.v(TAG,"create new file");
                //Toast.makeText(getBaseContext(),"create new file",Toast.LENGTH_LONG).show();
                Log.v(TAG,file.getAbsolutePath());
                //Toast.makeText(getBaseContext(),file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                //file.createNewFile();
//                file.setWritable(true);
                FileWriter fileWriter = new FileWriter(file);
                Log.v(TAG,"create new file writer");

                URL url = new URL(weatherURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line = in.readLine();
                StringBuffer json = new StringBuffer();
                while (line != null) {
                    json.append(line);
                    //fileWriter.write(line);
                    //fileWriter.flush();
                    line = in.readLine();
                }
                //fileWriter.close();

                Log.v(TAG,""+json);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void fileCreate() {
        if(fileDirectory.exists()) {


        }

    }
}