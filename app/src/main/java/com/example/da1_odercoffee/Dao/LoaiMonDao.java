package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.LoaiMon;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonDao {
    Dbhepper dbhepper;
    SQLiteDatabase db;
    public LoaiMonDao(Context context){
        dbhepper=new Dbhepper(context);
        db=dbhepper.getWritableDatabase();

    }
//    public  long ThemLoaiMon(LoaiMon ls){
//        ContentValues values=new ContentValues();
//        values.put("TenLoai",ls.getTenLoai());
//        values.put("HinhAnh",ls.getHinhAnh());
//        return db.insert("LoaiMon",null,values);
//    }
//    public  int SuaLoaiMon(LoaiMon ls){
//        ContentValues values=new ContentValues();
//        values.put("TenLoai",ls.getTenLoai());
//        values.put("HinhAnh",ls.getHinhAnh());
//        String[] dk=new String[]{String.valueOf(ls.getMaLoai())};
//        return db.update("LoaiMon",values,"MaLoai=?",dk);
//    }
//    public  int xoaLoaiMon(LoaiMon ls){
//        String[] dk=new String[]{String.valueOf(ls.getMaLoai())};
//        return db.delete("LoaiMon","MaLoai=?",dk);
//    }
public boolean ThemLoaiMon(LoaiMon loaiMonDTO){
    ContentValues contentValues = new ContentValues();
    contentValues.put("TenLoai",loaiMonDTO.getTenLoai());
    contentValues.put("HinhAnh",loaiMonDTO.getHinhAnh());
    long ktra = db.insert("LoaiMon",null,contentValues);

    if(ktra != 0){
        return true;
    }else {
        return false;
    }
}

    public boolean XoaLoaiMon(int maloai){
        long ktra = db.delete("LoaiMon","MaLoai"+ " = " +maloai
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiMon(LoaiMon loaiMonDTO,int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai",loaiMonDTO.getTenLoai());
        contentValues.put("HinhAnh",loaiMonDTO.getHinhAnh());
        long ktra = db.update("LoaiMon",contentValues,"MaLoai"+" = "+maloai,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<LoaiMon> LayDanhSachLoaiMon(){
        List<LoaiMon>list=new ArrayList<>();
        String sql="SELECT * FROM LoaiMon";
        Cursor c=db.rawQuery(sql,null);
        if (c!=null&& c.getCount()>0){
            c.moveToFirst();
            do {
                list.add(new LoaiMon(c.getInt(0),c.getString(1),c.getBlob(2)));
            }while (c.moveToNext());

        }
        return  list;
    }
    @SuppressLint("Range")
    public LoaiMon LayLoaiMonTheoMa(int id){
        LoaiMon loaiMon=new LoaiMon();
        Cursor c=db.rawQuery("SELECT * FROM LoaiMon WHERE MaLoai=?",new String[]{String.valueOf(id)});
        if (c!=null&& c.getCount()>0){
            c.moveToFirst();
            do {
              loaiMon.setMaLoai(c.getInt(c.getColumnIndex("MaLoai")));
              loaiMon.setTenLoai(c.getString(c.getColumnIndex("TenLoai")));
              loaiMon.setHinhAnh(c.getBlob(c.getColumnIndex("HinhAnh")));
            }while (c.moveToNext());

        }
        return  loaiMon;
    }
}
