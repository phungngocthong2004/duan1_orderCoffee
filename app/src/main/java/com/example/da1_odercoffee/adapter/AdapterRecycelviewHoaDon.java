package com.example.da1_odercoffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_odercoffee.Dao.BanDao;
import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.model.HoaDon;
import com.example.da1_odercoffee.model.NhanVien;

import java.util.List;

public class AdapterRecycelviewHoaDon extends RecyclerView.Adapter<AdapterRecycelviewHoaDon.viewholder> {
    private Context context;
    private List<HoaDon> listHd;
    NhanVienDao vienDao;
    BanDao banDao;

    public AdapterRecycelviewHoaDon(Context context, List<HoaDon> listHd) {
        this.context = context;
        this.listHd = listHd;
        vienDao=new NhanVienDao(context);
        banDao=new BanDao(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new AdapterRecycelviewHoaDon.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        HoaDon donDatDTO = listHd.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaHoaDon());
        holder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        if(donDatDTO.getTongTien()==0)
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donDatDTO.getTinhTrang().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        NhanVien nhanVienDTO = vienDao.LayNVTheoMa(donDatDTO.getMaHoaDon());
        holder.txt_customstatistic_TenNV.setText(vienDao.getTenNhanVien(donDatDTO.getMaNhanVien()));
        holder.txt_customstatistic_BanDat.setText(banDao.LayTenBanTheoMa(donDatDTO.getMaBan()));
    }




    @Override
    public int getItemCount() {
        return listHd.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV, txt_customstatistic_TongTien, txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txt_customstatistic_MaDon = (TextView) itemView.findViewById(R.id.tv_MaHoaDon);
            txt_customstatistic_NgayDat = (TextView) itemView.findViewById(R.id.tv_NgayHoaDon);
            txt_customstatistic_TenNV = (TextView) itemView.findViewById(R.id.tv_HoaDon_TenNV);
            txt_customstatistic_TongTien = (TextView) itemView.findViewById(R.id.tv_HoaDon_TongTien);
            txt_customstatistic_TrangThai = (TextView) itemView.findViewById(R.id.tv_HoaDon_TinhTrang);
            txt_customstatistic_BanDat = (TextView) itemView.findViewById(R.id.tv_HoaDon_Ban);
        }
    }
}
