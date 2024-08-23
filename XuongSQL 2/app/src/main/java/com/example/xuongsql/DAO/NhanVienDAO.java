package com.example.xuongsql.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class NhanVienDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;



    public NhanVienDAO (Context context){
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public NhanVien checkLogin(String Manv, String matkhau){
        NhanVien nv = null;
        String[] dieukien = new String[]{ Manv, matkhau };
        Cursor c = db.rawQuery("SELECT * FROM NhanVien WHERE MaNV = ? AND MatKhau = ?", dieukien);
        if(c != null && c.getCount() == 1){
            // có thông tin
            c.moveToFirst();
            // thứ tự các cột trong bảng 0: cột mã tt, 1 là cột hoten, 2 là cột mat khau ...
            nv = new NhanVien( c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getInt(4),
                    c.getInt(5));
            c.close();
        }
        return nv;
    }

    public String getNhanVienTen(String manv) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT HoDem,Ten FROM NhanVien WHERE MaNV = ?", new String[]{String.valueOf(manv)});
        String hoTen = "";
        if (cursor.moveToFirst()) {
            String hoDem = cursor.getString(0);
            String ten = cursor.getString(1);
            hoTen = hoDem + " " + ten;  // Tạo tên đầy đủ
        }
        cursor.close();
        return hoTen;
    }

    public boolean register(String username,String pass, String bietDanh){
        ContentValues v = new ContentValues();
        v.put("MaNV",username);
        v.put("MatKhau",pass);
        int lastSpaceIndex = bietDanh.lastIndexOf(" ");
        String hoDem = bietDanh.substring(0, lastSpaceIndex);
        String ten = bietDanh.substring(lastSpaceIndex + 1);
        v.put("HoDem",hoDem);
        v.put("Ten",ten);
        long check = db.insert("NhanVien",null,v);
        return check != -1;
    }

    public ArrayList<NhanVien> getList(){
        ArrayList<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM NhanVien", null);
        if(c.getCount() != 0 && c != null){
            c.moveToFirst();
            do {
                String ma = c.getString(0);
                String ho = c.getString(1);
                String ten = c.getString(2);
                String matkhau = c.getString(3);
                int quyen = c.getInt(4);
                int maPhong = c.getInt(5);
                int luong = c.getInt(6);
                String diaChi = c.getString(7);
                NhanVien nv = new NhanVien(ma,ho,ten,matkhau,quyen,maPhong,luong,diaChi);
                list.add(nv);

            }while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public boolean capNhatMatKhau(String ma, String oldPass, String newPass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NhanVien WHERE MaNV = ? AND MatKhau = ?", new String[]{ma, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("MatKhau", newPass);
            long check = sqLiteDatabase.update("NhanVien", contentValues, "MaNV = ?", new String[]{ma});
            if (check == -1) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public int addRow(NhanVien nv){
        ContentValues v = new ContentValues();
        v.put("MaNV", nv.getMaNV());
        v.put("HoDem",nv.getHoDem());
        v.put("Ten",nv.getTen());
        v.put("MatKhau", nv.getMatKhau());
        v.put("MaPhong",nv.getMaPhong());
        v.put("Luong",nv.getLuong());
        v.put("DiaChi",nv.getDiaChi());
        Log.d("zzzzzz", "addRow: " + nv.getMaNV() + " " + nv.getHoDem() + " " + nv.getTen()+ " " + nv.getMaPhong() );
        int kq = (int)db.insert("NhanVien",null,v);
        return kq;
    }

    public boolean deleteNhanSu(NhanVien nv){
        long check = db.delete("NhanVien","MaNV = ?", new String[]{nv.getMaNV()});
        return check != 0;
    }

    public boolean updateNhanSu(NhanVien nv){
        ContentValues v = new ContentValues();
        String ten = nv.getTen();
        Log.d("zzzzz", "updateNhanSu: " + nv.getTen());
        int lastSpaceIndex = ten.lastIndexOf(" ");
        String hoDem = ten.substring(0, lastSpaceIndex);
        String tenThat = ten.substring(lastSpaceIndex + 1);
        v.put("HoDem",hoDem);
        v.put("Ten",tenThat);
        v.put("MaPhong",nv.getMaPhong());
        v.put("Luong",nv.getLuong());
        v.put("DiaChi",nv.getDiaChi());
        long kq = db.update("NhanVien",v,"MaNV = ?", new String[]{nv.getMaNV()});
        return kq != 0;
    }

//    public boolean updateNhanSu(NhanVien nv) {
//        ContentValues v = new ContentValues();
//        String ten = nv.getTen();
//        int lastSpaceIndex = ten.lastIndexOf(" ");
//
//        String hoDem;
//        String tenThat;
//
//        // Kiểm tra nếu có dấu cách trong tên
//        if (lastSpaceIndex != -1) {
//            hoDem = ten.substring(0, lastSpaceIndex);
//            tenThat = ten.substring(lastSpaceIndex + 1);
//        } else {
//            // Nếu không có dấu cách, họ đệm trống và tên là toàn bộ chuỗi
//            hoDem = "";
//            tenThat = ten;
//        }
//
//        v.put("HoDem", hoDem);
//        v.put("Ten", tenThat);
//        v.put("MaPhong", nv.getMaPhong());
//
//        long kq = db.update("NhanVien", v, "MaNV = ?", new String[]{nv.getMaNV()});
//        return kq != 0;
//    }

//    public boolean updateNhanSu(NhanVien nv) {
//        ContentValues v = new ContentValues();
//
//        // Lấy họ đệm và tên hiện tại từ cơ sở dữ liệu
//        String[] columns = {"HoDem", "Ten"};
//        Cursor cursor = db.query("NhanVien", columns, "MaNV = ?", new String[]{nv.getMaNV()}, null, null, null);
//
//        String hoDem = "";
//        String tenHienTai = "";
//
//        if (cursor != null && cursor.moveToFirst()) {
//            hoDem = cursor.getString(cursor.getColumnIndex("HoDem"));
//            tenHienTai = cursor.getString(cursor.getColumnIndex("Ten"));
//            cursor.close();
//        }
//
//        // Nối thêm phần tên mới vào cuối tên hiện tại
//        String tenMoi = nv.getTen();
//        String tenThat = tenHienTai + " " + tenMoi;
//
//        v.put("HoDem", hoDem);  // Giữ lại họ đệm cũ
//        v.put("Ten", tenThat);   // Cập nhật tên đã nối
//        v.put("MaPhong", nv.getMaPhong());
//
//        long kq = db.update("NhanVien", v, "MaNV = ?", new String[]{nv.getMaNV()});
//        return kq != 0;
//    }

    public ArrayList<NhanVien> sortLuong(){
        ArrayList<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM NHANVIEN ORDER BY Luong DESC",null);
        if(c.getCount() != 0 && c != null){
            c.moveToFirst();
            do {
                String ma = c.getString(0);
                String ho = c.getString(1);
                String ten = c.getString(2);
                String matkhau = c.getString(3);
                int quyen = c.getInt(4);
                int maPhong = c.getInt(5);
                int luong = c.getInt(6);
                String diaChi = c.getString(7);
                NhanVien nv = new NhanVien(ma,ho,ten,matkhau,quyen,maPhong,luong,diaChi);
                list.add(nv);
            }while (c.moveToNext());
            c.close();
        }
        return list;
    }

//    public int getQuyen(String maNV) {
//        int quyen = -1; // Giá trị mặc định nếu không tìm thấy
//        Cursor cursor = null;
//
//        try {
//            // Truy vấn để lấy giá trị Quyen dựa trên MaNV
//            String query = "SELECT Quyen FROM NhanVien WHERE MaNV = ?";
//            cursor = db.rawQuery(query, new String[]{maNV});
//
//            if (cursor != null && cursor.moveToFirst()) {
//                quyen = cursor.getInt(cursor.getInt(0));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            db.close();
//        }
//
//        return quyen;
//    }

    public int getQuyenNV(String ma) {
        int quyen = 1;
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT Quyen FROM NhanVien WHERE MaNV = ?", new String[]{ma});
            if (c != null && c.moveToFirst()) {
                quyen = c.getInt(0);
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return quyen;
    }

    public ArrayList<NhanVien> getListThongTin(String ma){
        ArrayList<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT MaNV,HoDem,Ten,Luong,DiaChi,MaPhong FROM NhanVien WHERE MaNV = ?", new String[]{ma});
        if(c.getCount() != 0 && c != null){
            c.moveToFirst();
            do {
                String maNV = c.getString(0);
                String hoDem = c.getString(1);
                String ten = c.getString(2);
                int luong = c.getInt(3);
                String diaChi = c.getString(4);
                int maphong = c.getInt(5);
                NhanVien nv = new NhanVien(maNV,hoDem,ten,luong,diaChi,maphong);
                list.add(nv);
            }while (c.moveToNext());
        }
        return list;
    }

    public ArrayList<NhanVien> searchNhanVien(String find) {
        ArrayList<NhanVien> nhanVienList = new ArrayList<>();

        // Chuẩn bị câu lệnh SQL
        String query = "SELECT * FROM NhanVien WHERE MaNV LIKE ? OR Luong LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + find + "%"});

        // Lấy dữ liệu từ cursor
        if (cursor.moveToFirst()) {
            do {
                NhanVien nv = new NhanVien();
                nv.setMaNV(cursor.getString(0));
                nv.setHoDem(cursor.getString(1));
                nv.setTen(cursor.getString(2));
                nv.setMatKhau(cursor.getString(3));
                nv.setQuyen(cursor.getInt(4));
                nv.setMaPhong(cursor.getInt(5));
                nv.setLuong(cursor.getInt(6));
                nv.setDiaChi(cursor.getString(7));
                nhanVienList.add(nv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nhanVienList;
    }

    public ArrayList<NhanVien> searchNhanVien2(String find) {
        ArrayList<NhanVien> nhanVienList = new ArrayList<>();

        // Chuẩn bị câu lệnh SQL
        String query = "SELECT * FROM NhanVien WHERE MaNV LIKE ? OR Luong LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + find + "%","%" + find + "%"});

        // Lấy dữ liệu từ cursor
        if (cursor.moveToFirst()) {
            do {
                NhanVien nv = new NhanVien();
                nv.setMaNV(cursor.getString(0));
                nv.setHoDem(cursor.getString(1));
                nv.setTen(cursor.getString(2));
                nv.setMatKhau(cursor.getString(3));
                nv.setQuyen(cursor.getInt(4));
                nv.setMaPhong(cursor.getInt(5));
                nv.setLuong(cursor.getInt(6));
                nv.setDiaChi(cursor.getString(7));
                nhanVienList.add(nv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nhanVienList;
    }
}
