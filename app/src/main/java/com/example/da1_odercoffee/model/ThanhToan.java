package com.example.da1_odercoffee.model;

public class ThanhToan {
    private String TenMon;
    private int soLuong,giaTien;
    private  byte[] hinhAnh;

    public ThanhToan(String tenMon, int soLuong, int giaTien, byte[] hinhAnh) {
        TenMon = tenMon;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh;
    }

    public ThanhToan() {
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

}
