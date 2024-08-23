package com.example.xuongsql.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    static String DB_NAME = "QL_TV";
    static int DB_VERSION = 5;

    public MyDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String PhongBan = "CREATE TABLE PhongBan ( " +
                "MaPhong  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenPhong TEXT    UNIQUE NOT NULL )";

        db.execSQL(PhongBan);

        String themPhongBan = "INSERT INTO PhongBan VALUES (1,'HanhChinh')";
        db.execSQL(themPhongBan);

        String NhanVien = "CREATE TABLE NhanVien ( " +
                "MaNV    TEXT    PRIMARY KEY, " +
                "HoDem   TEXT    NOT NULL, " +
                "Ten     TEXT    NOT NULL, " +
                "MatKhau TEXT    NOT NULL, " +
                "Quyen   INTEGER DEFAULT (0), " +
                "MaPhong INTEGER REFERENCES PhongBan (MaPhong) DEFAULT(1)," +
                "Luong INTEGER," +
                "DiaChi TEXT);";
        db.execSQL(NhanVien);

        String themDuLieuNhanVien = "INSERT INTO NhanVien VALUES ('NV001','Hoàng Tiến', 'Đạt', '123',0,1,4000,'28 Phan Văn Trường')";
        db.execSQL(themDuLieuNhanVien);







    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i1>i ){
            // có nâng phiên bản, viết lệnh alter table hoặc drop rồi tạo lại
            db.execSQL("drop table if exists NhanVien");
            db.execSQL("DROP TABLE if exists PhongBan");
            onCreate( db ); // tạo lại bảng
        }
    }
}
