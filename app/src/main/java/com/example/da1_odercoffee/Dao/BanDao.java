package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.Ban;

import java.util.ArrayList;
import java.util.List;

public class BanDao {
    Dbhepper dbhepper;
    SQLiteDatabase db;

    public BanDao(Context context) {
        dbhepper = new Dbhepper(context);
        db = dbhepper.getWritableDatabase();

    }

    public boolean ThemBanAn(String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put( "TenBan",tenban);
        contentValues.put("TinhtrangBan","false");

        long ktra = db.insert("Ban",null,contentValues);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public int CapnhatTenBan(Ban ban) {
        ContentValues values = new ContentValues();
        values.put("TenBan", ban.getTenban());

        String[] dk = new String[]{String.valueOf(ban.getMaBan())};
        return db.update("Ban", values, "MaBan=?", dk);
    }

    public int xoaBan(Ban ban) {
        String[] dk = new String[]{String.valueOf(ban.getMaBan())};
        return db.delete("Ban","Maban=?", dk);
    }

    public List<Ban> LayTatCaBan() {
        List<Ban> list = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Ban ", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                list.add(new Ban(c.getInt(0), c.getString(1)));
            } while (c.moveToNext());

        }
        return list;
    }
    public boolean XoaBanTheoMa(int maban){
        long ktra =db.delete("Ban","MaBan"+" = "+maban,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }
    @SuppressLint("Range")
    public String LayTinhTrangBanTheoMa(int id) {
        String tinhtrang="";
        Cursor c = db.rawQuery("SELECT * FROM Ban WHERE MaBan=?", new String[]{String.valueOf(id)});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
            tinhtrang= c.getString(0);
            } while (c.moveToNext());

        }
        return  tinhtrang;
    }
    public int CapNhatTinhTrangBan(int maban,String tinhTrang){
        ContentValues values = new ContentValues();
        values.put("TinhtrangBan",tinhTrang);
        String[] dk = new String[]{String.valueOf(maban)};
        return  db.update("Ban",values,"MaBan=?",dk);

    }
    public String LayTenBanTheoMa(int maban){
        String tenban="";

        Cursor cursor = db.rawQuery("SELECT * FROM Ban Where MaBan=?",new String[]{String.valueOf(maban)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenban = cursor.getString(0);
            cursor.moveToNext();
        }

        return tenban;
    }

}

