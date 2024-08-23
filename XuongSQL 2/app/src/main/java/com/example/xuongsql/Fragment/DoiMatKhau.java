package com.example.xuongsql.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.Adapter.AdapterMatKhau;
import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.Login;
import com.example.xuongsql.R;

import java.util.ArrayList;


public class DoiMatKhau extends Fragment {
    String ma = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            ma = bundle.getString("ma");
        }
        Log.d("maaaaaaaa", "onCreateView: "+ ma);
        return inflater.inflate(R.layout.fragment_doimatkhau,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText edtPassOld = view.findViewById(R.id.edtPassOld);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);
        Button btnDoi = view.findViewById(R.id.btnDoi);

        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtPassOld.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();
                if (newPass.equals(reNewPass)) {
//                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
//                    String matt = sharedPreferences.getString("matt", "");
                    NhanVienDAO thuThuDAO = new NhanVienDAO(getContext());
                    boolean check = thuThuDAO.capNhatMatKhau(ma, oldPass, newPass);
                    if (check) {
                        Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getApplication(), Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu cũ không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
