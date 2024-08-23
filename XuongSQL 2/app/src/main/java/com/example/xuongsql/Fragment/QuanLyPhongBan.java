package com.example.xuongsql.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.example.xuongsql.Adapter.RecyclerPhongBan;
import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.PhongBan;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class QuanLyPhongBan extends Fragment {

    RecyclerView rycPhongBan;
    Button btnThemPhongBan;
    ArrayList<PhongBan> list;
    RecyclerPhongBan adapter;
    PhongBanDAO phongBanDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quanlyphongban,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rycPhongBan = view.findViewById(R.id.rycPhongBan);
        btnThemPhongBan = view.findViewById(R.id.btnThemPhongBan);
        phongBanDAO = new PhongBanDAO(getContext());
        list = phongBanDAO.getList();
        adapter = new RecyclerPhongBan(getContext(),list,phongBanDAO);
        rycPhongBan.setAdapter(adapter);

        btnThemPhongBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhongBan pb = new PhongBan();
                DialogThemBietOn(pb);
            }
        });
    }

    public void DialogThemBietOn(PhongBan pb){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_add_phongban,null);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.show();

        //anh xa
        EditText edtTenPhongBan = v.findViewById(R.id.edtTenPhongBan);
        Button btnThem = v.findViewById(R.id.btnThem);
        Button btnCancle = v.findViewById(R.id.btnCancle);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTenPhongBan.getText().toString();

               if (edtTenPhongBan.length() < 1){
                    Toast.makeText(getContext(), "Vui lòng nhập nội dung > 1 kí tự !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                pb.setTenPhongBan(ten);
                int res = phongBanDAO.AddRow(pb);
                if(res > 0){
                    list.add(pb); // Thêm mục mới vào danh sách
                    rycPhongBan.post(new Runnable() {
                        @Override
                        public void run() {
                            list.clear();
                            list.addAll(phongBanDAO.getList());
                            adapter.notifyDataSetChanged();
                            adapter.notifyItemInserted(list.size() - 1); // Cập nhật mục mới vào RecyclerView
                            rycPhongBan.scrollToPosition(list.size() - 1); // Cuộn đến mục mới
                            Log.d("zzzzz", "Item được thêm ở vị trí : " + (list.size() - 1));
                        }
                    });
                    Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();

            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
