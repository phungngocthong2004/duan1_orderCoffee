package com.example.da1_odercoffee.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.da1_odercoffee.model.NhanVien;

import java.util.List;

public class NhanVienAdapter extends BaseAdapter {
 private Context context;
 private List<NhanVien> listNv;

    public NhanVienAdapter(Context context, List<NhanVien> listNv) {
        this.context = context;
        this.listNv = listNv;
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

        return null;
    }
}
