package com.example.xuongsql.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.DTO.PhongBan;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class RecyclerPhongBan  extends RecyclerView.Adapter<RecyclerPhongBan.PhongBanHolder> {

    Context context;
    ArrayList<PhongBan> list;
    public final PhongBanDAO phongBanDAO;

    public RecyclerPhongBan(Context context, ArrayList<PhongBan> list, PhongBanDAO phongBanDAO) {
        this.context = context;
        this.list = list;
        this.phongBanDAO = phongBanDAO;
    }

    @NonNull
    @Override
    public PhongBanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_recyler_phongban,parent,false);
        return new PhongBanHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongBanHolder holder, int position) {
        PhongBan pb = list.get(position);
        holder.tvMa_Phong.setText("Mã Phòng : " + String.valueOf(pb.getMaPhong()));
        holder.tvTen_Phong.setText(pb.getTenPhongBan());

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhongBan pb1 = list.get(position);
                DialogSuaPhongBan(pb1);
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh Báo");
                builder.setIcon(R.drawable.warningg);
                builder.setMessage("Bạn có chắc chắn muốn xoá công việc này không ?");
                builder.setCancelable(false);

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long check = phongBanDAO.deletePhongBan(pb.getMaPhong());
                        switch ((int) check) {
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(phongBanDAO.getList());
                                notifyDataSetChanged();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa sách này", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showNhanVien(pb);
                return false;
            }
        });
    }

    public void showNhanVien(PhongBan phongBan){
        Log.d("zzzzzzzzz", "showNhanVien: "+ phongBan.getMaPhong());
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = ((Activity)context).getLayoutInflater().inflate(R.layout.dialog_shownhanvien,null);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.show();


        RecyclerView ryc = v.findViewById(R.id.ryc);
//        list = p
        ArrayList<NhanVien> listNhanVien = phongBanDAO.getListNhanVien(phongBan.getMaPhong());
        Log.d("DEBUG", "Số lượng nhân viên: " + listNhanVien.size());
        for (NhanVien nv : listNhanVien) {
            Log.d("DEBUG", "Nhân viên: " + nv.getMaNV() + " - " + nv.getTen());
        }
        AdapterShow adapterShow = new AdapterShow(context,listNhanVien,phongBanDAO);
        ryc.setAdapter(adapterShow);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class PhongBanHolder extends RecyclerView.ViewHolder {

        TextView tvMa_Phong,tvTen_Phong;
        ImageView btnSua,btnXoa;
        public PhongBanHolder(@NonNull View itemView) {
            super(itemView);

            tvMa_Phong = itemView.findViewById(R.id.tvMaPhongBan);
            tvTen_Phong = itemView.findViewById(R.id.tvTenPhongBan);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnDelete);
        }
    }

    public void DialogSuaPhongBan(PhongBan pbupdate){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_update_phongban,null);
        builder.setView(v);
        AlertDialog dialog = builder.create();

        //anh xa
        EditText edtTenPhongBan = v.findViewById(R.id.edtTenPhongBan);
        Button btnSua = v.findViewById(R.id.btnSua);
        Button btnCancle = v.findViewById(R.id.btnCancle);

        //set len
        edtTenPhongBan.setText(pbupdate.getTenPhongBan());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtTenPhongBan.getText().toString();
                PhongBan pb = new PhongBan(pbupdate.getMaPhong(),ten);

                boolean check = phongBanDAO.updateRow(pb);
                if(check){
                    if(ten.length() < 1){
                        Toast.makeText(context, "Tên Phòng ít nhất 1 kí tự", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.clear();
                            list.addAll(phongBanDAO.getList());
                            notifyDataSetChanged();
                        }
                    });
                    dialog.dismiss();
                }
                else
                    Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
