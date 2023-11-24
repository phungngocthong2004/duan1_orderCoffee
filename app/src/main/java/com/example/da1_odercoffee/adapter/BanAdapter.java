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
import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.ThanhToanActivity;
import com.example.da1_odercoffee.fragment.LoaiMonFragmnet;
import com.example.da1_odercoffee.model.Ban;
import com.example.da1_odercoffee.model.HoaDon;
import com.example.da1_odercoffee.model.ThanhToan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BanAdapter extends BaseAdapter {
    private Context context;
    private List<Ban> banList;
    private BanDao banDao;
    private HoaDonDao hoaDonDao;
    NhanVienDao vienDao;
    private FragmentManager fragmentManager;
    int manv;

    public BanAdapter(Context context, List<Ban> banList) {
        this.context = context;
        this.banList = banList;
        banDao = new BanDao(context);
        hoaDonDao = new HoaDonDao(context);
        vienDao = new NhanVienDao(context);
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

    static class ViewHolder {
        ImageView imgBanAn;
        ImageView imgGoiMon;
        ImageView imgThanhToan;
        ImageView imgAnNut;
        TextView txtTenBanAn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Ban ban = banList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_ban, parent, false);
            holder.imgBanAn = convertView.findViewById(R.id.img_item_Ban);
            holder.imgGoiMon = convertView.findViewById(R.id.img_item_GoiMon);
            holder.imgThanhToan = convertView.findViewById(R.id.img_item_ThanhToan);
            holder.imgAnNut = convertView.findViewById(R.id.img_item_AnNut);
            holder.txtTenBanAn = convertView.findViewById(R.id.txt_item_TenBanAn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int maban=banList.get(position).getMaBan();
        String kttinhtrang = banDao.LayTinhTrangBanTheoMa(ban.getMaBan());
        //đổi hình theo tình trạng
        if (kttinhtrang.equals("true")) {
            holder.imgBanAn.setImageResource(R.drawable.table_bar);
        } else {
            holder.imgBanAn.setImageResource(R.drawable.ic_baseline_event_seat_40);
        }

        holder.txtTenBanAn.setText(ban.getTenban());

        //sự kiện click
        holder.imgBanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HienThiButton(holder);
            }
        });

        holder.imgGoiMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent getIHome = ((Home_Activity) context).getIntent();
//                manv = getIHome.getIntExtra("manv", 0);
                manv = vienDao.laymaNV();
                String tinhtrang = banDao.LayTinhTrangBanTheoMa(ban.getMaBan());
                if (tinhtrang.equals("false")) {
                    //Thêm bảng gọi món và update tình trạng bàn
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaBan(ban.getMaBan());
                    hoaDon.setMaNhanVien(manv);
                    hoaDon.setNgayDat(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
                    hoaDon.setTinhTrang("false");
                    hoaDon.setTongTien(0);


                    long ktra = hoaDonDao.ThemHoaDon(hoaDon);

                    banDao.CapNhatTinhTrangBan(ban.getMaBan(), "true");
                    if (ktra > 0) {
                        Toast.makeText(context, "Thêm Vào Hóa Đơn Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm Vào Hóa Đơn Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
                //chuyển qua trang looai mon
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                LoaiMonFragmnet loaimonFragment = new LoaiMonFragmnet();


                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("maban", ban.getMaBan());
                loaimonFragment.setArguments(bDataCategory);
                transaction.replace(R.id.contentView, loaimonFragment).addToBackStack("hienthibanan");
                transaction.commit();
            }
        });

        holder.imgThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaydat = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
// /chuyển dữ liệu qua trang thanh toán
                Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                iThanhToan.putExtra("maban", maban);
                iThanhToan.putExtra("tenban", ban.getTenban());
                iThanhToan.putExtra("ngaydat", ngaydat);
                context.startActivity(iThanhToan);
            }
        });

        holder.imgAnNut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnButton(holder);
            }
        });

        return convertView;
    }

    private void HienThiButton(ViewHolder holder) {
        holder.imgGoiMon.setVisibility(View.VISIBLE);
        holder.imgThanhToan.setVisibility(View.VISIBLE);
        holder.imgAnNut.setVisibility(View.VISIBLE);
    }

    private void AnButton(ViewHolder holder) {
        holder.imgGoiMon.setVisibility(View.INVISIBLE);
        holder.imgThanhToan.setVisibility(View.INVISIBLE);
        holder.imgAnNut.setVisibility(View.INVISIBLE);
    }
}