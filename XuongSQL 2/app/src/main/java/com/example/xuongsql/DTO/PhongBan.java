package com.example.xuongsql.DTO;

public class PhongBan {
    private int maPhong;
    private String tenPhongBan;

    public PhongBan(int maPhong, String tenPhongBan) {
        this.maPhong = maPhong;
        this.tenPhongBan = tenPhongBan;
    }

    public PhongBan() {
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }
}
