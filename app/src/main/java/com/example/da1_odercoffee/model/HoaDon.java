package com.example.da1_odercoffee.model;

public class HoaDon {
    private int MaHoaDon,MaNhanVien,MaBan;
    private  String NgayDat;
    private String TinhTrang;
    private int TongTien;

    public HoaDon(int maHoaDon, int maNhanVien, int maBan, String ngayDat, String tinhTrang, int tongTien) {
        MaHoaDon = maHoaDon;
        MaNhanVien = maNhanVien;
        MaBan = maBan;
        NgayDat = ngayDat;
        TinhTrang = tinhTrang;
        TongTien = tongTien;
    }

    public HoaDon(int maNhanVien, int maBan, String ngayDat, String tinhTrang, int tongTien) {
        MaNhanVien = maNhanVien;
        MaBan = maBan;
        NgayDat = ngayDat;
        TinhTrang = tinhTrang;
        TongTien = tongTien;
    }

    public HoaDon() {
    }

    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        MaHoaDon = maHoaDon;
    }

    public int getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        MaNhanVien = maNhanVien;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public String getNgayDat() {
        return NgayDat;
    }

    public void setNgayDat(String ngayDat) {
        NgayDat = ngayDat;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public int getTongTien() {
        return TongTien;
    }

    public void setTongTien(int tongTien) {
        TongTien = tongTien;
    }
}
