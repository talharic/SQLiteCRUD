package com.example.sqlitecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button kaydet,sil,guncelle,goster;
    EditText ad,soyad,yas,sehir;
    TextView bilgiler;
    private Veritabanı v1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v1 = new Veritabanı(this);
        kaydet = (Button) findViewById(R.id.btnKayit);
        sil = (Button) findViewById(R.id.btnSil);
        guncelle = (Button) findViewById(R.id.btnGuncelle);
        goster = (Button) findViewById(R.id.btnGoster);
        ad = (EditText) findViewById(R.id.edtAd);
        soyad = (EditText) findViewById(R.id.edtSoyad);
        yas = (EditText) findViewById(R.id.edtSoyad);
        bilgiler = (TextView) findViewById(R.id.tvBilgiler);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    kayitEkle(ad.getText().toString(),soyad.getText().toString(),yas.getText().toString(),sehir.getText().toString());
                    Toast.makeText(MainActivity.this, "Eklendi", Toast.LENGTH_SHORT).show();
                }
                finally {
                    Toast.makeText(MainActivity.this, "Hata", Toast.LENGTH_SHORT).show();
                    v1.close();
                }
            }
        });

        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = kayitGetir();
                kayitGoster(cursor);
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sil(ad.getText().toString());
            }
        });

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guncelle(ad.getText().toString(),soyad.getText().toString(),yas.getText().toString(),sehir.getText().toString());
            }
        });
    }
    private void kayitEkle(String adi, String soyadi, String yasi, String sehri){
        SQLiteDatabase db = v1.getWritableDatabase();
        ContentValues veriler = new ContentValues();
        veriler.put("ad",adi);
        veriler.put("soyad",soyadi);
        veriler.put("yas",yasi);
        veriler.put("sehir",sehri);

        db.insertOrThrow("ogrencibilgi",null, veriler);
    }

    private String[] sutunlar = {"ad","soyad","yas","sehir"};

    private Cursor kayitGetir(){
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor cursor = db.query("ogrencibilgi",sutunlar,null,null,null,null,null);
        return cursor;
    }
    private void kayitGoster(Cursor goster){
        StringBuilder builder = new StringBuilder();
        while(goster.moveToNext()){
            String add = goster.getString(goster.getColumnIndexOrThrow("ad"));
            String soyadd = goster.getString(goster.getColumnIndexOrThrow("soyad"));
            String numaraa = goster.getString(goster.getColumnIndexOrThrow("sehir"));
            String bolumm = goster.getString(goster.getColumnIndexOrThrow("yas"));
            builder.append("Ad:").append(add+"\n");
            builder.append("Soyad:").append(soyadd+"\n");
            builder.append("Şehir:").append(numaraa+"\n");
            builder.append("Yaş:").append(bolumm+"\n");
            builder.append("---------------:").append("\n");
        }
        TextView text = (TextView) findViewById(R.id.tvBilgiler);
        text.setText(builder);
    }

    private void sil(String adi){
        SQLiteDatabase db = v1.getReadableDatabase();
        db.delete("ogrencibilgi","ad"+"=?",new String[]{adi});
    }

    public void guncelle(String ad,String soyad,String yas,String sehir){
        SQLiteDatabase db=v1.getWritableDatabase();
        ContentValues cvGuncelle = new ContentValues();
        cvGuncelle.put("ad",ad);
        cvGuncelle.put("soyad",soyad);
        cvGuncelle.put("yas",yas);
        cvGuncelle.put("sehir",sehir);
        db.update("ogrencibilgi",cvGuncelle,"ad"+ "=?",new String[]{ad});
        db.close();
    }
}