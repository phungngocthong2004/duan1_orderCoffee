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

import java.util.List;

public class LoaiMonAdapter extends BaseAdapter {
    private Context context;
    private List<LoaiMon>listloai;
   Viewholder viewHolder;


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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_loaimon, parent, false);
        View view = convertView;
        //nếu lần đầu gọi view

        if (view == null) {
            viewHolder=new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_loaimon, parent, false);

            //truyền component vào viewholder để ko gọi findview ở những lần hiển thị khác
            viewHolder.imgloaimon = (ImageView) view.findViewById(R.id.img_loaimon_HinhLoai);
            viewHolder.txttenmon = (TextView) view.findViewById(R.id.txt_loaimon_TenLoai);
            view.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) view.getTag();
        }
        LoaiMon loaiMon = listloai.get(position);

        viewHolder.txttenmon.setText(loaiMon.getTenLoai());

        if(loaiMon.getHinhAnh() != null){
            byte[] menuimage = loaiMon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewHolder.imgloaimon.setImageBitmap(bitmap);
        }else {
            viewHolder.imgloaimon.setImageResource(R.drawable.th);
        }

//        byte[] categoryimage = loaiMon.getHinhAnh();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage, 0, categoryimage.length);
//        viewHolder.imgloaimon.setImageBitmap(bitmap);

        return view;

    }
    public  class  Viewholder{
        TextView txttenmon ;
        ImageView imgloaimon ;
    }
}
