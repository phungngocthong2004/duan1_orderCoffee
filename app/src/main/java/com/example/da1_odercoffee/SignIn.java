package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends AppCompatActivity {
    TextInputLayout txtSignInTenDN, txtSignInMK;
    CheckBox chkLuuDN;
    Button btnSignIn;
    TextView tvSignUp;
    EditText edTaiKhoan, edMatKhau;
    String tenDN, passDN;
    NhanVienDao nhanVienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initUI(); // Ánh xạ

        nhanVienDao = new NhanVienDao(this);
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME","");
        String pass = pref.getString("PASSWORD","");
        Boolean rem = pref.getBoolean("REMEMBER", false);
        edTaiKhoan = txtSignInTenDN.getEditText();
        edMatKhau = txtSignInMK.getEditText();
        edTaiKhoan.setText(user);
        edMatKhau.setText(pass);
        chkLuuDN.setChecked(rem);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initUI() {
        txtSignInTenDN = findViewById(R.id.txt_signIn_tenDN);
        txtSignInMK = findViewById(R.id.txt_signIn_matKhau);
        chkLuuDN = findViewById(R.id.chk_dangNhap);
        btnSignIn = findViewById(R.id.btn_SignIn);
        tvSignUp = findViewById(R.id.tv_SignUp);
    }

    // Validate
    public boolean validate(){
        tenDN = txtSignInTenDN.getEditText().getText().toString().trim();
        passDN = txtSignInMK.getEditText().getText().toString().trim();
        if (tenDN.isEmpty() || passDN.isEmpty()){
            txtSignInTenDN.setError("Không được để trống");
            txtSignInMK.setError("Không được để trống");
            return false;
        } else {
            if (nhanVienDao.checklogin(tenDN, passDN) > 0){
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                rememberUser(tenDN, passDN, chkLuuDN.isChecked());
                Intent intent = new Intent(SignIn.this, Home_Activity.class);
                intent.putExtra("user", tenDN);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
    // Lưu mật khẩu
    public void rememberUser(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status){
            edit.clear(); // xóa tình trạng lưu trước đó
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        edit.commit(); // Lưu lại toàn bộ
    }
}