package com.example.xuongsql.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public class RecyclerNhanVien extends RecyclerView.Adapter<RecyclerNhanVien.NhanVienHolder> {
    Context context;
    ArrayList<NhanVien> list;
    NhanVienDAO nhanVienDAO;

    public RecyclerNhanVien(Context context, ArrayList<NhanVien> list, NhanVienDAO nhanVienDAO) {
        this.context = context;
        this.list = list;
        this.nhanVienDAO = nhanVienDAO;
    }

    @NonNull
    @Override
    public NhanVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NhanVienHolder(((Activity)context).getLayoutInflater().inflate(R.layout.rcy_nhanvien,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienHolder holder, int position) {
        NhanVien nv = list.get(position);
        holder.tvMa.setText("Mã NV: " + nv.getMaNV());
        holder.tvTen.setText( nv.getHoDem() + " " + nv.getTen());
        holder.tvMaPhongBan.setText("Mã Phòng: "+ nv.getMaPhong());
        holder.tvLuong.setText("Lương: " + nv.getLuong() + " VND");
        holder.tvDiaChi.setText(nv.getDiaChi());
        nhanVienDAO = new NhanVienDAO(context);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = nhanVienDAO.deleteNhanSu(nv);
                if(check){
                    Toast.makeText(context, "Xoá thành công !!", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(nhanVienDAO.getList());
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, "Xoá thất bại !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanVien nv = list.get(position);
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
                    list.addAll(nhanVienDAO.getList());
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

    class NhanVienHolder extends RecyclerView.ViewHolder {
        TextView tvMa,tvTen,tvMaPhongBan,tvLuong,tvDiaChi;
        ImageView btnUpdate,btndelete;
        public NhanVienHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMa);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvMaPhongBan = itemView.findViewById(R.id.tvMaPhongBan);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btndelete = itemView.findViewById(R.id.btnDelete);
            tvLuong = itemView.findViewById(R.id.tvLuong);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
        }
    }
}
