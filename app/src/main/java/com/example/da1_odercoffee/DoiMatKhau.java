package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.model.NhanVien;

public class DoiMatKhau extends AppCompatActivity {
    ImageButton imgBack;
    EditText edMkCu, edMkMoi, edLaiMk;
    Button btnDoi;
    NhanVienDao nhanVienDao;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        initUI();
        doiMk();
        pref = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
    }
    private void initUI(){
        imgBack = findViewById(R.id.imgBtn_back);
        edMkCu = findViewById(R.id.ed_MkCu);
        edMkMoi = findViewById(R.id.ed_MkMoi);
        edLaiMk = findViewById(R.id.ed_LaiMk);
        btnDoi = findViewById(R.id.btn_Doi);
    }
    private void doiMk(){
        nhanVienDao = new NhanVienDao(DoiMatKhau.this);
        btnDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = pref.getString("USERNAME", "");
                if (validate() > 0){
                    NhanVien nhanVien = nhanVienDao.getID(user);
                    nhanVien.setMatKhau(edMkMoi.getText().toString());
                    if (nhanVienDao.updatePass(nhanVien) > 0){
                        Toast.makeText(DoiMatKhau.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edMkCu.setText("");
                        edMkMoi.setText("");
                        edLaiMk.setText("");
                    } else {
                        Toast.makeText(DoiMatKhau.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public int validate(){
        int check = 1;
        if (edMkCu.getText().length() == 0 || edMkMoi.getText().length() == 0 || edLaiMk.getText().length() == 0){
            Toast.makeText(DoiMatKhau.this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String passOld = pref.getString("PASSWORD", "");
            String pass = edMkMoi.getText().toString();
            String rePass = edLaiMk.getText().toString();
            if (!passOld.equals(edMkCu.getText().toString())){
                Toast.makeText(DoiMatKhau.this, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(DoiMatKhau.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}