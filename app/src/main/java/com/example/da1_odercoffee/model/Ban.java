package com.example.da1_odercoffee.model;

public class Ban {
    private int MaBan;
    private String Tenban;

    private boolean duocchon;

    public Ban(int maBan, String tenban, boolean duocchon) {
        MaBan = maBan;
        Tenban = tenban;
        this.duocchon = duocchon;
    }

    public Ban(int maBan, String tenban) {
        MaBan = maBan;
        Tenban = tenban;
    }

    public Ban() {
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public String getTenban() {
        return Tenban;
    }

    public void setTenban(String tenban) {
        Tenban = tenban;
    }

    public boolean isDuocchon() {
        return duocchon;
    }

    public void setDuocchon(boolean duocchon) {
        this.duocchon = duocchon;
    }
}
