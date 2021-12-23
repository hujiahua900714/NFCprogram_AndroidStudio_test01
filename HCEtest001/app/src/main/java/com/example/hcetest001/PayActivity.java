package com.example.hcetest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.ReaderCallback;

public class PayActivity extends AppCompatActivity /*implements ReaderCallback*/ {

    //Button Bar declaration
    Button homeBtn, payBtn, chartBtn;

    EditText payAmount;
    Button payingBtn;
    TextView nfcReport;


    private NfcAdapter nfcAdapter;
    private ListView listView;
    //private IsoDepAdapter isoDepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //Button Bar definition
        homeBtn = (Button) findViewById(R.id.homeBtn);
        payBtn = (Button) findViewById(R.id.payBtn);
        chartBtn = (Button) findViewById(R.id.chartBtn);

        payAmount = (EditText) findViewById(R.id.payAmount);
        payingBtn = (Button) findViewById(R.id.payingBtn);
        nfcReport = (TextView) findViewById(R.id.nfcReport);

        //listView = (ListView)findViewById(R.id.listView);
        //isoDepAdapter = new IsoDepAdapter(getLayoutInflater());
//        listView.setAdapter(isoDepAdapter);
//        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        chartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
//                null);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        nfcAdapter.disableReaderMode(this);
//    }
//
//    @Override
//    public void onTagDiscovered(Tag tag) {
//        IsoDep isoDep = IsoDep.get(tag);
//        IsoDepTransceiver transceiver = new IsoDepTransceiver(isoDep, this);
//        Thread thread = new Thread(transceiver);
//        thread.start();
//    }
//
//    @Override
//    public void onMessage(final byte[] message) {
//        runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                isoDepAdapter.addMessage(new String(message));
//            }
//        });
//    }
//
//    @Override
//    public void onError(Exception exception) {
//        onMessage(exception.getMessage().getBytes());
//    }
}