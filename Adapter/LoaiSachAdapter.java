package com.example.duan.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan.DAO.LoaiSachDAO;
import com.example.duan.Model.LoaiSachModel;
import com.example.duan.R;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHoder> {

    private Context context;
    private ArrayList<LoaiSachModel> list;
    private LoaiSachDAO  loaiSachDAO;
    public LoaiSachAdapter(Context context, ArrayList<LoaiSachModel> list, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = loaiSachDAO;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_loaisach,parent,false);
        return new ViewHoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.txtma.setText("ID: "+ list.get(position).getMaloai());
        holder.txtten.setText(list.get(position).getTenloai());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo!");
                builder.setMessage("bạn có chắc chắn muốn xóa thể loại " + list.get(holder.getAdapterPosition()).getTenloai()+" không?");
                builder.setIcon(R.drawable.baseline_cancel_24);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getMaloai());
                        switch (check){
                            case -1:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();

                                break;
                            case 0:
                                Toast.makeText(context, "Bạn cần xóa các cuốn sách trong thể loại này trước", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                loadData();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Không ",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  ViewHoder extends RecyclerView.ViewHolder{
         TextView txtma,txtten;
         ImageView ivEdit,ivDelete;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtma = itemView.findViewById(R.id.txtma);
            txtten = itemView.findViewById(R.id.txtten);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

    private void showDialogUpdate(LoaiSachModel loaiSachModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.dialog_loaisach,null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView txtTieuDe = v.findViewById(R.id.txtTieude);
        EditText edtTenLoai = v.findViewById(R.id.edttenloai);
        Button btnLuu = v.findViewById(R.id.btnLuu);
        Button btnHuy = v.findViewById(R.id.btnHuy);
        txtTieuDe.setText("Cập nhật thông tin");
        btnLuu.setText("Cập nhật");
        edtTenLoai.setText(loaiSachModel.getTenloai());
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTenLoai.getText().toString();
                LoaiSachModel loaiSachUpdate = new LoaiSachModel(loaiSachModel.getMaloai(), tenLoai);
                boolean check = loaiSachDAO.suaLoaiSach(loaiSachUpdate);
                if(check){
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    loadData();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadData(){
        list.clear();
        list = loaiSachDAO.getDSLoaiSach();
        notifyDataSetChanged();
    }
}
