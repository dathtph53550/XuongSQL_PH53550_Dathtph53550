package com.example.xuongsql.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class AdapterShow extends RecyclerView.Adapter<AdapterShow.ShowHolder> {

    Context context;
    ArrayList<NhanVien> list;
    PhongBanDAO phongBanDAO;

    public AdapterShow(Context context, ArrayList<NhanVien> list, PhongBanDAO phongBanDAO) {
        this.context = context;
        this.list = list;
        this.phongBanDAO = phongBanDAO;
    }

    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowHolder(((Activity)context).getLayoutInflater().inflate(R.layout.rcy_show,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowHolder holder, int position) {
        NhanVien nv = list.get(position);
        holder.tvMa.setText(nv.getMaNV());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ShowHolder extends RecyclerView.ViewHolder {
        TextView tvMa;
        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
        }
    }
}
