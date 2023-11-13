package com.example.da1_odercoffee.model;

public class NhanVien {
    private int MaNV;
    private String HoTenNV;
    private String TenDN;
    private String MatKhau;
    private String SoDT;
    private int MaQuyen;

    public NhanVien(int maNV, String hoTenNV, String tenDN, String matKhau, String soDT, int maQuyen) {
        MaNV = maNV;
        HoTenNV = hoTenNV;
        TenDN = tenDN;
        MatKhau = matKhau;
        SoDT = soDT;
        MaQuyen = maQuyen;
    }

    public NhanVien(String hoTenNV, String tenDN, String matKhau, String soDT, int maQuyen) {
        HoTenNV = hoTenNV;
        TenDN = tenDN;
        MatKhau = matKhau;
        SoDT = soDT;
        MaQuyen = maQuyen;
    }

    public NhanVien() {
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public String getHoTenNV() {
        return HoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        HoTenNV = hoTenNV;
    }

    public String getTenDN() {
        return TenDN;
    }

    public void setTenDN(String tenDN) {
        TenDN = tenDN;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public int getMaQuyen() {
        return MaQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        MaQuyen = maQuyen;
    }
}
