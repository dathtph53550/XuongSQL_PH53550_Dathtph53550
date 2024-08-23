package com.example.xuongsql.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xuongsql.Adapter.AdapterThongTin;
import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.DTO.NhanVien;
import com.example.xuongsql.R;

import java.util.ArrayList;

public class FragmentThongTinCaNhan extends Fragment {

    RecyclerView rycThongTin;
    AdapterThongTin adapter;
    ArrayList<NhanVien> list;
    NhanVienDAO nhanVienDAO;
    String ma = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            ma = bundle.getString("ma");
        }
        Log.d("maa", "onCreateView: "+ ma);
        return inflater.inflate(R.layout.fragment_thongtincanhan,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rycThongTin = view.findViewById(R.id.rycThongTin);
        nhanVienDAO = new NhanVienDAO(getContext());
        list = nhanVienDAO.getListThongTin(ma);
        adapter = new AdapterThongTin(getContext(),list,nhanVienDAO);
        rycThongTin.setAdapter(adapter);
    }
}
