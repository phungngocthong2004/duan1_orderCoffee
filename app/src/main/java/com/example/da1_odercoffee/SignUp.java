package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.Dao.QuyenDao;
import com.example.da1_odercoffee.model.NhanVien;
import com.example.da1_odercoffee.model.Quyen;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextInputLayout txtSignUpTenDN, txtSignUpHoTen, txtSignUpDT, txtSignUpMK, txtSignUpLaiMK;
    RadioGroup rgQuyen;
    RadioButton rdoQuanLy, rdoNhanVien;
    TextView tvSignIn;
    Button btnSignUp;
    NhanVienDao nhanVienDao;
    String tenDN, hoTen, dienThoai, pass, rePass;
    int quyen,maquyen;

    QuyenDao quyenDao;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI(); // Ánh xạ
        nhanVienDao = new NhanVienDao(this);

        quyenDao=new QuyenDao(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !validate()) {
                    return;
                }

                NhanVien nhanVien = new NhanVien();

                nhanVien.setHoTenNV(hoTen);
                nhanVien.setTenDN(tenDN);
                nhanVien.setMatKhau(pass);
                nhanVien.setSoDT(dienThoai);

                if(rgQuyen.getCheckedRadioButtonId() == R.id.rdo_QuanLy){
                    quyenDao.ThemQuyen(1,"Quản lý");
                    nhanVien.setMaQuyen(1);

                }else if(rgQuyen.getCheckedRadioButtonId() == R.id.rdo_NhanVien) {
                    quyenDao.ThemQuyen(2,"Nhân Viên");
                    nhanVien.setMaQuyen(2);
                }

                long nv=nhanVienDao.ThemNhanVien(nhanVien);
                if (nv>0){
                    Toast.makeText(SignUp.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUp.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initUI(){
        txtSignUpTenDN = findViewById(R.id.txt_signUp_tenDN);
        txtSignUpHoTen = findViewById(R.id.txt_signUp_HoTen);
        txtSignUpDT = findViewById(R.id.txt_signUp_DienThoai);
        txtSignUpMK = findViewById(R.id.txt_signUp_matKhau);
        txtSignUpLaiMK = findViewById(R.id.txt_signUp_NhapLai);
        rgQuyen = findViewById(R.id.rg_Quyen);
        rdoQuanLy = findViewById(R.id.rdo_QuanLy);
        rdoNhanVien = findViewById(R.id.rdo_NhanVien);
        tvSignIn = findViewById(R.id.tv_SignIn);
        btnSignUp = findViewById(R.id.btn_SignUp);
    }

    // Validate các trường dữ liệu
    public boolean validate(){
        String checkspaces = "\\A\\w{1,50}\\z";

        tenDN = txtSignUpTenDN.getEditText().getText().toString().trim();
        hoTen = txtSignUpHoTen.getEditText().getText().toString().trim();
        dienThoai = txtSignUpDT.getEditText().getText().toString().trim();
        pass = txtSignUpMK.getEditText().getText().toString().trim();
        rePass = txtSignUpLaiMK.getEditText().getText().toString().trim();
        if (tenDN.isEmpty() || hoTen.isEmpty() || dienThoai.isEmpty() || pass.isEmpty() || rePass.isEmpty()){
            txtSignUpTenDN.setError("Không được để trống!");
            txtSignUpHoTen.setError("Không được để trống!");
            txtSignUpDT.setError("Không được để trống!");
            txtSignUpMK.setError("Không được để trống!");
            txtSignUpLaiMK.setError("Không được để trống!");
            return false;
        } else {
            if (!tenDN.matches(checkspaces)){
                txtSignUpTenDN.setError("Tên đăng nhập không chứa khoảng trắng");
                return false;
            } else if (tenDN.length() > 50) {
                txtSignUpTenDN.setError("Tên đăng nhập không quá 50 ký tự");
                return false;
            } else {
                txtSignUpTenDN.setError(null);
                txtSignUpTenDN.setErrorEnabled(false);
            }
            txtSignUpHoTen.setError(null);
            txtSignUpHoTen.setErrorEnabled(false);
            if (dienThoai.length() != 10){
                txtSignUpDT.setError("Số điện thoại không hợp lệ");
                return false;
            } else {
                txtSignUpDT.setError(null);
                txtSignUpDT.setErrorEnabled(false);
            }
            if (!PASSWORD_PATTERN.matcher(pass).matches()){
                txtSignUpMK.setError("Mật khẩu ít nhất 6 ký tự!");
                return false;
            } else {
                txtSignUpMK.setError(null);
                txtSignUpMK.setErrorEnabled(false);
            }
            if (!pass.equals(rePass)) {
                Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                txtSignUpLaiMK.setError(null);
                txtSignUpLaiMK.setErrorEnabled(false);
            }
        }
        if (rgQuyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Vui lòng chọn quyền!", Toast.LENGTH_SHORT).show();
            return false;
        } else if(rgQuyen.getCheckedRadioButtonId() == R.id.rdo_QuanLy){
            quyen = 1;
        } else if(rgQuyen.getCheckedRadioButtonId() == R.id.rdo_NhanVien){
            quyen = 2;
        }
        return true;
    }
}