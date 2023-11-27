package com.example.da1_odercoffee.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhepper extends SQLiteOpenHelper {
    static  final  String Dbname="AppOrderCoffeeTrongNhaHang";
    static  final  int version=3;

    public  Dbhepper (Context context){
        super(context,Dbname,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTableQuyen="CREATE TABLE Quyen (" +
                "    MaQuyen  INTEGER PRIMARY KEY ," +
                "    TenQuyen TEXT    NOT NULL)";


        String CreateTableNhanVien="CREATE TABLE NhanVien (" +
                "    MaNV    INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    hoTenNv TEXT    NOT NULL," +
                "    tenDN   TEXT    NOT NULL," +
                "    MatKhau TEXT    NOT NULL," +
                "    SoDT    TEXT    NOT NULL," +
                "    MaQuyen INTEGER REFERENCES Quyen (MaQuyen))";

     String CreatetableLoaiMon="CREATE TABLE LoaiMon (" +
             "    MaLoai  INTEGER PRIMARY KEY AUTOINCREMENT," +
             "    TenLoai TEXT    NOT NULL," +
             "    HinhAnh BLOB    NOT NULL )";

        String CreatetableMon="CREATE TABLE Mon (" +
                "    MaMon     INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    TenMon    TEXT    NOT NULL," +
                "    GiaTien   INTEGER NOT NULL," +
                "    TrangThai TEXT    NOT NULL," +
                "    HinhAnh   BLOB    NOT NULL," +
                "    Maloai    INTEGER REFERENCES LoaiMon (MaLoai) )";

        String CreatetableBan="CREATE TABLE Ban (" +
                "    MaBan        INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    TenBan       TEXT    NOT NULL," +
                "    TinhtrangBan  TEXT   NOT NULL   )";

       String CreatetableHoaDon="CREATE TABLE HoaDon (" +
               "    MaHoaDon  INTEGER PRIMARY KEY AUTOINCREMENT," +
               "    MaNV      INTEGER REFERENCES NhanVien (MaNV)," +
               "    MaBan     INTEGER REFERENCES Ban (MaBan)," +
               "    NgayDat   TEXT    NOT NULL," +
               "    TinhTrang TEXT    NOT NULL," +
               "    TongTien  INTEGER NOT NULL)";

        String CreatetableChiTietHoaDon="CREATE TABLE ChiTietHoaDon (" +
                "    MaHoaDon INTEGER," +
                "    MaMon    INTEGER," +
                "    SoLuong  INTEGER NOT NULL," +
                "    PRIMARY KEY(MaHoaDon,MaMon))";

//        sqLiteDatabase.execSQL("INSERT INTO Quyen VALUES(MaQuyen,TenQuyen),(1,'Quản Lý'),(2,'Nhân Viên')");

//        String insert_quyen="INSERT INTO Quyen values(1, 'Quản Lý'),(2,' Nhân Viên')";
//       sqLiteDatabase.execSQL(insert_quyen);
        sqLiteDatabase.execSQL(CreateTableQuyen);
        sqLiteDatabase.execSQL(CreateTableNhanVien);
        sqLiteDatabase.execSQL(CreatetableLoaiMon);
        sqLiteDatabase.execSQL(CreatetableMon);
        sqLiteDatabase.execSQL(CreatetableBan);
        sqLiteDatabase.execSQL(CreatetableHoaDon);
        sqLiteDatabase.execSQL(CreatetableChiTietHoaDon);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1!=i){
            String DroptableQuyen="drop table if exists Quyen";
            sqLiteDatabase.execSQL(DroptableQuyen);
            String DroptableNhanVien="drop table if exists NhanVien";
            sqLiteDatabase.execSQL(DroptableNhanVien);
            String DroptableLoaiMon="drop table if exists LoaiMon";
            sqLiteDatabase.execSQL(DroptableLoaiMon);
            String DroptableMon="drop table if exists Mon";
            sqLiteDatabase.execSQL(DroptableMon);
            String DroptableBan="drop table if exists Ban";
            sqLiteDatabase.execSQL(DroptableBan);
            String DroptableHoaDon="drop table if exists HoaDon";
            sqLiteDatabase.execSQL(DroptableHoaDon);
            String DroptableChiTietHoaDon="drop table if exists ChiTietHoaDon";
            sqLiteDatabase.execSQL(DroptableChiTietHoaDon);
            onCreate(sqLiteDatabase);
        }
    }
}
