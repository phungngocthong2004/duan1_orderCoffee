package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.ChiTietHoaDonDao;
import com.example.da1_odercoffee.Dao.HoaDonDao;
import com.example.da1_odercoffee.fragment.MonFragmnet;
import com.example.da1_odercoffee.model.ChiTietHoaDon;
import com.example.da1_odercoffee.model.Mon;
import com.google.android.material.textfield.TextInputLayout;

public class SoluongMonActivity extends AppCompatActivity {
    TextInputLayout TXTL_soluong_SoLuong;
    Button BTN_soluong_DongY;
    int maban, mamon;
    HoaDonDao hoaDonDao;
   ChiTietHoaDonDao chiTietHoaDonDAO;
   MonFragmnet monFragmnet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soluong);
        TXTL_soluong_SoLuong=findViewById(R.id.txtl_Soluong_SoLuong);
        BTN_soluong_DongY=findViewById(R.id.btn_Soluong_DongY);
        hoaDonDao = new HoaDonDao(this);
        chiTietHoaDonDAO = new ChiTietHoaDonDao(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        mamon = intent.getIntExtra("mamon",0);

        BTN_soluong_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateSoluong()){
                    return;
                }

                int mahoadon =  hoaDonDao.LayMaDonTheoMaBan(maban,"false");
                boolean ktra = chiTietHoaDonDAO.KiemTraMonTonTai(mahoadon,mamon);
                if(ktra){
                    //update số lượng món đã chọn
                    int sluongcu = chiTietHoaDonDAO.LaySLMonTheoMaDon(mahoadon,mamon);
                    int sluongmoi = Integer.parseInt(TXTL_soluong_SoLuong.getEditText().getText().toString());
                    int tongsl = sluongcu + sluongmoi;

                    ChiTietHoaDon chiTietDonDatDTO = new ChiTietHoaDon();
                    chiTietDonDatDTO.setMaMon(mamon);
                    chiTietDonDatDTO.setMaHoaDon(mahoadon);
                    chiTietDonDatDTO.setSoLuong(tongsl);

                    boolean ktracapnhat = chiTietHoaDonDAO.CapNhatSL(chiTietDonDatDTO);
                    if(ktracapnhat){
                        Toast.makeText(getApplicationContext(),"Cập Nhật Thành Công",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Cập Nhật Thất Bại",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //thêm số lượng món nếu chưa chọn món này
                    int sluong = Integer.parseInt(TXTL_soluong_SoLuong.getEditText().getText().toString());
                    ChiTietHoaDon chiTiethoadon = new ChiTietHoaDon();
                    chiTiethoadon.setMaMon(mamon);
                    chiTiethoadon.setMaHoaDon(mahoadon);
                    chiTiethoadon.setSoLuong(sluong);

                    long ktracapnhat = chiTietHoaDonDAO.ThemChiTietDonDat(chiTiethoadon);
                    if(ktracapnhat>0){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private boolean validateSoluong(){
        String val = TXTL_soluong_SoLuong.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_soluong_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_soluong_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        }else {
            TXTL_soluong_SoLuong.setError(null);
            TXTL_soluong_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}