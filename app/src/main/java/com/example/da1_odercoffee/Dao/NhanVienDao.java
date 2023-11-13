package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDao {
    Dbhepper dbhepper;
    SQLiteDatabase db;

    public NhanVienDao(Context context) {
        dbhepper = new Dbhepper(context);
        db = dbhepper.getWritableDatabase();

    }

    public long ThemNhanVien(NhanVien nv) {
        ContentValues values = new ContentValues();
        values.put("hoTenNv", nv.getHoTenNV());
        values.put("tenDN", nv.getTenDN());
        values.put("MatKhau", nv.getMatKhau());
        values.put("SoDT", nv.getSoDT());
        values.put("MaQuyen", nv.getMaQuyen());
        return db.insert("NhanVien", null, values);
    }

    public int SuaNhanVien(NhanVien nv) {
        ContentValues values = new ContentValues();
        values.put("hoTenNv", nv.getHoTenNV());
        values.put("tenDN", nv.getTenDN());
        values.put("MatKhau", nv.getMatKhau());
        values.put("SoDT", nv.getSoDT());
        values.put("MaQuyen", nv.getMaQuyen());
        String[] dk = new String[]{String.valueOf(nv.getMaNV())};
        return db.update("NhanVien", values, "MaNV=?", dk);
    }

    public int xoaNhanVien(NhanVien nv) {
        String[] dk = new String[]{String.valueOf(nv.getMaNV())};
        return db.delete("NhanVien", "MaNV=?", dk);
    }

    @SuppressLint("Range")
    public NhanVien LayNVTheoMa(int id) {
        NhanVien nhanVien = new NhanVien();
        Cursor c = db.rawQuery("SELECT * FROM NhanVien WHERE MaNV=?", new String[]{String.valueOf(id)});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                nhanVien.setMaNV(c.getInt(c.getColumnIndex("MaNV")));
                nhanVien.setHoTenNV(c.getString(c.getColumnIndex("hoTenNv")));
                nhanVien.setTenDN(c.getString(c.getColumnIndex("tenDN")));
                nhanVien.setMatKhau(c.getString(c.getColumnIndex("MatKhau")));
                nhanVien.setSoDT(c.getString(c.getColumnIndex("SoDT")));
                nhanVien.setMaQuyen(c.getInt(c.getColumnIndex("MaQuyen")));
            } while (c.moveToNext());

        }
        return nhanVien;
    }

    public int layQuyenNV(int id) {
        int maquyen = 0;
        Cursor c = db.rawQuery("SELECT * FROM NhanVien WHERE MaNV=?", new String[]{String.valueOf(id)});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                maquyen = c.getInt(0);
            } while (c.moveToNext());
        }
        return maquyen;
    }

    private List<NhanVien> getdata(String sql, String... dieukien) {
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, dieukien);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                list.add(new NhanVien(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5)));
            } while (c.moveToNext());
        }

        return list;
    }

    public List<NhanVien> LayDanhsachNhanVien() {
        String sql = "SELECT * From NhanVien";
        return getdata(sql);
    }

    public int checklogin(String tenDN, String MatKhau) {
        String sql = "SELECT * FROM NhanVien WHERE tenDN=? AND MatKhau=?";
        List<NhanVien> list = getdata(sql, tenDN, MatKhau);
        if (list.size() == 0) {
            return -1;
        }
        return 1;


    }

    public boolean KtraTonTaiNhanVien() {
        String query = "SELECT * FROM NhanVien";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }
//    public int KiemTraDangNhap(String tenDN,String MatKhau){
//      int manv=0;
//      Cursor c=db.rawQuery("SELECT * FROM NhanVien WHERE tenDN=? AND MatKhau=? ",new String[]{tenDN,MatKhau});
//        if (c!=null&& c.getCount()>0){
//            c.moveToFirst();
//            do {
//                manv=c.getInt(0);
//            }while (c.moveToNext());
//        }
//        return  manv;
//    }

//    public List<NhanVien> LayDanhSachNV(){
//        List<NhanVien>list=new ArrayList<>();
//        String sql="SELECT * FROM NhanVien";
//        Cursor c=db.rawQuery(sql,null);
//        if (c!=null&& c.getCount()>0){
//            c.moveToFirst();
//            do {
//                list.add(new NhanVien(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5)));
//            }while (c.moveToNext());
//
//        }
//        return  list;
//    }

}
