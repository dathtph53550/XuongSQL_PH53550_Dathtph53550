package com.example.xuongsql.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.DTO.PhongBan;
import com.example.xuongsql.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterThongTin extends RecyclerView.Adapter<AdapterThongTin.ThongTinHolder> {

    Context context;
    ArrayList<NhanVien> list;
    NhanVienDAO nhanVienDAO;

    public AdapterThongTin(Context context, ArrayList<NhanVien> list, NhanVienDAO nhanVienDAO) {
        this.context = context;
        this.list = list;
        this.nhanVienDAO = nhanVienDAO;
    }

    @NonNull
    @Override
    public ThongTinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThongTinHolder(((Activity)context).getLayoutInflater().inflate(R.layout.layout_thongtin,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThongTinHolder holder, int position) {
        NhanVien nv = list.get(position);
        holder.tvMa.setText(nv.getMaNV());
        holder.tvHoDem.setText(nv.getHoDem());
        holder.tvTen.setText(nv.getTen());
        holder.tvLuong.setText(nv.getLuong() + " VND");
        holder.tvDiaChi.setText(nv.getDiaChi());
        holder.tvMaPhongBan.setText(String.valueOf(nv.getMaPhong()));
        nhanVienDAO = new NhanVienDAO(context);
        holder.btnThayDoiThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(nv);
            }
        });
    }


    private  void showDialog(NhanVien nhanVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_updatenhansu, null);
        Spinner spnPhongBan = view.findViewById(R.id.spnPhongBan);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtLuong = view.findViewById(R.id.edtLuong);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
//        getDataThanhVien(spnThanhVien);
//        getDataSach(spnSach);
        getDataThanhVien(spnPhongBan);
        builder.setView(view);


        edtTen.setText(nhanVien.getHoDem() + " " + nhanVien.getTen());
        edtLuong.setText(String.valueOf(nhanVien.getLuong()));
        edtDiaChi.setText(nhanVien.getDiaChi());
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ten = edtTen.getText().toString();
                String luong = edtLuong.getText().toString();
                String diaChi = edtDiaChi.getText().toString();

                int lastSpaceIndex = ten.lastIndexOf(" ");
                String hoDem = ten.substring(0, lastSpaceIndex);
                String tenThat = ten.substring(lastSpaceIndex + 1);
                if ( ten.isEmpty()){
                    Toast.makeText(context, "Vui lòng đầy đủ !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnPhongBan.getSelectedItem();
                int pban = (int) hsTV.get("MaPhong");

                NhanVien nv = new NhanVien(nhanVien.getMaNV(), hoDem,ten,nhanVien.getMatKhau(),nhanVien.getQuyen(),pban,Integer.parseInt(luong),diaChi);
                boolean check = nhanVienDAO.updateNhanSu(nv);
                if(check){
                    list.clear();
                    list.addAll(nhanVienDAO.getListThongTin(nhanVien.getMaNV()));
                    notifyDataSetChanged();
                    Toast.makeText(context, "Update thành công !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Update thất bại !!", Toast.LENGTH_SHORT).show();
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

    public void getDataThanhVien(Spinner spnPhongBan){
        PhongBanDAO phongBanDAO = new PhongBanDAO(context);
        ArrayList<PhongBan> list = phongBanDAO.getList();

        ArrayList<HashMap<String, Object>> listPB = new ArrayList<>();
        for (PhongBan tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaPhong", tv.getMaPhong());
            hs.put("TenPhong", tv.getTenPhongBan());
            listPB.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listPB, android.R.layout.simple_list_item_1, new String[]{"TenPhong"}, new int[]{android.R.id.text1});
        spnPhongBan.setAdapter(simpleAdapter);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ThongTinHolder extends RecyclerView.ViewHolder {
        TextView tvMa,tvHoDem,tvTen,tvLuong,tvDiaChi,tvMaPhongBan;
        Button btnThayDoiThongTin;

        public ThongTinHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvHoDem = itemView.findViewById(R.id.tvHoDem);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvLuong = itemView.findViewById(R.id.tvLuong);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvMaPhongBan = itemView.findViewById(R.id.tvMaPhongBan);
            btnThayDoiThongTin = itemView.findViewById(R.id.btnThayDoiThongTin);
        }
    }
}
