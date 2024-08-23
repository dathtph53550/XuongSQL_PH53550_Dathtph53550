package com.example.xuongsql.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.Login;
import com.example.xuongsql.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMatKhau extends RecyclerView.Adapter<AdapterMatKhau.MatKhauHolder> {

    Context context;
    ArrayList<NhanVien> list;
    NhanVienDAO nhanVienDAO;

    public AdapterMatKhau(Context context, ArrayList<NhanVien> list, NhanVienDAO nhanVienDAO) {
        this.context = context;
        this.list = list;
        this.nhanVienDAO = nhanVienDAO;
    }

    @NonNull
    @Override
    public MatKhauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MatKhauHolder(((Activity)context).getLayoutInflater().inflate(R.layout.ryc_matkhau,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MatKhauHolder holder, int position) {
        NhanVien nv = list.get(position);
        holder.tvMa.setText("Mã: " + nv.getMaNV());
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(nv);
            }
        });
    }

    private  void showDialog(NhanVien nhanVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);

        TextView tvMa = view.findViewById(R.id.tvMa);
        EditText edtPassOld = view.findViewById(R.id.edtPassOld);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);
//        getDataThanhVien(spnThanhVien);
//        getDataSach(spnSach);
        builder.setView(view);
        tvMa.setText(nhanVien.getMaNV());

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String oldPass = edtPassOld.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();
                if (newPass.equals(reNewPass)) {
//                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
//                    String matt = sharedPreferences.getString("matt", "");
                    NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
                    boolean check = nhanVienDAO.capNhatMatKhau(nhanVien.getMaNV(), oldPass, newPass);
                    if (check) {
                        Toast.makeText(context, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Mật khẩu cũ không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MatKhauHolder extends RecyclerView.ViewHolder {
        TextView tvMa;
        ImageView btnUpdate;
        public MatKhauHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
