package com.example.da1_odercoffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.da1_odercoffee.ChiTietHoaDon_activity;
import com.example.da1_odercoffee.Dao.HoaDonDao;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.adapter.HoaDonAdapter;
import com.example.da1_odercoffee.model.HoaDon;

import java.util.List;


public class HoaDonFragmnet  extends Fragment {
    ListView lvHoaDon;
    List<HoaDon> listHoaDon;
     HoaDonDao hoaDonDAO;
    HoaDonAdapter hoaDonAdapter;

    FragmentManager fragmentManager;
    int madon, manv, maban;
    String ngaydat;
    int tongtien;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenthoadon,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Home_Activity)getActivity()).getSupportActionBar().setTitle("Hóa đơn");
        setHasOptionsMenu(true);
        lvHoaDon=view.findViewById(R.id.lvHoaDon);

        hoaDonDAO = new HoaDonDao(getActivity());
        listHoaDon = hoaDonDAO.LayTatCaHoaDon();
        hoaDonAdapter = new HoaDonAdapter(getActivity(),listHoaDon);
        lvHoaDon.setAdapter(hoaDonAdapter);
        hoaDonAdapter.notifyDataSetChanged();


        lvHoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                madon = listHoaDon.get(position).getMaHoaDon();
                manv = listHoaDon.get(position).getMaNhanVien();
                maban = listHoaDon.get(position).getMaBan();
                ngaydat = listHoaDon.get(position).getNgayDat();
                tongtien =listHoaDon.get(position).getTongTien();

                Intent intent = new Intent(getActivity(), ChiTietHoaDon_activity.class);
                intent.putExtra("madon",madon);
                intent.putExtra("manv",manv);
                intent.putExtra("maban",maban);
                intent.putExtra("ngaydat",ngaydat);
                intent.putExtra("tongtien",tongtien);
                startActivity(intent);
            }
        });
    }
}
