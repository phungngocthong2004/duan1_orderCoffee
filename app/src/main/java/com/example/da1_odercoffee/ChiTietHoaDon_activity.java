package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da1_odercoffee.Dao.BanDao;
import com.example.da1_odercoffee.Dao.ChiTietHoaDonDao;
import com.example.da1_odercoffee.Dao.HoaDonDao;
import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.Dao.ThanhToanDao;
import com.example.da1_odercoffee.adapter.ThanhToanAdapter;
import com.example.da1_odercoffee.model.ChiTietHoaDon;
import com.example.da1_odercoffee.model.NhanVien;
import com.example.da1_odercoffee.model.ThanhToan;

import java.util.List;

public class ChiTietHoaDon_activity extends AppCompatActivity {
    ImageView img_cthd_backbtn;
    TextView txt_cthd_MaDon,txt_cthd_NgayDat,txt_cthd_TenBan
            ,txt_cthd_TenNV,txt_cthd_TongTien;
    GridView gvDetailStatistic;
    int madon, manv, maban,tongtien;
    String ngaydat;
   NhanVienDao nhanVienDAO;
   BanDao banDAO;
    List<ThanhToan> thanhToanDTOList;
    ThanhToanDao thanhToanDAO;
    ThanhToanAdapter thanhToanAdapter;
    HoaDonDao donDao;
ChiTietHoaDonDao chiTietHoaDonDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        img_cthd_backbtn = (ImageView)findViewById(R.id.img_cthd_backbtn);
        txt_cthd_MaDon = (TextView)findViewById(R.id.txt_cthd_MaDon);
        txt_cthd_NgayDat = (TextView)findViewById(R.id.txt_cthd_NgayDat);
        txt_cthd_TenBan = (TextView)findViewById(R.id.txt_cthd_TenBan);
        txt_cthd_TenNV = (TextView)findViewById(R.id.txt_cthd_TenNV);
        txt_cthd_TongTien = (TextView)findViewById(R.id.txt_cthd_TongTien);
        gvDetailStatistic = (GridView)findViewById(R.id.gvcthd);

        nhanVienDAO = new NhanVienDao(this);
        banDAO = new BanDao(this);
        thanhToanDAO = new ThanhToanDao(this);
        donDao=new HoaDonDao(this);
        HienThiDSCTDD();

        // laays Tu Hóa Dơn
        Intent intent = getIntent();
        madon = intent.getIntExtra("madon",0);
        manv = intent.getIntExtra("manv",0);
        maban = intent.getIntExtra("maban",0);
        ngaydat = intent.getStringExtra("ngaydat");
        tongtien = intent.getIntExtra("tongtien",0);
        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (madon !=0){
            txt_cthd_MaDon.setText("Mã đơn: "+madon);
            txt_cthd_NgayDat.setText(ngaydat);
            txt_cthd_TongTien.setText(tongtien+" VNĐ");

            NhanVien nhanVien = nhanVienDAO.LayNVTheoMa(manv);
            txt_cthd_TenNV.setText(nhanVienDAO.getTenNhanVien(manv));
            txt_cthd_TenBan.setText(banDAO.LayTenBanTheoMa(maban));

            HienThiDSCTDD();
        }


        img_cthd_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }
    private void HienThiDSCTDD(){

        thanhToanDTOList = thanhToanDAO.LayDSMonTheoMaDon(madon);
        thanhToanAdapter = new ThanhToanAdapter(this,thanhToanDTOList);
        gvDetailStatistic.setAdapter(thanhToanAdapter);
        thanhToanAdapter.notifyDataSetChanged();
    }
}