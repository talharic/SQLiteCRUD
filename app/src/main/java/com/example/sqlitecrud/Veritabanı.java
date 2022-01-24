package com.example.sqlitecrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Veritabanı extends SQLiteOpenHelper {
    private static final String VERİTABANI_ADI = "ogrenciler";
    private static final int SURUM = 1;

    public Veritabanı(Context context) {
        super(context, VERİTABANI_ADI,null,SURUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ogrencibilgi (ad Text, soyad Text, yas Integer, sehir Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ogrencibilgi");
        onCreate(db);
    }
}
