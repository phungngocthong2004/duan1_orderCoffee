package com.example.da1_odercoffee.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.model.LoaiMon;

import java.util.List;

public class AdapterRecycelviewLoaimon extends RecyclerView.Adapter<AdapterRecycelviewLoaimon.ViewHolder> {
    private Context context;
    private List<LoaiMon> list;

    public AdapterRecycelviewLoaimon(Context context, List<LoaiMon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaimon,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiMon loaiMonDTO = list.get(position);
         holder.txt_loaimon_TenLoai.setText(loaiMonDTO.getTenLoai());
        byte[] categoryimage = loaiMonDTO.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(categoryimage,0,categoryimage.length);
        holder.img_loaimon_HinhLoai.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_loaimon_TenLoai;
        ImageView img_loaimon_HinhLoai;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            txt_loaimon_TenLoai=itemView.findViewById(R.id.txt_loaimon_TenLoai);
            img_loaimon_HinhLoai=itemView.findViewById(R.id.img_loaimon_HinhLoai);
        }
    }

}
