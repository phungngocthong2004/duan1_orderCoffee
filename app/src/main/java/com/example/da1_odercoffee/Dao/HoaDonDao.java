package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDao {
    Dbhepper dbhepper;
    SQLiteDatabase db;
    public HoaDonDao(Context context){
        dbhepper= new Dbhepper(context);
        db = dbhepper.getWritableDatabase();
    }
    public long ThemHoaDon(HoaDon hoaDon){
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaNV",hoaDon.getMaNhanVien());
        contentValues.put("MaBan",hoaDon.getMaBan());
        contentValues.put("NgayDat",hoaDon.getNgayDat());
        contentValues.put("TinhTrang",hoaDon.getTinhTrang());
        contentValues.put("TongTien",hoaDon.getTongTien());

        return db.insert("HoaDon",null,contentValues);

    }
    public List<HoaDon> LayTatCaHoaDon() {
        List<HoaDon> list = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM HoaDon ", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                list.add(new HoaDon(c.getInt(0), c.getInt(1),c.getInt(2),c.getString(3),c.getString(4),c.getInt(5)));
            } while (c.moveToNext());

        }
        return list;
    }
    public List<HoaDon> LayDanhSachDonDatTheoNgay(String NgayThang) {
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM HoaDon where NgayDat like "+"'%"+NgayThang+"%'", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                list.add(new HoaDon(c.getInt(0), c.getInt(1),c.getInt(2),c.getString(3),c.getString(4),c.getInt(5)));
            } while (c.moveToNext());

        }
        return list;
    }
    public long LayMahoaDonTheoMaBan(int maban,String TinhTrang){
        long magoimon=0 ;

        Cursor cursor = db.rawQuery("SELECT * FROM HoaDon Where MaBan = "+maban+"  AND TinhTrang= "+TinhTrang+"",null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
            magoimon=cursor.getInt(0);
            } while (cursor.moveToNext());

        }
        return magoimon;
    }
  
    public int UpdateTongTienHoaDon(HoaDon hoaDon){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TongTien",hoaDon.getTongTien());
        String[] dk=new String[]{String.valueOf(hoaDon.getMaHoaDon())};
        return  db.update("HoaDon",contentValues,"MaHoaDon=?" ,dk);

    }
    public int UpdateTThaiHoaDonTheoMaBan(HoaDon hoaDon){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TinhTrang",hoaDon.getTinhTrang());
        String[] dk=new String[]{String.valueOf(hoaDon.getMaHoaDon())};
        return  db.update("HoaDon",contentValues,"MaHoaDon=?" ,dk);
    }
}
