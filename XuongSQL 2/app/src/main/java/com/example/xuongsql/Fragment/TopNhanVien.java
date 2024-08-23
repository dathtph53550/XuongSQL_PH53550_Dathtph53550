package com.example.xuongsql.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.Adapter.AdapterTop10;
import com.example.xuongsql.Adapter.RecyclerNhanVien;
import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class TopNhanVien extends Fragment {
    RecyclerView ryc;
    ArrayList<NhanVien> list;
    NhanVienDAO nhanVienDAO;
    AdapterTop10 adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topnhanvien,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ryc = view.findViewById(R.id.ryc);
        nhanVienDAO = new NhanVienDAO(getContext());
        list = nhanVienDAO.sortLuong();
        adapter = new AdapterTop10(getContext(),list,nhanVienDAO);
        ryc.setAdapter(adapter);
    }
}
