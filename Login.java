package com.example.duan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan.DAO.NguoiDungDAO;

public class Login extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        nguoiDungDAO = new NguoiDungDAO(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                boolean check = nguoiDungDAO.KiemTra(user,pass);
                if (check){
                    startActivity(new Intent(Login.this, MainActivity.class));
                }else{
                    Toast.makeText(Login.this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}