package com.example.da1_odercoffee.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.da1_odercoffee.Dao.BanDao;
import com.example.da1_odercoffee.Dao.HoaDonDao;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.ThanhToanActivity;
import com.example.da1_odercoffee.fragment.LoaiMonFragmnet;
import com.example.da1_odercoffee.model.Ban;
import com.example.da1_odercoffee.model.HoaDon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BanAdapter extends BaseAdapter {
    private Context context;
    private List<Ban> banList;
    ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnNut;
    TextView txtTenBanAn;

    //    ViewHolder viewHolder;
    BanDao banDao;
    HoaDonDao hoaDonDao;
    FragmentManager fragmentManager;
    Ban ban;
    public BanAdapter(Context context, List<Ban> banList) {
        this.context = context;
        this.banList = banList;
        banDao = new BanDao(context);
        hoaDonDao = new HoaDonDao(context);
        fragmentManager = ((Home_Activity) context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banList.size();
    }

    @Override
    public Object getItem(int position) {
        return banList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banList.get(position).getMaBan();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        if (convertView != null)
            v = convertView;
        else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_ban, parent, false);
        }
        imgBanAn = v.findViewById(R.id.img_item_Ban);
        imgGoiMon = v.findViewById(R.id.img_item_GoiMon);
        imgThanhToan = v.findViewById(R.id.img_item_ThanhToan);
        imgAnNut = v.findViewById(R.id.img_item_AnNut);
        txtTenBanAn = v.findViewById(R.id.txt_item_TenBanAn);

        ban = banList.get(position);


        String kttinhtrang = banDao.LayTinhTrangBanTheoMa(ban.getMaBan());
        //đổi hình theo tình trạng
        if (kttinhtrang.equals("true")) {
            imgBanAn.setImageResource(R.drawable.table_bar);
        } else {
            imgBanAn.setImageResource(R.drawable.table_bar);
        }

        txtTenBanAn.setText(ban.getTenban());

        //sự kiện click
        int maban = banList.get(position).getMaBan();
        String tenban = banList.get(position).getTenban();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat = dateFormat.format(calendar.getTime());


//        imgBanAn.setTag(position);
        imgBanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int vitri= (int) v.getTag();
                    ban.setDuocchon(true);
                    HienThiButton();
                }

        });
//        imgBanAn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    ban.setDuocchon(true);
//                    HienThiButton();
//                }else{
//                    AnButton();
//                }
//            }
//        });
        imgGoiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIHome = ((Home_Activity) context).getIntent();
                int manv = getIHome.getIntExtra("manv", 0);
                String tinhtrang = banDao.LayTinhTrangBanTheoMa(maban);

                // neeu ban không trống
                if (tinhtrang.equals("false")) {
                    //Thêm bảng gọi món và update tình trạng bàn
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaBan(maban);
                    hoaDon.setMaNhanVien(manv);
                    hoaDon.setNgayDat(ngaydat);
                    hoaDon.setTinhTrang("false");
                    hoaDon.setTongTien(0);


                    long ktra = hoaDonDao.ThemHoaDon(hoaDon);
                    banDao.CapNhatTinhTrangBan(maban, "true");
                    if (ktra == 0) {
                        Toast.makeText(context, context.getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                    }
                }
                //chuyển qua trang looai mon
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                LoaiMonFragmnet loaimonFragment = new LoaiMonFragmnet();


                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("maban", maban);
                loaimonFragment.setArguments(bDataCategory);
                transaction.replace(R.id.contentView, loaimonFragment).addToBackStack("hienthibanan");
                transaction.commit();
            }
        });
        imgThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//   /chuyển dữ liệu qua trang thanh toán
                Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                iThanhToan.putExtra("maban", maban);
                iThanhToan.putExtra("tenban", tenban);
                iThanhToan.putExtra("ngaydat", ngaydat);
                context.startActivity(iThanhToan);
            }
        });
        imgAnNut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnButton();
            }
        });

        return v;

    }


    private void HienThiButton() {
        imgGoiMon.setVisibility(View.VISIBLE);
        imgThanhToan.setVisibility(View.VISIBLE);
        imgAnNut.setVisibility(View.VISIBLE);
    }

    private void AnButton() {
        imgGoiMon.setVisibility(View.INVISIBLE);
        imgThanhToan.setVisibility(View.INVISIBLE);
        imgAnNut.setVisibility(View.INVISIBLE);
    }

}


