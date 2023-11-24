package com.example.da1_odercoffee;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;


import com.example.da1_odercoffee.fragment.BanFragment;
import com.example.da1_odercoffee.fragment.HomeFragmnet;
import com.example.da1_odercoffee.fragment.NhanVienFragment;
import com.example.da1_odercoffee.fragment.ThongKeFragmnet;
import com.example.da1_odercoffee.fragment.ThongTinFragmnet;
import com.example.da1_odercoffee.model.NhanVien;
import com.example.da1_odercoffee.model.Quyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Home_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    BottomNavigationView bottomNavigationView;
    int maquyen ;
    BanFragment banFragmnet;
    HomeFragmnet homeFragmnet;
    ThongKeFragmnet thongKeFragmnet;
    NhanVienFragment nhanVienFragment;

    ThongTinFragmnet thongTinFragmnet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        setSupportActionBar(toolbar); //tạo toolbar

        //khai baos  cacs Fragmnet
        homeFragmnet = new HomeFragmnet();
        banFragmnet = new BanFragment();
        thongKeFragmnet = new ThongKeFragmnet();
        nhanVienFragment = new NhanVienFragment();
        thongTinFragmnet = new ThongTinFragmnet();


// lan dau vao home
        getSupportFragmentManager().beginTransaction().add(R.id.contentView, homeFragmnet).commit();
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Trang Chủ</font>"));
//
        SharedPreferences  sharedPreferences = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        maquyen=sharedPreferences.getInt("maquyen", 0);
//
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.bot_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentView, homeFragmnet).commit();
                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Trang Chủ</font>"));
//                    getSupportActionBar().hide();

                    return  true;
                } else if (id == R.id.bot_table) {
                    if ( maquyen==2) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentView, banFragmnet).commit();
                        getSupportActionBar().show();
                        getSupportActionBar().setTitle("Bàn");
                    } else {
                        Toast.makeText(getApplicationContext(), "Quản Lý không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    }
                    return  true;
                } else if (id == R.id.bot_staff) {
                    if ( maquyen==1) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentView, nhanVienFragment).commit();
                        getSupportActionBar().setTitle("Quản Lý Nhân Viên");
                        getSupportActionBar().show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Nhân Viên không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    }
                    return  true;
                } else if (id == R.id.bot_thongke) {
                    if ( maquyen==1) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentView, thongKeFragmnet).commit();
                        getSupportActionBar().setTitle("Thống Kê");
                        getSupportActionBar().show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Nhân Viên không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    }
                    return  true;
                } else if (id == R.id.bot_information) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentView, thongTinFragmnet).commit();
                    getSupportActionBar().setTitle("Thông Tin Cá Nhân");
                    getSupportActionBar().show();;
                }
                return true;
            }
        });

    }

    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            super.onBackPressed();
        }

    }
}