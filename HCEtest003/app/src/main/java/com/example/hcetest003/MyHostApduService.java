package com.example.hcetest003;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

public class MyHostApduService extends HostApduService {
    private int messageCounter = 0;

    static String STATUS_SUCCESS = "9000";
    static String STATUS_FAILED = "6F00";
    static String CLA_NOT_SUPPORTED = "6E00";
    static String INS_NOT_SUPPORTED = "6D00";
    static String AID = "A0000002471001";
    static String SELECT_INS = "A4";
    static String DEFAULT_CLA = "00";
    static int MIN_APDU_LENGTH = 12;

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        if (selectAidApdu(apdu)) {
            Log.i("HCEDEMO", "Application selected");
            return getWelcomeMessage();
        }
        else {
            Log.i("HCEDEMO", "Received: " + new String(apdu));
            return getNextMessage();
        }
    }

    private byte[] getWelcomeMessage() {
        return "Hello Desktop!".getBytes();
    }

    private byte[] getNextMessage() {
        return ("Message from android: " + messageCounter++).getBytes();
    }

    private boolean selectAidApdu(byte[] apdu) {
        return apdu.length >= 2 && apdu[0] == (byte)0 && apdu[1] == (byte)0xa4;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i("HCEDEMO", "Deactivated: " + reason);
    }
}
