package com.example.filewriterreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;

import java.util.regex.*;

public class DatabaseActivity extends AppCompatActivity {

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private DBhelper dbHelper;

    String digit = "[\\d]+";

    EditText goodsInput, categoryInput, priceInput, idInput;
    LinearLayout dataView;
    Button insertBtn, deleteBtn;
    Button mainBtn, databaseBtn, cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mainBtn = (Button) findViewById(R.id.mainBtn);
        databaseBtn = (Button) findViewById(R.id.databaseBtn);
        cameraBtn = (Button) findViewById(R.id.cameraBtn);

        goodsInput = (EditText) findViewById(R.id.goodsInput);
        categoryInput = (EditText) findViewById(R.id.categoryInput);
        priceInput = (EditText) findViewById(R.id.priceInput);
        idInput = (EditText) findViewById(R.id.idInput);

        dataView = (LinearLayout) findViewById(R.id.dataView);

        insertBtn = (Button) findViewById(R.id.insertBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        dbHelper = new DBhelper(this.getBaseContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = dbHelper.getWritableDatabase();

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatabaseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatabaseActivity.this,CameraActivity.class);
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
        String goods = goodsInput.getText().toString();
        String category = categoryInput.getText().toString();
        String price = priceInput.getText().toString();
        goodsInput.setText("");
        categoryInput.setText("");
        priceInput.setText("");

        long millis=System.currentTimeMillis();
        java.sql.Date sqlDate=new java.sql.Date(millis);
        String date = sqlDate.toString();

        long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put("goods", goods);
        contentValues.put("price", Integer.valueOf(price));
        contentValues.put("category", category);
        contentValues.put("date", date);

        id = db.insert(DataBaseTable, null, contentValues);
        updateDB();
    }

    void deleteDB() {
        String id = idInput.getText().toString();
        if(Pattern.matches(digit, id)) {
            idInput.setText("");
            int idNum = Integer.parseInt(id);

            db.delete(DataBaseTable,"_id= "+idNum,null);

            updateDB();
        }
        else {
            Toast.makeText(this, "this is not a  positive number", Toast.LENGTH_LONG).show();
        }
    }

    void updateDB() {
        StringBuilder allData = new StringBuilder();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable, null);
        String[] idArray = new String[c.getCount()];
        String[] priceArray = new String[c.getCount()];
        String[] goodsArray = new String[c.getCount()];
        String[] categoryArray = new String[c.getCount()];
        String[] dateArray = new String[c.getCount()];
        dataView.removeAllViews();
        c.moveToFirst();
        String[] formatting = new String[5];
        allData.append(String.format("%-4s%-16s%-8s%-16s%-16s%n","_id","goods","price","category","date"));

        for(int i = 0; i < c.getCount(); i++) {
            Button btn = new Button(this);
            String revealedStr = new String();
            String hiddenStr = new String();
            idArray[i] = c.getString(0);
            formatting[0] = c.getString(0);

            goodsArray[i] = c.getString(1);
            formatting[1] = c.getString(1);

            priceArray[i] = c.getString(2);
            formatting[2] = c.getString(2);

            categoryArray[i] = c.getString(3);
            formatting[3] = c.getString(3);

            dateArray[i] = c.getString(4);
            formatting[4] = c.getString(4);

            revealedStr = String.format("%16s%8s", formatting[1], formatting[2]);
            hiddenStr = String.format("%4s%16s%16s", formatting[0], formatting[3], formatting[4]);
            btn.setText(revealedStr);
            btn.setTextColor(Color.BLACK);
            btn.setBackgroundColor(Color.LTGRAY);
            setClick(btn, revealedStr, hiddenStr, btn.getWidth(), btn.getHeight());
            dataView.addView(btn);
            c.moveToNext();
        }
        dataView.setPadding(8,8,8,8);
    }

    public void setClick(final Button btn, String str1, String str2, Integer int1, Integer int2) {
        btn.setOnClickListener(new View.OnClickListener() {
            boolean press = false;
            String revealedStr = str1;
            String hiddenStr = str2;
            Integer width = int1;
            Integer height = int2;
            LinearLayout background = findViewById(R.id.dataView);
            @Override
            public void onClick(View v) {
                background.setPadding(8,8,8,8);
                if(!press) {
                    press = true;
                    btn.setText(hiddenStr);
                    btn.setTextColor(Color.BLACK);
                    btn.setBackgroundColor(Color.WHITE);
                    btn.setWidth(width);
                    btn.setHeight(height);

                }
                else {
                    press = false;
                    btn.setText(revealedStr);
                    btn.setTextColor(Color.BLACK);
                    btn.setBackgroundColor(Color.LTGRAY);
                    btn.setWidth(width);
                    btn.setHeight(height);
                }
            }
        });
    }
}

