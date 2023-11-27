package com.example.da1_odercoffee.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da1_odercoffee.Dao.QuyenDao;
import com.example.da1_odercoffee.DoiMatKhau;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.model.NhanVien;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienAdapter extends BaseAdapter {
 private Context context;
 private List<NhanVien> listNv;
 ViewHolder viewHolder;
 QuyenDao quyenDao;

    public NhanVienAdapter(Context context, List<NhanVien> listNv) {
        this.context = context;
        this.listNv = listNv;
        quyenDao=new QuyenDao(context);
    }

    @Override
    public int getCount() {
        return listNv.size();
    }

    @Override
    public Object getItem(int position) {
        return listNv.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listNv.get(position).getMaNV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_nhanvien,parent,false);

            viewHolder.img_customstaff_HinhNV = (ImageView)view.findViewById(R.id.avatar_NhanVien);
            viewHolder.txt_customstaff_TenNV = (TextView)view.findViewById(R.id.tv_TenNV);
            viewHolder.txt_customstaff_TenQuyen = (TextView)view.findViewById(R.id.tv_QuyenNV);
            viewHolder.txt_customstaff_SDT = (TextView)view.findViewById(R.id.tv_DienThoai);


            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        NhanVien nhanVienDTO = listNv.get(position);


        viewHolder.txt_customstaff_TenNV.setText(nhanVienDTO.getHoTenNV());
        viewHolder.txt_customstaff_TenQuyen.setText("Quyền: "+quyenDao.LayTenQuyenTheoMa(nhanVienDTO.getMaQuyen()));
        viewHolder.txt_customstaff_SDT.setText("Số ĐT: "+nhanVienDTO.getSoDT());

        return view;
    }
    public class ViewHolder{
        ImageView img_customstaff_HinhNV;
        TextView txt_customstaff_TenNV, txt_customstaff_TenQuyen,txt_customstaff_SDT, txt_customstaff_Email;
    }

    }





