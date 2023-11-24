package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
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
    public void ThemQuyen(String tenquyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenQuyen",tenquyen);
        db.insert("Quyen",null,contentValues);
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
//public String getTenQuyenTheoma(int id) {
//    String tenQuyen = "";
//    Cursor c = db.rawQuery("SELECT TenQuyen FROM Quyen WHERE MaQuyen =?", new String[]{String.valueOf(id)});
//    if (c != null && c.getCount() > 0) {
//        c.moveToFirst();
//        tenQuyen = c.getString(0);
//    }
//    return tenQuyen;
//}
    @SuppressLint("Range")
    public String LayTenQuyenTheoMa(int maquyen){
        String tenquyen ="";
        String query = "SELECT * FROM "+"Quyen"+" WHERE "+"MaQuyen"+" = "+maquyen;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenquyen = cursor.getString(cursor.getColumnIndex("TenQuyen"));
            cursor.moveToNext();
        }
        return tenquyen;
    }
}
