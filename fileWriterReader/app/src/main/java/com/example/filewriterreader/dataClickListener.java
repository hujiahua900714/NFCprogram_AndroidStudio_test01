package com.example.filewriterreader;

import android.view.View;

public class dataClickListener implements View.OnClickListener {
    boolean press = false;
    String revealedStr = new String();
    String hiddenStr = new String();
    public void sendParams(String str1, String str2) {
        revealedStr = str1;
        hiddenStr = str2;
    }

    @Override
    public void onClick(View v) {

    }
}
