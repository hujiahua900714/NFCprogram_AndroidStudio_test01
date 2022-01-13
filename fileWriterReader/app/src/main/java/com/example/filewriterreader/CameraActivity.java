package com.example.filewriterreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "Parser";
//    public ArrayList<String> permissionCode;
    String[] permissionCode = new String[] {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS };
    ArrayList<String> parser = new ArrayList<String>();
    String smsto = "([^:]+)([:])([\\d]+):(.+)";

    Button mainBtn, databaseBtn, cameraBtn;
    Button uriBtn;

    //camera object
    private CodeScanner cameraScanner;
    CodeScannerView cameraView;
    EditText uriText;
//    CameraSource cameraSource;
//    BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //permissionCode = new ArrayList<String>();
        setPermission();

        mainBtn = (Button) findViewById(R.id.mainBtn);
        databaseBtn = (Button) findViewById(R.id.databaseBtn);
        cameraBtn = (Button) findViewById(R.id.cameraBtn);

        cameraView = (CodeScannerView) findViewById(R.id.cameraView);
        cameraScanner = new CodeScanner(this, cameraView);

        uriText = (EditText) findViewById(R.id.uriText);
        uriBtn = (Button) findViewById(R.id.uriBtn);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        CameraSource cameraSource=new CameraSource.Builder(this,barcodeDetector).setRequestedPreviewSize(300,300).setAutoFocusEnabled(true).build();

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setPermission();
                Intent intent = new Intent(CameraActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        databaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setPermission();
                Intent intent = new Intent(CameraActivity.this,DatabaseActivity.class);
                startActivity(intent);
            }
        });

        cameraScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setPermission();
                        Toast.makeText(CameraActivity.this, result.getText(), Toast.LENGTH_SHORT).show();

                        String str = uriText.getText().toString();
                        Pattern pattern = Pattern.compile(smsto);
                        Matcher matcher = pattern.matcher(str);
                        parser.clear();
                        if(matcher.find()) {
                            parser.add(matcher.group(1));
                            parser.add(matcher.group(3));
                            parser.add(matcher.group(4));
                            uriText.setText(parser.get(2));
                        }
                        else {
                            parser.add("none");
                            uriText.setText(result.getText());
                        }
                        Log.v(TAG, str);
                    }
                });
            }
        });

        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermission();
                cameraScanner.startPreview();
            }
        });

        uriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
//                Log.v(TAG, parser.get(0));
//                Log.v(TAG, parser.get(1));
//                Log.v(TAG, parser.get(2));
                if(parser.get(0).equals("smsto") || parser.get(0).equals("SMSTO")) {
                    uri = Uri.parse("smsto:" + parser.get(1));
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", parser.get(2));
                    startActivity(intent);
                }
                else {
                    uri = Uri.parse(uriText.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onResume() {
        setPermission();
        super.onResume();
        cameraScanner.startPreview();
    }

    @Override
    public void onPause() {
        cameraScanner.releaseResources();
        super.onPause();
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