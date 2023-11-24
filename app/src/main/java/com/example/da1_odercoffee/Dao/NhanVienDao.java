package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


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

//    public int SuaNhanVien(NhanVien nv) {
//        ContentValues values = new ContentValues();
//        values.put("hoTenNv", nv.getHoTenNV());
//        values.put("tenDN", nv.getTenDN());
//        values.put("MatKhau", nv.getMatKhau());
//        values.put("SoDT", nv.getSoDT());
//        values.put("MaQuyen", nv.getMaQuyen());
//        String[] dk = new String[]{String.valueOf(nv.getMaNV())};
//        return db.update("NhanVien", values, "MaNV=?", dk);
//    }
public long SuaNhanVien(NhanVien nhanVienDTO,int manv){
    ContentValues contentValues = new ContentValues();
    contentValues.put("hoTenNv",nhanVienDTO.getHoTenNV());
    contentValues.put("tenDN",nhanVienDTO.getTenDN());
    contentValues.put("MatKhau",nhanVienDTO.getMatKhau());
    contentValues.put("SoDT",nhanVienDTO.getSoDT());
    contentValues.put("MaQuyen",nhanVienDTO.getMaQuyen());

    long ktra = db.update("NhanVien",contentValues,
            "MaNV"+" = "+manv,null);
    return ktra;
}
    public boolean XoaNV(int manv){
        long ktra = db.delete("NhanVien","MaNV"+ " = " +manv
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public NhanVien LayNVTheoMa(int id) {
        NhanVien nhanVien = new NhanVien();

        Cursor c = db.rawQuery("SELECT * FROM NhanVien WHERE MaNV=?",new String[]{String.valueOf(id)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            nhanVien.setMaNV(c.getInt(c.getColumnIndex("MaNV")));
            nhanVien.setHoTenNV(c.getString(c.getColumnIndex("hoTenNv")));
            nhanVien.setTenDN(c.getString(c.getColumnIndex("tenDN")));
            nhanVien.setMatKhau(c.getString(c.getColumnIndex("MatKhau")));
            nhanVien.setSoDT(c.getString(c.getColumnIndex("SoDT")));
            nhanVien.setMaQuyen(c.getInt(c.getColumnIndex("MaQuyen")));
            c.moveToNext();
        }


        return nhanVien;
    }
//    public String getTenNhanVien(int id) {
//        String tennv = "";
//        Cursor c = db.rawQuery("SELECT hoTenNv FROM NhanVien WHERE MaNV =?", new String[]{String.valueOf(id)});
//        if (c != null && c.getCount() > 0) {
//            c.moveToFirst();
//            tennv = c.getString(0);
//        }
//        return tennv;
//    }
    public String getTenNhanVien(int id) {
        String tennv = "";
        Cursor c = db.rawQuery("SELECT hoTenNv FROM NhanVien WHERE MaNV =?", new String[]{String.valueOf(id)});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            tennv = c.getString(0);
        } else {
            Log.d("NhanVienDao", "Không tìm thấy nhân viên với mã: " + id);
        }
        return tennv;
    }
//    @SuppressLint("Range")
//    public NhanVienDTO LayNVTheoMa(int manv){
//        NhanVienDTO nhanVienDTO = new NhanVienDTO();
//        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN+" WHERE "+CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv;
//        Cursor cursor = database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//             nhanVien.setMaNV(c.getInt(c.getColumnIndex("MaNV")));
//                nhanVien.setHoTenNV(c.getString(c.getColumnIndex("hoTenNv")));
//                nhanVien.setTenDN(c.getString(c.getColumnIndex("tenDN")));
//                nhanVien.setMatKhau(c.getString(c.getColumnIndex("MatKhau")));
//                nhanVien.setSoDT(c.getString(c.getColumnIndex("SoDT")));
//                nhanVien.setMaQuyen(c.getInt(c.getColumnIndex("MaQuyen")));
//
//            cursor.moveToNext();
//        }
//        return nhanVienDTO;
//    }



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
    public List<NhanVien> getimKiem(String name) {
        String sql = ("SELECT * FROM NhanVien where hoTenNv like "+"'%"+name+"%'");
        return getdata(sql);
    }

//    public int checklogin(String tenDN, String MatKhau) {
//        String sql = "SELECT * FROM NhanVien WHERE tenDN=? AND MatKhau=?";
//        List<NhanVien> list = getdata(sql, tenDN, MatKhau);
//        if (list.size() == 0) {
//            return -1;
//        }
//        return 1;
//
//
//    }
//    public int checkquyen(int maquyen) {
//        String sql = "SELECT * FROM NhanVien WHERE MaQuyen=?";
//        List<NhanVien> list = getdata(sql, String.valueOf(maquyen));
//        if (list.size() == 0) {
//            return -1;
//        }
//        return 1;
//
//
//    }
    @SuppressLint("Range")
    public int LayQuyenNV(int manv){
        int maquyen = 0;
        String query = "SELECT * FROM "+"NhanVien"+" WHERE "+"MaNV"+" = "+manv;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndex("MaQuyen"));

            cursor.moveToNext();
        }
        return maquyen;
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
    public int KiemTraDangNhap(String tenDN,String MatKhau){
      int manv=0;
      Cursor c=db.rawQuery("SELECT * FROM NhanVien WHERE tenDN=? AND MatKhau=? ",new String[]{tenDN,MatKhau});
        if (c!=null&& c.getCount()>0){
            c.moveToFirst();
            do {
                manv=c.getInt(0);
            }while (c.moveToNext());
        }
        return  manv;
    }
    public int laymaNV(){
        int manv=0;
        Cursor c=db.rawQuery("SELECT * FROM NhanVien",null);
        if (c!=null&& c.getCount()>0){
            c.moveToFirst();
            do {
                manv=c.getInt(0);
            }while (c.moveToNext());
        }
        return  manv;
    }

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
