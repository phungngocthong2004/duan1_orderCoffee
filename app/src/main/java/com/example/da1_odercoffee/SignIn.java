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
int maquyen;
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

                if (!validateUserName()|!validatePassWord()){
                    return  ;
                }
                tenDN = txtSignInTenDN.getEditText().getText().toString().trim();
                passDN = txtSignInMK.getEditText().getText().toString().trim();
                int ktra = nhanVienDao.KiemTraDangNhap(tenDN,passDN);
                int maquyen = nhanVienDao.LayQuyenNV(ktra);

                if(ktra != 0) {
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("maquyen", maquyen);
                    editor.commit();
                    rememberUser(tenDN,passDN,chkLuuDN.isChecked());
                    Intent intent = new Intent(SignIn.this, Home_Activity.class);
                    intent.putExtra("tendn",txtSignInTenDN.getEditText().getText().toString());
                    intent.putExtra("matkhau",txtSignInMK.getEditText().getText().toString());
                    intent.putExtra("manv",ktra);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignIn.this, "Đăng Nhập Thất Bai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maquyen==1){
                    Intent intent = new Intent(SignIn.this, SignUp.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SignIn.this, "Bạn Không có Quyền Truy  Đăng ký", Toast.LENGTH_SHORT).show();
                }

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

    private boolean validateUserName(){
        String val = txtSignInTenDN.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtSignInTenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtSignInTenDN.setError(null);
            txtSignInTenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txtSignInMK.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txtSignInMK.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            txtSignInMK.setError(null);
            txtSignInMK.setErrorEnabled(false);
            return true;
        }
    }
}