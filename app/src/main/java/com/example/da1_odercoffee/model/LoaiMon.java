package com.example.da1_odercoffee.model;

public class LoaiMon {
    private int MaLoai;
    private String TenLoai;
    private  byte[] HinhAnh;

    public LoaiMon(int maLoai, String tenLoai, byte[] hinhAnh) {
        MaLoai = maLoai;
        TenLoai = tenLoai;
        HinhAnh = hinhAnh;
    }

    public LoaiMon(String tenLoai, byte[] hinhAnh) {
        TenLoai = tenLoai;
        HinhAnh = hinhAnh;
    }

    public LoaiMon() {
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
