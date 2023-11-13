package com.example.da1_odercoffee.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.Quyen;


public class QuyenDao {
    SQLiteDatabase db;
    Dbhepper dbhepper;
    public QuyenDao(Context context){
          dbhepper=new Dbhepper(context);
         db=dbhepper.getWritableDatabase();

    }
    public long insertQuyen(Quyen quyen){
        ContentValues values=new ContentValues();
        values.put("TenQuyen",quyen.getTenQuyen());
        return  db.insert("Quyen",null ,values);

    }

//    public List<Quyen> getdata(String sql,String ...dieukien){
//        List<Quyen> list = new ArrayList<>();
//        Cursor c = db.rawQuery(sql, dieukien);
//        if (c != null && c.getCount() > 0) {
//            c.moveToFirst();
//            do {
//                list.add(new Quyen(c.getInt(0),c.getString(1)));
//            } while (c.moveToNext());
//        }
//
//        return list;
//    }
public String getTenQuyenTheoma(int id) {
    String tenQuyen = "";
    Cursor c = db.rawQuery("SELECT TenQuyen FROM Quyen WHERE MaQuyen =?", new String[]{String.valueOf(id)});
    if (c != null && c.getCount() > 0) {
        c.moveToFirst();
        tenQuyen = c.getString(0);
    }
    return tenQuyen;
}
}
