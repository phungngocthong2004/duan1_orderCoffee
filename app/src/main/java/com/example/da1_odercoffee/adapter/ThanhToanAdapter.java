package com.example.da1_odercoffee.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.model.ChiTietHoaDon;
import com.example.da1_odercoffee.model.ThanhToan;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThanhToanAdapter extends BaseAdapter {
    private Context context;
    private List<ThanhToan> listThanhToan;
    CircleImageView img_thanhtoan_HinhMon;
    TextView txt_thanhtoan_TenMon, txt_thanhtoan_SoLuong, txt_thanhtoan_GiaTien;

    public ThanhToanAdapter(Context context, List<ThanhToan> listThanhToan) {
        this.context = context;
        this.listThanhToan = listThanhToan;
    }


    @Override
    public int getCount() {
        return listThanhToan.size();
    }

    @Override
    public Object getItem(int position) {
        return listThanhToan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView != null) {
            v = convertView;
        } else {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_mon_thanh_toan, parent, false);
        }

        img_thanhtoan_HinhMon = (CircleImageView) v.findViewById(R.id.imgthanhtoan);
        txt_thanhtoan_TenMon = (TextView) v.findViewById(R.id.txt_thanhtoan_tenmon);
        txt_thanhtoan_SoLuong = (TextView) v.findViewById(R.id.txt_thanhtoan_soluong);
        txt_thanhtoan_GiaTien = (TextView) v.findViewById(R.id.txt_thanhtoan_ThanhTien);


        ThanhToan thanhToanDTO = listThanhToan.get(position);

        txt_thanhtoan_TenMon.setText("Tên Món: "+thanhToanDTO.getTenMon());
        txt_thanhtoan_SoLuong.setText("Số Lượng:"+thanhToanDTO.getSoLuong());
        txt_thanhtoan_GiaTien.setText("Giá: "+thanhToanDTO.getGiaTien()+ " đ");

        byte[] paymentimg = thanhToanDTO.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(paymentimg, 0, paymentimg.length);
        img_thanhtoan_HinhMon.setImageBitmap(bitmap);

        return v;
    }

    public class ViewHolder {

    }

}
