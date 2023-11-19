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
import com.example.da1_odercoffee.model.Mon;

import java.util.List;

public class MonAdapter extends BaseAdapter {
    private Context context;
    private List<Mon> listmon;

   Viewholder viewholder;
    public MonAdapter(Context context, List<Mon> listmon) {
        this.context = context;
        this.listmon = listmon;
    }

    @Override
    public int getCount() {
        return listmon.size();
    }

    @Override
    public Object getItem(int position) {
        return listmon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listmon.get(position).getMaMon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_mon,parent,false);

            viewholder.img_mon_HinhMon = (ImageView)view.findViewById(R.id.img_mon_HinhMon);
            viewholder.txt_mon_TenMon = (TextView) view.findViewById(R.id.txt_mon_TenMon);
            viewholder.txt_mon_TinhTrang = (TextView)view.findViewById(R.id.txt_mon_TinhTrang);
            viewholder.txt_mon_GiaTien = (TextView)view.findViewById(R.id.txt_mon_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        Mon mon = listmon.get(position);
        viewholder.txt_mon_TenMon.setText(mon.getTenMon());
        viewholder.txt_mon_GiaTien.setText(mon.getGiaTien()+" VNĐ");

        //hiển thị tình trạng của món
        if(mon.getTinhTrang().equals("true")){
            viewholder.txt_mon_TinhTrang.setText("Còn món");
        }else{
            viewholder.txt_mon_TinhTrang.setText("Hết món");
        }

        //lấy hình ảnh
        if(mon.getHinhAnh() != null){
            byte[] menuimage = mon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_mon_HinhMon.setImageBitmap(bitmap);
        }else {
            viewholder.img_mon_HinhMon.setImageResource(R.drawable.anhmon);
        }

        return view;
    }


    public class Viewholder{
        ImageView img_mon_HinhMon;
        TextView txt_mon_TenMon, txt_mon_GiaTien,txt_mon_TinhTrang;

    }
}
