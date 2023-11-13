package com.example.da1_odercoffee.model;

public class ChiTietHoaDon {
    private int MaHoaDon;
    private int MaMon;
    private int SoLuong;

    public ChiTietHoaDon(int maHoaDon, int maMon, int soLuong) {
        MaHoaDon = maHoaDon;
        MaMon = maMon;
        SoLuong = soLuong;
    }

    public ChiTietHoaDon() {
    }

    public int getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        MaHoaDon = maHoaDon;
    }

    public int getMaMon() {
        return MaMon;
    }

    public void setMaMon(int maMon) {
        MaMon = maMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}
