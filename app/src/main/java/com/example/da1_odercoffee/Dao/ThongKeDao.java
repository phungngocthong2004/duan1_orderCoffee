package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_odercoffee.database.Dbhepper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDao {
    Dbhepper dbhepper;
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");

    public ThongKeDao(Context context) {
        this.context = context;
         dbhepper = new Dbhepper(context);
        db = dbhepper.getWritableDatabase();
    }

    //top 10


    //thong ke doanh thu

    @SuppressLint("Range")
    public int getDoanhthu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(TongTien) FROM HoaDon Where NgayDat BETWEEN ? AND ? ";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});
        while (c.moveToNext()) {
            list.add(c.getInt(0));
        }

        return list.get(0);
    }
}
