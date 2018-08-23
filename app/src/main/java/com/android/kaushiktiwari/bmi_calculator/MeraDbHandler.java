package com.android.kaushiktiwari.bmi_calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import static android.R.attr.name;

/**
 * Created by kaushik Tiwari on 17-06-2018.
 */

public class MeraDbHandler extends SQLiteOpenHelper {

    SQLiteDatabase db;
    Context context;
    MeraDbHandler(Context context) {
        super(context, "persondb", null, 1);
        this.context = context;
        db = this.getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table person(bmi text,msg text,time text)";
        db.execSQL(sql);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    public void addperson(String bmi,String msg,String time) {
        ContentValues cv = new ContentValues();
        cv.put("bmi",bmi);
        cv.put("msg",msg);
        cv.put("time",time);
        long rid = db.insert("person", null, cv);
        if (rid < 0)
           Toast.makeText(context, "Data Not Saved", Toast.LENGTH_SHORT).show();
       // else
          // Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();

        }

    public String viewperson() {
        StringBuffer data = new StringBuffer();
        Cursor cursor = db.query("person", null, null, null, null, null, null, null);
        cursor.moveToFirst();
        int i=0;
        if (cursor.getCount() > 0)
        {
            do {
                  i++;
                data.append(i+")"+" "+"BMI: "+cursor.getString(0)+"\n\t\t"+cursor.getString(1)+"\n\t\t"+cursor.getString(2)+"\n\n");
            } while (cursor.moveToNext());
        }
        return data.toString();
    }



}