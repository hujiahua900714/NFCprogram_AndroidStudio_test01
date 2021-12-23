package com.example.filewriterreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private DBhelper dbHelper;

    EditText accountInput, passwordInput, idInput;
    TextView outputText;
    Button insertBtn, deleteBtn, pageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        accountInput = (EditText) findViewById(R.id.accountInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        idInput = (EditText) findViewById(R.id.idInput);

        outputText = (TextView) findViewById(R.id.outputText);

        insertBtn = (Button) findViewById(R.id.insertBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        pageBtn = (Button) findViewById(R.id.pageBtn);

        dbHelper = new DBhelper(this.getBaseContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = dbHelper.getWritableDatabase();

        pageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatabaseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDB();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDB();
            }
        });
    }

    void insertDB() {
        String account = accountInput.getText().toString();
        String password = passwordInput.getText().toString();
        accountInput.setText("");
        passwordInput.setText("");

        long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account);
        contentValues.put("password", password);
        id = db.insert(DataBaseTable, null, contentValues);
        updateDB();
    }

    void deleteDB() {
        String id = idInput.getText().toString();
        idInput.setText("");
        int idNum = Integer.parseInt(id);

        db.delete(DataBaseTable,"_id= "+idNum,null);

        updateDB();
    }

    void updateDB() {
        StringBuilder allData = new StringBuilder();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable, null);
        String[] AccountArray = new String[c.getCount()];
        String[] AccountID = new String[c.getCount()];
        String[] PasswordArray = new String[c.getCount()];
        c.moveToFirst();

        allData.append("_id     account     password\n");
        for(int i = 0; i < c.getCount(); i++) {
            AccountID[i] = c.getString(0);
            allData.append(c.getString(0)+"  ");
            AccountArray[i] = c.getString(1);
            allData.append(c.getString(1)+"  ");
            PasswordArray[i] = c.getString(2);
            allData.append(c.getString(2)+"  \n");
            c.moveToNext();
        }

        outputText.setText(allData);
    }
}