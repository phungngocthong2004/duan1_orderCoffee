package com.example.da1_odercoffee.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.model.LoaiMon;
import com.example.da1_odercoffee.model.Mon;

import java.util.List;

public class LoaiMonAdapter extends BaseAdapter {
    private Context context;
    private List<LoaiMon> listloai;
//    Viewholder viewholder;

    public LoaiMonAdapter(Context context, List<LoaiMon> listloai) {
        this.context = context;
        this.listloai = listloai;
    }

    @Override
    public int getCount() {
        return listloai.size();
    }

    @Override
    public Object getItem(int position) {
        return listloai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listloai.get(position).getMaLoai();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView != null) {
            v = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaimon, parent, false);
        }
        ImageView img_Loaimon = v.findViewById(R.id.img_loaimon_HinhLoai);
        TextView txt_lm_TenLoaiMon = v.findViewById(R.id.txt_loaimon_TenLoai);

        LoaiMon loaiMon = listloai.get(position);
        txt_lm_TenLoaiMon.setText(loaiMon.getTenLoai());
        //lấy hình ảnh
        if (loaiMon.getHinhAnh() != null) {
            byte[] menuimage = loaiMon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage, 0, menuimage.length);
            img_Loaimon.setImageBitmap(bitmap);
        } else {
            img_Loaimon.setImageResource(R.drawable.item_coffee1);
        }

        return v;
    }



}