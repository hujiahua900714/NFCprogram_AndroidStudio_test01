package com.example.hcetest003;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputTxt;
    TextView outputTxt;

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter[] filters;
    String[][] techLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputTxt = (EditText) findViewById(R.id.inputTxt);
        outputTxt = (TextView) findViewById(R.id.outputTxt);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);


        //Here you define an intent that will be raised when a tag is received.
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        //These are the tag conditions to throw the intent of above.
        //ACTION_TECH_DISCOVERED means the tag need to be of the type defined in the techlist.
        filters = new IntentFilter[] { new IntentFilter(
                NfcAdapter.ACTION_TECH_DISCOVERED) };

        techLists = new String[][] { { "android.nfc.tech.IsoPcdA" } };
        //PendingIntent outputData = new PendingIntent(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
        //        null);
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters,
                    techLists);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // Get the tag from the given intent
        super.onNewIntent(intent);
        Tag t = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    }
}