package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.model.NhanVien;
import com.google.android.material.textfield.TextInputLayout;

public class DoiMatKhau extends AppCompatActivity {
    ImageButton imgBack;
    TextInputLayout edMkCu, edMkMoi, edLaiMk;
    Button btnDoi;
    NhanVienDao nhanVienDao;

    int manv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        SharedPreferences pref = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        initUI();
        doiMk();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void initUI() {
        imgBack = findViewById(R.id.imgBtn_back);
        edMkCu = findViewById(R.id.ed_MkCu);
        edMkMoi = findViewById(R.id.ed_MkMoi);
        edLaiMk = findViewById(R.id.ed_LaiMk);
        btnDoi = findViewById(R.id.btn_Doi);
    }


    private void doiMk() {
        nhanVienDao = new NhanVienDao(DoiMatKhau.this);
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                manv = intent.getIntExtra("manv", 0);
                if (validate() > 0) {
                    NhanVien nhanVien = nhanVienDao.getheoId(String.valueOf(manv));
                    nhanVien.setMatKhau(edMkMoi.getEditText().getText().toString().trim());
                    if (nhanVienDao.updateTT(nhanVien) > 0) {
                        Toast.makeText(DoiMatKhau.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edMkCu.getEditText().setText("");
                        edMkMoi.getEditText().setText("");
                        edLaiMk.getEditText().setText("");
                    } else {
                        Toast.makeText(DoiMatKhau.this, "thay đồi Mật khẩu Thất bai", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public int validate() {
        int check = 1;

        String mkcu=edMkCu.getEditText().getText().toString().trim();
        String mkmoi=edMkCu.getEditText().getText().toString().trim();
        String mklại=edMkCu.getEditText().getText().toString().trim();
        if (mkcu.isEmpty() || mkmoi.isEmpty()|| mklại.isEmpty()) {
            Toast.makeText(DoiMatKhau.this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences pref = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD", "");
            String pass = edMkMoi.getEditText().getText().toString().trim();
            String rePass = edLaiMk.getEditText().getText().toString().trim();
            if (!passOld.equals(mkcu)) {
                Toast.makeText(DoiMatKhau.this, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)) {
                Toast.makeText(DoiMatKhau.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}