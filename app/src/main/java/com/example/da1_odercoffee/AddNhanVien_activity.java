package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.Dao.QuyenDao;
import com.example.da1_odercoffee.model.NhanVien;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddNhanVien_activity extends AppCompatActivity  implements View.OnClickListener {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");

    ImageView IMG_addstaff_back;
    TextView TXT_addstaff_title;
    TextInputLayout TXTL_addstaff_HoVaTen, TXTL_addstaff_TenDN, TXTL_addstaff_SDT, TXTL_addstaff_MatKhau,TXTL_addstaff_NhapLaiMatKhau;
    RadioGroup rg_addstaff_Quyen;
    RadioButton rd_addstaff_QuanLy,rd_addstaff_NhanVien;

    Button BTN_addstaff_ThemNV;
    NhanVienDao nhanVienDAO;
    String hoTen,tenDN,sDT,matKhau;
    int manv = 0,quyen = 0;
    long ktra = 0;
    QuyenDao quyenDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);
        TXT_addstaff_title = (TextView)findViewById(R.id.txt_addstaff_title);
        IMG_addstaff_back = (ImageView)findViewById(R.id.img_addstaff_back);
        TXTL_addstaff_HoVaTen = (TextInputLayout)findViewById(R.id.txtl_addstaff_HoVaTen);
        TXTL_addstaff_TenDN = (TextInputLayout)findViewById(R.id.txtl_addstaff_TendangNhap);
        TXTL_addstaff_SDT = (TextInputLayout)findViewById(R.id.txtl_addstaff_DienThoai);
        TXTL_addstaff_MatKhau = (TextInputLayout)findViewById(R.id.txtl_addstaff_MatKhau);
        TXTL_addstaff_NhapLaiMatKhau = (TextInputLayout)findViewById(R.id.txtl_addstaff_NhapLaiMatKhau);
        rg_addstaff_Quyen = (RadioGroup)findViewById(R.id.rg_addstaff_Quyen);
        rd_addstaff_QuanLy = (RadioButton)findViewById(R.id.rd_addstaff_QuanLy);
        rd_addstaff_NhanVien = (RadioButton)findViewById(R.id.rd_addstaff_NhanVien);
        BTN_addstaff_ThemNV = (Button)findViewById(R.id.btn_addstaff_ThemNV);


        nhanVienDAO = new NhanVienDao(this);
       quyenDao =new QuyenDao(this);
        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        manv = getIntent().getIntExtra("manv",0);

        //lấy manv từ display staff
        if(manv != 0){
            TXT_addstaff_title.setText("Sửa nhân viên");
            NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(manv);

            //Hiển thị thông tin từ csdl
            TXTL_addstaff_HoVaTen.getEditText().setText(nhanVien.getHoTenNV());
            TXTL_addstaff_TenDN.getEditText().setText(nhanVien.getTenDN());
            TXTL_addstaff_SDT.getEditText().setText(nhanVien.getSoDT());
            TXTL_addstaff_MatKhau.getEditText().setText(nhanVien.getMatKhau());



            if(nhanVien.getMaQuyen() == 1){
                rd_addstaff_QuanLy.setChecked(true);
            }else {
                rd_addstaff_NhanVien.setChecked(true);
            }

            //Hiển thị ngày sinh từ csdl
//            String date = nhanVienDTO.getNGAYSINH();
//            String[] items = date.split("/");
//            int day = Integer.parseInt(items[0]);
//            int month = Integer.parseInt(items[1]) - 1;
//            int year = Integer.parseInt(items[2]);
//            DT_addstaff_NgaySinh.updateDate(year,month,day);
//            BTN_addstaff_ThemNV.setText("Sửa nhân viên");
        }
        //endregion

        BTN_addstaff_ThemNV.setOnClickListener(this);
        IMG_addstaff_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String chucnang;
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        int  quyen = pref.getInt("MaQuyen",0);
        if (id==R.id.btn_addstaff_ThemNV) {

            if ( !validateFullName() | !validatePassWord() |
                    !validatePermission() | !validatePhone() | !validateUserName()|!vadidatenhaplaipass()) {
                return;
            }
            //Lấy dữ liệu từ view
            hoTen = TXTL_addstaff_HoVaTen.getEditText().getText().toString();
            tenDN = TXTL_addstaff_TenDN.getEditText().getText().toString();
            sDT = TXTL_addstaff_SDT.getEditText().getText().toString();
            matKhau = TXTL_addstaff_MatKhau.getEditText().getText().toString();


            if (rg_addstaff_Quyen.getCheckedRadioButtonId() == R.id.rd_addstaff_QuanLy) {
                quyen = 1;
            } else if (rg_addstaff_Quyen.getCheckedRadioButtonId() == R.id.rd_addstaff_NhanVien) {
                quyen = 2;
            }



            //truyền dữ liệu vào obj nhanvien
            NhanVien nhanVienDTO = new NhanVien();
            nhanVienDTO.setHoTenNV(hoTen);
            nhanVienDTO.setTenDN(tenDN);
            nhanVienDTO.setSoDT(sDT);
            nhanVienDTO.setMatKhau(matKhau);
            nhanVienDTO.setMaQuyen(quyen);

            if (manv != 0) {
                ktra = nhanVienDAO.SuaNhanVien(nhanVienDTO, manv);
                chucnang = "sua";
            } else {
                ktra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
                chucnang = "themnv";
            }
            //Thêm, sửa nv dựa theo obj nhanvienDTO
            Intent intent = new Intent();
            intent.putExtra("ketquaktra", ktra);
            intent.putExtra("chucnang", chucnang);
            setResult(RESULT_OK, intent);
            finish();

        } else if (id==R.id.img_addstaff_back) {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }


    }
    private boolean validateFullName(){
        String val = TXTL_addstaff_HoVaTen.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addstaff_HoVaTen.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addstaff_HoVaTen.setError(null);
            TXTL_addstaff_HoVaTen.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = TXTL_addstaff_TenDN.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            TXTL_addstaff_TenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            TXTL_addstaff_TenDN.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addstaff_TenDN.setError("Không được cách chữ!");
            return false;
        }
        else {
            TXTL_addstaff_TenDN.setError(null);
            TXTL_addstaff_TenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = TXTL_addstaff_SDT.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            TXTL_addstaff_SDT.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            TXTL_addstaff_SDT.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            TXTL_addstaff_SDT.setError(null);
            TXTL_addstaff_SDT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_addstaff_MatKhau.getEditText().getText().toString().trim();
        String Nhaplaimk = TXTL_addstaff_NhapLaiMatKhau.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addstaff_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if(!val.equals(Nhaplaimk)){
            TXTL_addstaff_NhapLaiMatKhau.setError("Mật Khẩu Không Khớp");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_addstaff_MatKhau.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            TXTL_addstaff_MatKhau.setError(null);
            TXTL_addstaff_MatKhau.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vadidatenhaplaipass() {

        String Nhaplaimk = TXTL_addstaff_NhapLaiMatKhau.getEditText().getText().toString().trim();
        if (Nhaplaimk.isEmpty()) {
            TXTL_addstaff_NhapLaiMatKhau.setError(getResources().getString(R.string.not_empty));
        } else {
            TXTL_addstaff_NhapLaiMatKhau.setError(null);
            TXTL_addstaff_NhapLaiMatKhau.setErrorEnabled(false);
            return true;
        }
        return true;
    }

    private boolean validatePermission(){
        if(rg_addstaff_Quyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn quyền",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }


}