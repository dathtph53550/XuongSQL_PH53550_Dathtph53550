package com.example.xuongsql.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class AdapterTop10 extends RecyclerView.Adapter<AdapterTop10.Top10Holder> {


    Context context;
    ArrayList<NhanVien> list;
    NhanVienDAO nhanVienDAO;

    public AdapterTop10(Context context, ArrayList<NhanVien> list, NhanVienDAO nhanVienDAO) {
        this.context = context;
        this.list = list;
        this.nhanVienDAO = nhanVienDAO;
    }

    @NonNull
    @Override
    public Top10Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Top10Holder(((Activity)context).getLayoutInflater().inflate(R.layout.rcy_top,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Top10Holder holder, int position) {
        NhanVien nv = list.get(position);
        holder.tvMa.setText("Mã NV: " + nv.getMaNV());
        holder.tvTen.setText( nv.getHoDem() + " " + nv.getTen());
        holder.tvMaPhongBan.setText("Mã Phòng: "+ nv.getMaPhong());
        holder.tvLuong.setText("Lương: " + nv.getLuong() + " VND");
        holder.tvDiaChi.setText(nv.getDiaChi());
        nhanVienDAO = new NhanVienDAO(context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Top10Holder extends RecyclerView.ViewHolder {
        TextView tvMa,tvTen,tvMaPhongBan,tvLuong,tvDiaChi;
        public Top10Holder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvMaPhongBan = itemView.findViewById(R.id.tvMaPhongBan);
            tvLuong = itemView.findViewById(R.id.tvLuong);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
        }
    }
}
