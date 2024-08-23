package com.example.xuongsql.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.DTO.PhongBan;
import com.example.xuongsql.DbHelper.MyDbHelper;

import java.util.ArrayList;

public class PhongBanDAO {
    MyDbHelper dbHelper;
    SQLiteDatabase db;

    public PhongBanDAO(Context context){
        dbHelper = new MyDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<PhongBan> getList(){
        ArrayList<PhongBan> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM PhongBan",null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            do{
                int ma_phong = c.getInt(0);
                String ten_phong = c.getString(1);
                PhongBan pb = new PhongBan(ma_phong,ten_phong);
                list.add(pb);
            }while (c.moveToNext());
        }
        return list;
    }

    public int AddRow(PhongBan phongBan){
        ContentValues v = new ContentValues();
        v.put("TenPhong",phongBan.getTenPhongBan());
        int kq = (int) db.insert("PhongBan",null,v);
        return kq;
    }

    public boolean updateRow(PhongBan pb){
        // tạo đối tượng truyền dữ liệu vào bảng
        ContentValues v = new ContentValues();
        v.put("TenPhong",pb.getTenPhongBan());
        String [] dieu_kien = {String.valueOf(pb.getMaPhong())};
        // thực thi lệnh cập nhật
        long kq = db.update("PhongBan", v,"MaPhong = ?", dieu_kien );
        return kq > 0; // nếu update thành công thì kq >0
    }

    public boolean deleteRow (PhongBan pb){
        // tạo đk update
        String [] dieu_kien = { String.valueOf(  pb.getMaPhong()  ) };
        long kq = db.delete("PhongBan", "MaPhong = ?", dieu_kien );
        return kq>0;
    }
    public int deletePhongBan(int maPhongBan){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NhanVien WHERE MaPhong = ?", new String[]{String.valueOf(maPhongBan)});
        if (cursor.getCount() != 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("PhongBan", "MaPhong = ?", new String[]{String.valueOf(maPhongBan)});
        if (check == -1){
            return 0;
        }
        return 1;
    }

    public ArrayList<NhanVien> getListNhanVien(int ma){
        ArrayList<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT MaNV FROM NhanVien WHERE MaPhong = ?",new String[]{String.valueOf(ma)});
        if(c.getCount() != 0 && c != null){
            c.moveToFirst();
            do {
                String maNV = c.getString(0);
                NhanVien nv = new NhanVien();
                nv.setMaNV(maNV);
                list.add(nv);
            }while (c.moveToNext());
            c.close();
        }
        return list;
    }
}
