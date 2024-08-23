package com.example.xuongsql.DTO;

public class NhanVien {
    private String MaNV;
    private String HoDem;
    private String Ten;
    private String MatKhau;
    private int Quyen;
    private int MaPhong;
    private int luong;
    private String diaChi;

    public NhanVien(String maNV, String hoDem, String ten, String matKhau, int quyen, int maPhong, int luong, String diaChi) {
        MaNV = maNV;
        HoDem = hoDem;
        Ten = ten;
        MatKhau = matKhau;
        Quyen = quyen;
        MaPhong = maPhong;
        this.luong = luong;
        this.diaChi = diaChi;
    }

    public NhanVien(String maNV, String hoDem, String ten, String matKhau, int quyen, int maPhong) {
        MaNV = maNV;
        HoDem = hoDem;
        Ten = ten;
        MatKhau = matKhau;
        Quyen = quyen;
        MaPhong = maPhong;
    }

    public NhanVien(String maNV, String hoDem, String ten,int luong,String diaChi,int maPhong) {
        MaNV = maNV;
        HoDem = hoDem;
        Ten = ten;
        MaPhong = maPhong;
        this.luong = luong;
        this.diaChi = diaChi;
    }



    public NhanVien() {
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getHoDem() {
        return HoDem;
    }

    public void setHoDem(String hoDem) {
        HoDem = hoDem;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public int getQuyen() {
        return Quyen;
    }

    public void setQuyen(int quyen) {
        Quyen = quyen;
    }

    public int getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(int maPhong) {
        MaPhong = maPhong;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }


}
