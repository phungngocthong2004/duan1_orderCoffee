package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.BanDao;
import com.example.da1_odercoffee.Dao.ChiTietHoaDonDao;
import com.example.da1_odercoffee.Dao.HoaDonDao;
import com.example.da1_odercoffee.Dao.ThanhToanDao;
import com.example.da1_odercoffee.adapter.ThanhToanAdapter;
import com.example.da1_odercoffee.fragment.HoaDonFragmnet;
import com.example.da1_odercoffee.model.ThanhToan;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity {
    ImageView IMG_payment_backbtn;
    TextView TXT_ThanhToan_TenBan, TXT_thanhtoanToan_NgayDat, TXT_ThanhToan_TongTien;
    Button BTN_ThanhToan_ThanhToan;
    GridView gvDisplayPayment;
    HoaDonDao hdDAO;
    BanDao banDAO;
    ThanhToanDao thanhToanDAO;
    List<ThanhToan> listThanhToan;
    ThanhToanAdapter thanhToanAdapter;
    int tongtien,maquyen;
    int maban, mahoadon;

    FragmentManager fragmentManager;
    HoaDonFragmnet hoaDonFragmnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        //region thuộc tính view
        gvDisplayPayment= (GridView)findViewById(R.id.gvPayment);
        IMG_payment_backbtn = (ImageView)findViewById(R.id.img_payment_backbtn);
        TXT_ThanhToan_TenBan = (TextView)findViewById(R.id.txt_payment_TenBan);
        TXT_thanhtoanToan_NgayDat = (TextView)findViewById(R.id.txt_payment_NgayDat);
        TXT_ThanhToan_TongTien = (TextView)findViewById(R.id.txt_payment_TongTien);
        BTN_ThanhToan_ThanhToan = (Button)findViewById(R.id.btn_payment_ThanhToan);
        //endregion
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
        //khởi tạo kết nối csdl
        hdDAO = new HoaDonDao(this);
        thanhToanDAO = new ThanhToanDao(this);
        banDAO = new BanDao(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã bàn đc chọn
        Intent intent = getIntent();
        maban = intent.getIntExtra("maban",0);
        String tenban = intent.getStringExtra("tenban");
        String ngaydat = intent.getStringExtra("ngaydat");

        TXT_ThanhToan_TenBan.setText(tenban);
        TXT_thanhtoanToan_NgayDat.setText(ngaydat);

        //ktra mã bàn tồn tại thì hiển thị
        if(maban !=0 ){
            HienThiThanhToan();

            for (int i=0;i<listThanhToan.size();i++){
                int soluong = listThanhToan.get(i).getSoLuong();
                int giatien = listThanhToan.get(i).getGiaTien();

                tongtien += (soluong * giatien);
            }
            TXT_ThanhToan_TongTien.setText(tongtien +" VNĐ");
        }

        BTN_ThanhToan_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maquyen==2){
                    boolean ktraban = banDAO.CapNhatTinhTrangBan(maban, "false");
                    boolean ktradondat = hdDAO.UpdateTThaiDonTheoMaBan(maban, "true");
                    boolean ktratongtien = hdDAO.UpdateTongTienHoaDon(mahoadon, tongtien);
                    if (ktraban && ktradondat && ktratongtien) {
                        HienThiThanhToan();
                        Toast.makeText(ThanhToanActivity.this, "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();
                        tongtien = 0;
                        TXT_ThanhToan_TongTien.setText(tongtien + " VNĐ");
                    } else {

                        Toast.makeText(ThanhToanActivity.this, "Thanh Toán Thất Bại!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ThanhToanActivity.this, "Thanh Toán Là Việc của Nhân Viên", Toast.LENGTH_SHORT).show();
                }

            }
        });

        IMG_payment_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void HienThiThanhToan() {
        mahoadon =  hdDAO.LayMaDonTheoMaBan(maban,"false");
        listThanhToan = thanhToanDAO.LayDSMonTheoMaDon(mahoadon);
        thanhToanAdapter = new ThanhToanAdapter(ThanhToanActivity.this, listThanhToan);
        gvDisplayPayment.setAdapter(thanhToanAdapter);
        thanhToanAdapter.notifyDataSetChanged();
    }


}