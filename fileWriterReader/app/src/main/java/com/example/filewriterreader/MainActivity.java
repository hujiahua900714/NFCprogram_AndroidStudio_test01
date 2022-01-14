package com.example.filewriterreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FileActivity";
    String[] permissionCode = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS };

    Button mainBtn, databaseBtn, cameraBtn;

    Button writePrivateBtn, writePublicBtn, readPrivateBtn, readPublicBtn;
    EditText writeText;
    TextView readText;

    Button dlJSONBtn;
    String weatherURL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-007?Authorization=CWB-A78BDE64-AEBA-4C17-BB26-DF01D492EF41&format=JSON";
    URL url;

    File fileDirectory;
    String fileName = "text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermission();

        mainBtn = (Button) findViewById(R.id.mainBtn);
        databaseBtn = (Button) findViewById(R.id.databaseBtn);
        cameraBtn = (Button) findViewById(R.id.cameraBtn);

        writePrivateBtn = (Button) findViewById(R.id.writePrivateBtn);
        writePublicBtn = (Button) findViewById(R.id.writePublicBtn);
        readPrivateBtn = (Button) findViewById(R.id.readPrivateBtn);
        readPublicBtn = (Button) findViewById(R.id.readPublicBtn);

        writeText = (EditText) findViewById(R.id.writeText);
        readText = (TextView) findViewById(R.id.readText);
        //File file = Environment.getExternalStoragePublicDirectory(Environment.getStorageDirectory())
        Context context = getApplication();
        //fileDirectory = context.getDataDir();
        //fileDirectory = getExternalFilesDir("txt");

        dlJSONBtn = (Button) findViewById(R.id.dlJSONBtn);

        databaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DatabaseActivity.class);
                startActivity(intent);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });

        writePrivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileDirectory = getExternalFilesDir("txt");
                inputFilePrivate();
            }
        });

        writePublicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            23);
                }
                fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                inputFilePublic();
            }
        });

        readPrivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileDirectory = getExternalFilesDir("txt");
                outputFilePrivate();
            }
        });

        readPublicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            24);
                }
                fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                outputFilePublic();
            }
        });

        dlJSONBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(()-> {
                    try {
                        fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                        if (!fileDirectory.exists()) {
                            Log.v(TAG,"create directory");
                            fileDirectory.mkdirs();
                        }

                        //String fileName = "test.json";
                        File file = new File(fileDirectory + "/" + fileName);
                        Log.v(TAG,"create new file");

                        Log.v(TAG,file.getAbsolutePath());

                        FileOutputStream fileWriter = new FileOutputStream(file);
                        Log.v(TAG,"write file");

                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileWriter);

                        //outputStreamWriter.write("");
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
                            outputStreamWriter.write(line);
                            outputStreamWriter.flush();
                            line = in.readLine();
                        }
                        //fileWriter.close();
                        outputStreamWriter.close();

                        //Log.v(TAG,""+json);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();


            }
        });
    }

    void inputFilePrivate() {
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

    void inputFilePublic() {
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

    void outputFilePrivate() {

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
    }

    void outputFilePublic() {
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
    }

    public void setPermission() {
        for(int i = 0; i < 7; i++) {
            if(ContextCompat.checkSelfPermission(this,
                    permissionCode[i]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        permissionCode,
                        24);
                break;
            }
        }
    }

}