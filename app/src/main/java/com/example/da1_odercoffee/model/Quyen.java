package com.example.da1_odercoffee.model;

public class Quyen {
    private int maQuyen;
    private  String tenQuyen;

    public Quyen(int maQuyen, String tenQuyen) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
    }

    public Quyen() {
    }

    public Quyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public int getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }
}
