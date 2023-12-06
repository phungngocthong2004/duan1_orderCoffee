package com.example.da1_odercoffee.adapter;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.da1_odercoffee.Dao.BanDao;
import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.model.Ban;
import com.example.da1_odercoffee.model.HoaDon;
import com.example.da1_odercoffee.model.NhanVien;
import com.example.da1_odercoffee.model.ThanhToan;

import java.util.List;

public class HoaDonAdapter extends BaseAdapter {

    private Context context;
    private List<HoaDon>listHoaDon;
    TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
            ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;
    NhanVienDao nhanVienDAO;
    private List<ThanhToan>listthanhtoan;
    BanDao banAnDAO;
    int tongtien=0;

    public HoaDonAdapter(Context context, List<HoaDon> listHoaDon) {
        this.context = context;
        this.listHoaDon = listHoaDon;
        nhanVienDAO=new NhanVienDao(context);
        banAnDAO=new BanDao(context);

    }

    @Override
    public int getCount() {
        return listHoaDon.size();
    }

    @Override
    public Object getItem(int position) {
        return listHoaDon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listHoaDon.get(position).getMaHoaDon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if(convertView!= null) {
            v = convertView;
        }else {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_hoadon,parent,false);
        }
            txt_customstatistic_MaDon = v.findViewById(R.id.tv_MaHoaDon);
            txt_customstatistic_NgayDat = v.findViewById(R.id.tv_NgayHoaDon);
            txt_customstatistic_TenNV = v.findViewById(R.id.tv_HoaDon_TenNV);
            txt_customstatistic_TongTien =v.findViewById(R.id.tv_HoaDon_TongTien);
            txt_customstatistic_TrangThai = v.findViewById(R.id.tv_HoaDon_TinhTrang);
            txt_customstatistic_BanDat = v.findViewById(R.id.tv_HoaDon_Ban);

        HoaDon hd =listHoaDon.get(position);
        txt_customstatistic_MaDon.setText("Mã đơn: " + hd.getMaHoaDon());
        txt_customstatistic_NgayDat.setText(hd.getNgayDat());
        txt_customstatistic_TongTien.setText("Tổng tiền: " + hd.getTongTien() + " VNĐ");
        if (hd.getTinhTrang().equals("true"))
        {
            txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
             txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
//        NhanVien nhanVienDTO = nhanVienDAO.LayNVTheoMa(hd.getMaHoaDon());
           txt_customstatistic_TenNV.setText("Nhân viên: " + nhanVienDAO.getTenNhanVien(hd.getMaNhanVien()));
           txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(hd.getMaBan()));
        return v;
    }
}

