package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.LoaiMon;
import com.example.da1_odercoffee.model.Mon;

import java.util.ArrayList;
import java.util.List;

public class MonDao {
    Dbhepper dbhepper;
    SQLiteDatabase db;

    public MonDao(Context context) {
        dbhepper = new Dbhepper(context);
        db = dbhepper.getWritableDatabase();

    }

//    public long ThemMon(Mon mon) {
//        ContentValues values = new ContentValues();
//        values.put("TenMon", mon.getTenMon());
//        values.put("GiaTien", mon.getGiaTien());
//        values.put("TrangThai", mon.getTinhTrang());
//        values.put("HinhAnh", mon.getHinhAnh());
//        values.put("Maloai", mon.getMaLoai());
//
//        return db.insert("Mon", null, values);
//    }
//
//    public int SuaMon(Mon mon) {
//        ContentValues values = new ContentValues();
//        values.put("TenMon", mon.getTenMon());
//        values.put("GiaTien", mon.getGiaTien());
//        values.put("TrangThai", mon.getTinhTrang());
//        values.put("HinhAnh", mon.getHinhAnh());
//        values.put("Maloai", mon.getMaLoai());
//        String[] dk = new String[]{String.valueOf(mon.getMaMon())};
//        return db.update("Mon", values, "MaMon=?", dk);
//    }
//
//    public int xoaMon(Mon mon) {
//        String[] dk = new String[]{String.valueOf(mon.getMaMon())};
//        return db.delete("Mon", "MaMon=?", dk);
//    }
public boolean ThemMon(Mon monDTO){
    ContentValues contentValues = new ContentValues();

    contentValues.put("TenMon",monDTO.getTenMon());
    contentValues.put("GiaTien",monDTO.getGiaTien());
    contentValues.put("HinhAnh",monDTO.getHinhAnh());
    contentValues.put("TrangThai","true");
  contentValues .put("MaLoai",monDTO.getMaLoai());
    long ktra = db.insert("Mon",null,contentValues);

    if(ktra !=0){
        return true;
    }else {
        return false;
    }
}

    public boolean XoaMon(int mamon){
        long ktra = db.delete("Mon","MaMon"+ " = " +mamon
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(Mon monDTO,int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenMon",monDTO.getTenMon());
        contentValues.put("GiaTien",monDTO.getGiaTien());
        contentValues.put("HinhAnh",monDTO.getHinhAnh());
        contentValues.put("TrangThai",monDTO.getTinhTrang());
        contentValues .put("MaLoai",monDTO.getMaLoai());
        long ktra = db.update("Mon",contentValues,
                "MaMon"+" = "+mamon,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<Mon> LayDanhSachMonTheoLoai(int maloai) {
        List<Mon> list = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM Mon WHERE MaLoai=?", new String[]{String.valueOf(maloai)});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                list.add(new Mon(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3), c.getBlob(4), c.getInt(5)));
            } while (c.moveToNext());

        }
        return list;
    }

    @SuppressLint("Range")
    public Mon LayMonTheoMa(int id) {
        Mon mon = new Mon();
        Cursor c = db.rawQuery("SELECT * FROM Mon WHERE MaMon=?", new String[]{String.valueOf(id)});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                mon.setMaMon(c.getInt(c.getColumnIndex("MaMon")));
                mon.setTenMon(c.getString(c.getColumnIndex("TenMon")));
                mon.setGiaTien(c.getInt(c.getColumnIndex("GiaTien")));
                mon.setTinhTrang(c.getString(c.getColumnIndex("TrangThai")));
                mon.setHinhAnh(c.getBlob(c.getColumnIndex("HinhAnh")));
                mon.setMaLoai(c.getInt(c.getColumnIndex("Maloai")));
            } while (c.moveToNext());

        }
        return mon;
    }
}
