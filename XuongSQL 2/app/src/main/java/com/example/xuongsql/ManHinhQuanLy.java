package com.example.xuongsql;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.xuongsql.DAO.NhanVienDAO;
import com.example.xuongsql.Fragment.DoiMatKhau;
import com.example.xuongsql.Fragment.FragmentThongTinCaNhan;
import com.example.xuongsql.Fragment.HomeFragment;
import com.example.xuongsql.Fragment.QuanLyNhanSuFragment;
import com.example.xuongsql.Fragment.QuanLyPhongBan;
import com.example.xuongsql.Fragment.TopNhanVien;
import com.google.android.material.navigation.NavigationView;

public class ManHinhQuanLy extends AppCompatActivity {
    FragmentManager fm;
    HomeFragment homeFragment;
    QuanLyNhanSuFragment quanLyNhanSuFragment;
    QuanLyPhongBan quanLyPhongBan;
    DoiMatKhau doiMatKhau;
    TopNhanVien topNhanVien;
    FragmentThongTinCaNhan thongTinCaNhan;


    DrawerLayout drawerLayout;
    Toolbar mToolbar;
    NavigationView nav;
    NhanVienDAO nhanVienDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_quan_ly);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        drawerLayout = findViewById(R.id.main);
        mToolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.nav_drawer);
        setSupportActionBar(mToolbar);
        nhanVienDAO = new NhanVienDAO(this);

        View headerView = nav.getHeaderView(0);
        TextView tvTitle = headerView.findViewById(R.id.tv_tenNhanVien);

        String ma = getIntent().getStringExtra("USERNAME");
        Log.d("zzzz", "onCreate: " + getIntent().getStringExtra("USERNAME"));

        Bundle bundle = new Bundle();
        bundle.putString("ma",ma);

        if(ma != null){
            String ten = nhanVienDAO.getNhanVienTen(ma);
            tvTitle.setText(ten);

            int quyen = nhanVienDAO.getQuyenNV(ma);
            Log.d("lay quyen", "onCreate: " + quyen);
            if(quyen == 0){
                Menu menu = nav.getMenu();
                menu.findItem(R.id.mnu_QuanLyNhanSu).setVisible(false);
                menu.findItem(R.id.mTop10).setVisible(false);
            }
        }
        else {
            Menu menu = nav.getMenu();
            menu.findItem(R.id.mDoiMatKhau).setVisible(false);
            menu.findItem(R.id.mnu_ThongTinCaNhan).setVisible(false);
        }


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                mToolbar,
                R.string.chuoi_open,
                R.string.chuoi_close
        );

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener( drawerToggle );

        //------ khởi tạo các biến
        fm = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        quanLyNhanSuFragment = new QuanLyNhanSuFragment();
        quanLyPhongBan = new QuanLyPhongBan();
        doiMatKhau = new DoiMatKhau();
        topNhanVien = new TopNhanVien();
        thongTinCaNhan = new FragmentThongTinCaNhan();

        // hiển thị fragment home
        fm.beginTransaction().add(R.id.frag_container, homeFragment).commit();




        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //doi fragment
                int idMenu = menuItem.getItemId();
                if(idMenu == R.id.mnu_QuanLyNhanSu){
                    fm.beginTransaction().replace(R.id.frag_container,quanLyNhanSuFragment).commit();
                }
                else if(idMenu == R.id.mnu_QuanLyPhongBan){
                    fm.beginTransaction().replace(R.id.frag_container,quanLyPhongBan).commit();
                } else if (idMenu == R.id.mDoiMatKhau) {
                    fm.beginTransaction().replace(R.id.frag_container,doiMatKhau).commit();
                    doiMatKhau.setArguments(bundle);
                }
                else if (idMenu == R.id.mTop10){
                    fm.beginTransaction().replace(R.id.frag_container,topNhanVien).commit();
                } else if (idMenu == R.id.mnu_ThongTinCaNhan) {
                    fm.beginTransaction().replace(R.id.frag_container,thongTinCaNhan).commit();
                    thongTinCaNhan.setArguments(bundle);
                } else if (idMenu == R.id.mDangXuat) {
                    startActivity(new Intent(ManHinhQuanLy.this, Login.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle( menuItem.getTitle());
                return false;
            }
        });






    }
}