package com.example.da1_odercoffee.model;

import java.io.Serializable;

public class Mon {
    private int MaMon;
    private String TenMon;
    private int GiaTien;
    private String TinhTrang;
    private byte[] HinhAnh;
    private  int MaLoai;

    public Mon(int maMon, String tenMon, int giaTien, String tinhTrang, byte[] hinhAnh, int maLoai) {
        MaMon = maMon;
        TenMon = tenMon;
        GiaTien = giaTien;
        TinhTrang = tinhTrang;
        HinhAnh = hinhAnh;
        MaLoai = maLoai;
    }

    public Mon(String tenMon, int giaTien, String tinhTrang, byte[] hinhAnh, int maLoai) {
        TenMon = tenMon;
        GiaTien = giaTien;
        TinhTrang = tinhTrang;
        HinhAnh = hinhAnh;
        MaLoai = maLoai;
    }

    public Mon() {
    }

    public int getMaMon() {
        return MaMon;
    }

    public void setMaMon(int maMon) {
        MaMon = maMon;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
        GiaTien = giaTien;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }
}
