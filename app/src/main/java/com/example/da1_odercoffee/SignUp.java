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


                if ( !validateFullName() | !validatePassWord() |
                        !validatePermission() | !validatePhone() | !validateUserName()|!vadidatenhaplaipass()) {
                    return;
                }

                NhanVien nhanVien = new NhanVien();
                nhanVien.setHoTenNV(hoTen);
                nhanVien.setTenDN(tenDN);
                nhanVien.setMatKhau(pass);
                nhanVien.setSoDT(dienThoai);
//                nhanVien.setMaQuyen(quyen);

                if(rgQuyen.getCheckedRadioButtonId() == R.id.rdo_QuanLy){
                    quyenDao.ThemQuyen("Quản lý");
                    nhanVien.setMaQuyen(1);
                }else if(rgQuyen.getCheckedRadioButtonId() == R.id.rdo_NhanVien) {
                    quyenDao.ThemQuyen("Nhân Viên");
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
    private boolean validateFullName(){
        String val = txtSignUpHoTen.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtSignUpHoTen.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtSignUpHoTen.setError(null);
            txtSignUpHoTen.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = txtSignUpTenDN.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            txtSignUpTenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            txtSignUpTenDN.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            txtSignUpTenDN.setError("Không được cách chữ!");
            return false;
        }
        else {
            txtSignUpTenDN.setError(null);
            txtSignUpTenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = txtSignUpDT.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            txtSignUpDT.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            txtSignUpDT.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            txtSignUpDT.setError(null);
            txtSignUpDT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txtSignUpMK.getEditText().getText().toString().trim();
        String Nhaplaimk = txtSignUpLaiMK.getEditText().toString().trim();
        if(val.isEmpty()){
            txtSignUpMK.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!PASSWORD_PATTERN.matcher(val).matches()){
            txtSignUpMK.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            txtSignUpMK.setError(null);
            txtSignUpMK.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vadidatenhaplaipass() {
        String val = txtSignUpMK.getEditText().getText().toString().trim();
        String Nhaplaimk = txtSignUpLaiMK.getEditText().getText().toString().trim();
        if (Nhaplaimk.isEmpty()) {
            txtSignUpLaiMK.setError(getResources().getString(R.string.not_empty));
            return  false;
        }else if(!val.equals(Nhaplaimk)){
            txtSignUpLaiMK.setError("Mật Khẩu Không Khớp");
            return false;
        } else {
            txtSignUpLaiMK.setError(null);
            txtSignUpLaiMK.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePermission(){
        if(rgQuyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn quyền",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }


}