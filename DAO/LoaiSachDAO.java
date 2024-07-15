package com.example.duan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan.DATABASE.DBHelper;
import com.example.duan.Model.LoaiSachModel;

import java.util.ArrayList;

public class LoaiSachDAO {
    private DBHelper dbHelper;
     public LoaiSachDAO(Context context){
         dbHelper = new DBHelper(context);
     }
     //lay danh sach loai sach

    public ArrayList<LoaiSachModel> getDSLoaiSach(){
        ArrayList<LoaiSachModel> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LOAISACH",null);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiSachModel(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themLoaiSach(String tenLoai){
         SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai",tenLoai);

        long check = sqLiteDatabase.insert("LOAISACH",null,contentValues);

        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean suaLoaiSach(LoaiSachModel loaiSachModel){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSachModel.getTenloai());

        int check = sqLiteDatabase.update("LOAISACH",contentValues,"maloai = ?",new String[]{String.valueOf((loaiSachModel.getMaloai()))});
        if (check == -1){
            return false;
        }else {
            return true;
        }
    }
    /*
    -1 : không xóa dc do lỗi hệ thống
     0 : không xóa dc (do khóa ngoại)
     1 : xóa thành công
    * */
    public int xoaLoaiSach(int maLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        //kiểm tra sự tồn tại của những cuốn sách trong bảng sách vơi thể loại đang thực hiện xóa

        Cursor cursor = sqLiteDatabase.rawQuery("select * from SACH where maloai = ?",new String[]{String.valueOf(maLoai)});
        if (cursor.getCount()>0){
            return 0;//không xóa dc do rành buộc khóa ngoại
        }else {
            int check = sqLiteDatabase.delete("LOAISACH","maloai = ?",new String[]{String.valueOf(maLoai)});
            if (check == 0){
                return -1;//xóa không dc vì lỗi,không tìm thấy loại sách cần xóa
            }else {
                return 1;
            }
        }
    }
}
