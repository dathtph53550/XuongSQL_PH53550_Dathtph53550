package com.example.xuongsql;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.xuongsql.DAO.NhanVienDAO;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //anh xa
        EditText edtBietDanh;
        EditText txtHoTen,txtPass,txtPassAgain;
        Button btnDangNhap,btnTroVe;
        txtHoTen = findViewById(R.id.txtHoTen);
        txtPass = findViewById(R.id.txtPass);
        txtPassAgain = findViewById(R.id.txtPassAgain);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnTroVe = findViewById(R.id.btnTroVe);
        edtBietDanh = findViewById(R.id.txtBietDanh);

        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtHoTen.getText().toString();
                String pass = txtPass.getText().toString();
                String re_pass = txtPassAgain.getText().toString();
                String bietDanh = edtBietDanh.getText().toString();

                if(user.trim().isEmpty()){
                    Toast.makeText(Register.this, "Chưa nhập Username", Toast.LENGTH_SHORT).show();
                }else if(pass.trim().isEmpty() || re_pass.trim().isEmpty()){
                    Toast.makeText(Register.this, "Chưa nhập Pass và Re-Pass", Toast.LENGTH_SHORT).show();
                }
                else if(!pass.equals(re_pass)){
                    Toast.makeText(Register.this, "Mật khẩu không trùng kớp !!", Toast.LENGTH_SHORT).show();
                }
                else if(bietDanh.trim().isEmpty()){
                    Toast.makeText(Register.this, "Vui Lòng Nhập Đầy Đủ Họ Tên !!", Toast.LENGTH_SHORT).show();
                }
                else{
                        boolean check = nhanVienDAO.register(user,pass,bietDanh);
                        if(check){
                            Toast.makeText(Register.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));
                        }
                        else{
                            Toast.makeText(Register.this, user + "   " +pass + "  "+ re_pass, Toast.LENGTH_SHORT).show();
                            Toast.makeText(Register.this, "Đăng Ký chưa thành công ", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }
}