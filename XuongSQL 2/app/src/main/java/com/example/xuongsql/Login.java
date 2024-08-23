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
import com.example.xuongsql.DTO.NhanVien;

public class Login extends AppCompatActivity {
    EditText edMaNV, edMatkhau;
    Button btnLogin;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ///---- ánh xạ view
        edMatkhau = findViewById(R.id.ed_matkhau);
        edMaNV = findViewById(R.id.ed_manv);
        btnLogin = findViewById(R.id.btnLogin);
        Button btnDangKy =  findViewById(R.id.btnRegister);
        // khởi tạo các biến
        nhanVienDAO = new NhanVienDAO(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matt = edMaNV.getText().toString();
                String matkhau = edMatkhau.getText().toString();
                // Kiểm tra hợp lệ
                if(matt.length() <3){
                    Toast.makeText(Login.this, "Mã cần nhập ít nhất 3 ky tu", Toast.LENGTH_SHORT).show();
                    return;// thoát khỏi hàm
                }

                if(matt.equals("Admin") && matkhau.equals("123")){
                    Toast.makeText(Login.this, "Đăng nhập thành công !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, ManHinhQuanLy.class));
                }
                // la tương tự...
                // kiểm tra trong CSDL
                NhanVien kqLogin = nhanVienDAO.checkLogin( matt, matkhau);
                if(kqLogin != null){
                    // Đúng thông tin login
                    // lưu đăng nhập nếu có
                    // gọi activity chính

                    // Chuyển đến màn hình quản lý và truyền dữ liệu tên đăng nhập
                    Intent intent = new Intent(Login.this, ManHinhQuanLy.class);
                    intent.putExtra("USERNAME", matt);
                    Toast.makeText(Login.this, "Đăng Nhập Thành Công !!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}