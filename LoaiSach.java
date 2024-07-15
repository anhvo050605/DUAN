package com.example.duan;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan.Adapter.LoaiSachAdapter;
import com.example.duan.DAO.LoaiSachDAO;
import com.example.duan.Model.LoaiSachModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LoaiSach extends AppCompatActivity {
    private LoaiSachDAO loaiSachDAO;
    private RecyclerView recyclerViewLoaiSach;
    private ArrayList<LoaiSachModel> list;
    private RelativeLayout relativeLoaiSach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loai_sach);

        recyclerViewLoaiSach = findViewById(R.id.RVLoaiSach);
        FloatingActionButton floatAdd = findViewById(R.id.floatADD);
        relativeLoaiSach = findViewById(R.id.relativeLoaiSach);

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiển thị dialog thêm
                showDialogThem();
            }
        });

        //data
        loaiSachDAO = new LoaiSachDAO(this);


        //adapter
        loadData();

    }
    private void loadData(){
        //adapter
        list = loaiSachDAO.getDSLoaiSach();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerViewLoaiSach.setLayoutManager(gridLayoutManager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this,list,loaiSachDAO);
        recyclerViewLoaiSach.setAdapter(loaiSachAdapter);
    }
    private void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_loaisach,null);
        builder.setView(v);

        AlertDialog alertDialog = builder.create();
        // dòng code loại bỏ viền dialog
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // dòng code này khi người dùng bấm vào ngoài dialog sẽ k thoát ra
//        alertDialog.setCancelable(false);
        alertDialog.show();

        //ánh xạ
        EditText edtTenLoai = v.findViewById(R.id.edttenloai);
        Button btnLuu = v.findViewById(R.id.btnLuu);
        Button btnHuy = v.findViewById(R.id.btnHuy);
        CardView carViewDialog = v.findViewById(R.id.carViewDialog);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTenLoai.getText().toString();

                if (tenLoai.equals("")){
//                    Toast.makeText(LoaiSach.this, "Nhập đầy đủ thông tin! ", Toast.LENGTH_SHORT).show();
                    showNoti(carViewDialog,"Nhập đầy đủ thông tin!");
                    return;
                }
                boolean check = loaiSachDAO.themLoaiSach(tenLoai);

                if(check == true){

                    showNoti(relativeLoaiSach,"Thêm thành công!");
                    alertDialog.dismiss();
                    loadData();
                }else {
//                    Snackbar.make(recyclerViewLoaiSach,"Thêm thất bại!",Snackbar.LENGTH_SHORT).show();
                    showNoti(relativeLoaiSach,"Thêm thất bại!");
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void showNoti(View v,String message){
        Snackbar.make(v ,message,Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}