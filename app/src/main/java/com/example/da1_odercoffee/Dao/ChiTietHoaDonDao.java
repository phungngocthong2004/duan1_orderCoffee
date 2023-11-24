package com.example.da1_odercoffee.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_odercoffee.database.Dbhepper;
import com.example.da1_odercoffee.model.ChiTietHoaDon;


public class ChiTietHoaDonDao {
    Dbhepper dbhepper;
    SQLiteDatabase db;
    public ChiTietHoaDonDao(Context context){
       dbhepper=new Dbhepper(context);
        db = dbhepper.getWritableDatabase();
    }

    public boolean KiemTraMonTonTai(int mahoadon, int mamon){
//        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONDAT+ " WHERE " +CreateDatabase.TBL_CHITIETDONDAT_MAMON+
//                " = " +mamon+ " AND " +CreateDatabase.TBL_CHITIETDONDAT_MADONDAT+ " = "+madondat;
        Cursor cursor = db.rawQuery("SELECT * FROM ChiTietHoaDon WHERE MaHoaDon=? AND MaMon=?",new String[]{String.valueOf(mahoadon), String.valueOf(mamon)});
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
//    public int LaySLMonTheoMaDon(int maHoaDon, int mamon){
//        int soluong = 0;
////        String query = "SELECT * FROM " +CreateDatabase.TBL_CHITIETDONDAT+ " WHERE " +CreateDatabase.TBL_CHITIETDONDAT_MAMON+
////                " = " +mamon+ " AND " +CreateDatabase.TBL_CHITIETDONDAT_MADONDAT+ " = "+madondat;
//        Cursor cursor = db.rawQuery("SELECT * FROM ChiTietHoaDon WHERE MaHoaDon=? AND MaMon=?",new String[]{String.valueOf(maHoaDon), String.valueOf(mamon)});
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            soluong = cursor.getInt(0);
//            cursor.moveToNext();
//        }
//        return soluong;
//    }
    public int LaySLMonTheoMaDon(int madondat, int mamon){
        int soluong = 0;
        String query = "SELECT * FROM " +"ChiTietHoaDon"+ " WHERE " +"MaMon"+
                " = " +mamon+ " AND " +"MaHoaDon"+ " = "+madondat;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex("SoLuong"));
            cursor.moveToNext();
        }
        return soluong;
    }
    public boolean CapNhatSL(ChiTietHoaDon chiTietDonDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("SoLuong", chiTietDonDatDTO.getSoLuong());

        long ktra = db.update("ChiTietHoaDon",contentValues,"MaHoaDon"+ " = "
                +chiTietDonDatDTO.getMaHoaDon()+ " AND " +"MaMon"+ " = "
                +chiTietDonDatDTO.getMaMon(),null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }

//    public int CapNhatSL(ChiTietHoaDon chiTietHoaDon){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("SoLuong", chiTietHoaDon.getSoLuong());
//        String[] dk1 =new String[]{String.valueOf(chiTietHoaDon.getMaMon())};
//        String[] dk2 =new String[]{String.valueOf(chiTietHoaDon.getMaHoaDon())};
//       return db.update("ChiTietHoaDon",contentValues,"MaHoaDon" + " = "
//
//                +dk1+ " AND " +"MaMon"+ " = " +dk2,null);

//        return db.update("ChiTietHoaDon",contentValues,"MaHoaDon" + " = "
//
//                +chiTietHoaDon.getMaHoaDon()+ " AND " +"MaMon"+ " = "
//                +chiTietHoaDon.getMaMon(),null);
    }

    public  long ThemChiTietDonDat(ChiTietHoaDon chiTietHoaDon){
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaHoaDon",chiTietHoaDon.getMaHoaDon());
        contentValues.put("MaMon",chiTietHoaDon.getMaMon());
        contentValues.put("SoLuong",chiTietHoaDon.getSoLuong());

      return  db.insert("ChiTietHoaDon",null,contentValues);
    }
    @SuppressLint("Range")
    public int LayMaHoa(int HoaDon){
        int mahoadon = 0;
        String query = "SELECT * FROM "+"ChiTietHoaDon"+" WHERE "+"MaHoaDon"+" = "+HoaDon;
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            mahoadon = cursor.getInt(cursor.getColumnIndex("MaHoaDon"));

            cursor.moveToNext();
        }
        return mahoadon;
    }
}
