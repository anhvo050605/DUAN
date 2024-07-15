package com.example.duan.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DATABASE.DBHelper;

public class NguoiDungDAO   {
    private DBHelper dbHelper;

    public NguoiDungDAO(Context context){
        dbHelper = new DBHelper(context);
    }
    //kiểm tra thông tin đăng nhập
    //nếu có giá trị(user + pass đúng) ==> true
    //ngược lại trả về false
    public boolean KiemTra(String username,String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from NGUOIDUNG where tendangnhap = ? and matkhau = ?",new String[]{username,password});
//        if (cursor.getCount() > 0)
//            return true;
//        else return false;
       return cursor.getCount() > 0;
    }

}
