package com.example.duan.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "QUANLYTHUVIEN", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng loại sách
        String tLoaiSach = "create table LOAISACH(" +
                "maloai integer primary key autoincrement," +
                "tenloai text)";
        db.execSQL(tLoaiSach);
        // data mẫu
        db.execSQL("insert into LOAISACH values" +
                "(1,'thiếu nhi')," +
                "(2,'tình cảm')," +
                "(3,'ma')," +
                "(4,'trinh sát')," +
                "(5,'truyện cười')," +
                "(6,'kinh dị')");
//        ===================================================================
        String tSach = "create table SACH(" +
                "masach integer primary key autoincrement, " +
                "tensach text," +
                "tacgia text," +
                "giaban integer," +
                "maloai integer references LOAISACH(maloai))";
        db.execSQL(tSach);
        // data mẫu
        db.execSQL("insert into SACH values" +
                "(1,'Tấm cám','Nguyễn Nhật Ánh',15000,1)," +
                "(2,'Trạng Quỳnh','Kim Đồng',5000,1)");
//        ===================================================================
        /*
        * role:
        * 1 - người dùng
        * 2 - thủ thư
        * 3 - admin
        * */
        String tNguoiDung = "create table NGUOIDUNG(" +
                "mand integer primary key autoincrement," +
                "tennd text," +
                "sdt text," +
                "diachi text ," +
                "tendangnhap text," +
                "matkhau text," +
                "role integer)";
        db.execSQL(tNguoiDung);
        // data mẫu
        db.execSQL("insert into NGUOIDUNG values" +
                "(1,'Nguyễn Văn A','0123456789','Q1 TP.HCM','aaa','123',1)," +
                "(2,'Huỳnh Văn B','0369852147','Q8 TP.HCM','bbb','456',2)," +
                "(3,'Trần Thị C','0147852369','Q12 TP.HCM','ccc','789',3) ");
//        ===================================================================
        String tPhieuMuon = "create table PHIEUMUON(" +
                "mapm integer primary key autoincrement, " +
                "ngaymuon text," +
                "ngaytra text," +
                "mand integer references NGUOIDUNG(mand)) ";
        db.execSQL(tPhieuMuon);
        //data mẫu
        db.execSQL("insert into PHIEUMUON values " +
                "(1,'05/06/2024','07/07/2024',1)");
//        ===================================================================
        String tCTPM = "create table CTPM(" +
                "mactpm integer primary key autoincrement, " +
                "mapm integer references PHIEUMUON(mapm)," +
                "masach integer references SACH(masach)," +
                "soluong integer)";
        db.execSQL(tCTPM);
        // data mẫu
        db.execSQL("insert into CTPM values(1,1,1,5),(2,1,2,3)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("drop table if exists LOAISACH");
            db.execSQL("drop table if exists SACH");
            db.execSQL("drop table if exists NGUOIDUNG");
            db.execSQL("drop table if exists PHIEUMUON");
            db.execSQL("drop table if exists CTPM");
            onCreate(db);
        }
    }
}
