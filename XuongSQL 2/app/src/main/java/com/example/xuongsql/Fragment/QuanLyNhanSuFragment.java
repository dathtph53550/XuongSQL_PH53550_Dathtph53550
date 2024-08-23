package com.example.xuongsql.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.Adapter.RecyclerNhanVien;
import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DAO.PhongBanDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.DTO.PhongBan;
import com.example.xuongsql.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class QuanLyNhanSuFragment extends Fragment {
    RecyclerView rycNhanSu;
    ArrayList<NhanVien> list;
    NhanVienDAO nhanVienDAO;
    FloatingActionButton btnAdd;
    RecyclerNhanVien adapter;
    EditText edtFind;
    ImageView imgFind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quanlynhansu,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rycNhanSu = view.findViewById(R.id.rycNhanSu);
        btnAdd = view.findViewById(R.id.btnAdd);
        edtFind = view.findViewById(R.id.edtFind);
        nhanVienDAO = new NhanVienDAO(getContext());
        imgFind = view.findViewById(R.id.imgFind);

        list = nhanVienDAO.getList();
        adapter = new RecyclerNhanVien(getContext(),list,nhanVienDAO);
        rycNhanSu.setAdapter(adapter);
//        list = nhanVienDAO.getList();
//        adapter = new RecyclerNhanVien(getContext(),list,nhanVienDAO);
//        rycNhanSu.setAdapter(adapter);

        imgFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.addAll(nhanVienDAO.searchNhanVien2(edtFind.getText().toString()));
                adapter.notifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private  void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themnhansu, null);
        Spinner spnPhongBan = view.findViewById(R.id.spnPhongBan);
        EditText edtMa = view.findViewById(R.id.edtMa);
        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtMatKhau = view.findViewById(R.id.edtMatKhau);
        EditText edtLuong = view.findViewById(R.id.edtLuong);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
//        getDataThanhVien(spnThanhVien);
//        getDataSach(spnSach);
        getDataThanhVien(spnPhongBan);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ma = edtMa.getText().toString();
                String ten = edtTen.getText().toString();
                String matKhau = edtMatKhau.getText().toString();
                String luong = edtLuong.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                int lastSpaceIndex = ten.lastIndexOf(" ");
                String hoDem = ten.substring(0, lastSpaceIndex);
                String tenThat = ten.substring(lastSpaceIndex + 1);
                if (ma.isEmpty() || ten.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng đầy đủ !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnPhongBan.getSelectedItem();
                int pban = (int) hsTV.get("MaPhong");

                NhanVien nv = new NhanVien();
                nv.setMaNV(ma);
                nv.setHoDem(hoDem);
                nv.setTen(tenThat);
                nv.setMaPhong(pban);
                nv.setMatKhau(matKhau);
                nv.setLuong(Integer.parseInt(luong));
                nv.setDiaChi(diaChi);
                int check = nhanVienDAO.addRow(nv);
                if(check > 0){
                    list.clear();
                    list.addAll(nhanVienDAO.getList());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Thêm thành công !!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Thêm thất bại !!", Toast.LENGTH_SHORT).show();
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
        PhongBanDAO phongBanDAO = new PhongBanDAO(getContext());
        ArrayList<PhongBan> list = phongBanDAO.getList();

        ArrayList<HashMap<String, Object>> listPB = new ArrayList<>();
        for (PhongBan tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaPhong", tv.getMaPhong());
            hs.put("TenPhong", tv.getTenPhongBan());
            listPB.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listPB, android.R.layout.simple_list_item_1, new String[]{"TenPhong"}, new int[]{android.R.id.text1});
        spnPhongBan.setAdapter(simpleAdapter);
    }

}
