package com.example.da1_odercoffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da1_odercoffee.Dao.HoaDonDao;
import com.example.da1_odercoffee.Dao.LoaiMonDao;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.adapter.AdapterRecycelviewHoaDon;
import com.example.da1_odercoffee.adapter.AdapterRecycelviewLoaimon;
import com.example.da1_odercoffee.adapter.LoaiMonAdapter;
import com.example.da1_odercoffee.model.HoaDon;
import com.example.da1_odercoffee.model.LoaiMon;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.List;

public class HomeFragmnet extends Fragment implements View.OnClickListener {
    RecyclerView rcLoaiMOn,rcHoaDon;
    TextView txtXemAllLoaiMon,txtxemAllHoaDon;
    List<LoaiMon>litstloai;
    LoaiMonAdapter loaiMonAdapter;
    LoaiMonDao loaiMonDao;
    ViewFlipper viewFlipper;
    AdapterRecycelviewLoaimon recycelviewLoaimon;
    List<HoaDon>listhoadon;
    HoaDonDao hoaDonDao;
    AdapterRecycelviewHoaDon adapterRecycelviewHoaDon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenthome,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFlipper=view.findViewById(R.id.viewFlipper);
        rcLoaiMOn=view.findViewById(R.id.rcv_fragmnetHome_LoaiMon);
        rcHoaDon=view.findViewById(R.id.rcv_fragmenthome_hoadon);
        txtXemAllLoaiMon=view.findViewById(R.id.txt_fragmnetHome_xemtatca);
        txtxemAllHoaDon=view.findViewById(R.id.txt_fragmnethome_XemTatHoaDon);

        loaiMonDao=new LoaiMonDao(getContext());
        HienThiDSLoai();

        hoaDonDao=new HoaDonDao( getActivity());
        HienThiDanhsachHoaDon();
        AcctionViewFllipper();




        txtxemAllHoaDon.setOnClickListener(this);
        txtXemAllLoaiMon.setOnClickListener(this);

    }
    private  void AcctionViewFllipper(){
        List<String>mangquangcao=new ArrayList<>();
        mangquangcao.add("https://png.pngtree.com/png-clipart/20210620/original/pngtree-coffee-color-coffee-shop-promotion-banner-png-image_6440532.jpg");
        mangquangcao.add("https://img.freepik.com/free-psd/delicious-coffee-sale-facebook-cover-banner-design-template_268949-37.jpg?size=626&ext=jpg");
        mangquangcao.add("https://www.tiendauroi.com/wp-content/uploads/2020/05/vinid-highlandcofffee.png");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getContext());
            Glide.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        Animation slide_in= AnimationUtils.loadAnimation(getContext(),R.anim.slide_qquoangcao);
        Animation slide_out= AnimationUtils.loadAnimation(getContext(),R.anim.slide_out_quangcao);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setInAnimation(slide_out);
    }
    private void HienThiDSLoai(){
        rcLoaiMOn.setHasFixedSize(true);
        rcLoaiMOn.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        litstloai = loaiMonDao.LayDanhSachLoaiMon();
        recycelviewLoaimon=new AdapterRecycelviewLoaimon(getActivity(),litstloai);
        rcLoaiMOn.setAdapter(recycelviewLoaimon);

        recycelviewLoaimon.notifyDataSetChanged();

    }
    private void HienThiDanhsachHoaDon(){
        rcHoaDon.setHasFixedSize(true);
        rcHoaDon.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        listhoadon = hoaDonDao.LayTatCaHoaDon();
        adapterRecycelviewHoaDon=new AdapterRecycelviewHoaDon(getActivity(),listhoadon);
       rcHoaDon.setAdapter(adapterRecycelviewHoaDon);
        adapterRecycelviewHoaDon.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

//        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navigation_view_trangchu);
        if (id==R.id.txt_fragmnetHome_xemtatca) {
            FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
            tranDisplayCategory.replace(R.id.contentView,new LoaiMonFragmnet());
            tranDisplayCategory.addToBackStack(null);
            tranDisplayCategory.commit();
        } else if (id==R.id.txt_fragmnethome_XemTatHoaDon) {
            FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
            tranDisplayStatistic.replace(R.id.contentView,new HoaDonFragmnet());
            tranDisplayStatistic.addToBackStack(null);
            tranDisplayStatistic.commit();
        }


    }
}
