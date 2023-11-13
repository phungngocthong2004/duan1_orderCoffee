package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.ThanhToan;

import java.util.ArrayList;
import java.util.List;

public class ThanhToanDao {
    Dbhepper dbhepper;
    SQLiteDatabase db;
    public ThanhToanDao(Context context){
        dbhepper=new Dbhepper(context);
        db =  dbhepper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<ThanhToan> LayDSMonTheoMaDon(int madondat){
        List<ThanhToan> thanhToanDTOS = new ArrayList<ThanhToan>();
//        String sql = "SELECT * FROM "+CreateDatabase.TBL_CHITIETDONDAT+" ctdd,"+CreateDatabase.TBL_MON+" mon WHERE "
//                +"ctdd."+CreateDatabase.TBL_CHITIETDONDAT_MAMON+" = mon."+CreateDatabase.TBL_MON_MAMON+" AND "
//                +CreateDatabase.TBL_CHITIETDONDAT_MADONDAT+" = '"+madondat+"'";

        Cursor cursor = db.rawQuery("SELECT * FROM ChiTietHoaDon cthd Join  Mon mon On cthd.MaMon = mon.MaMon AND cthd.MaHoaDon=? ",new String[]{String.valueOf(madondat)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToan thanhToan = new ThanhToan();
            thanhToan.setSoLuong(cursor.getInt(cursor.getColumnIndex("SoLuong")));
            thanhToan.setGiaTien(cursor.getInt(cursor.getColumnIndex("GiaTien")));
            thanhToan.setTenMon(cursor.getString(cursor.getColumnIndex("TenMon")));
            thanhToan.setHinhAnh(cursor.getBlob(cursor.getColumnIndex("HinhAnh")));
            thanhToanDTOS.add(thanhToan);

            cursor.moveToNext();
        }

        return thanhToanDTOS;
    }
}
