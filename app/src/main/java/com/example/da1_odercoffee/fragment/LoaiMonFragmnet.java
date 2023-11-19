package com.example.da1_odercoffee.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.da1_odercoffee.AddLoaiMon_Activity;
import com.example.da1_odercoffee.Dao.LoaiMonDao;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.adapter.LoaiMonAdapter;
import com.example.da1_odercoffee.model.LoaiMon;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class LoaiMonFragmnet extends Fragment {

    GridView gridView;
    List<LoaiMon> loaiMonList;
    LoaiMonDao loaiMonDAO;
    LoaiMonAdapter adapterLoai;
    FragmentManager fragmentManager;
    int maban;
    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themloai"))
                        {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Sủa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnetloaimon,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((Home_Activity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản Lý Loại Món</font>"));

        gridView = (GridView)view.findViewById(R.id.gvLoaiMon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonDAO = new LoaiMonDao(getActivity());
        HienThiDSLoai();

        Bundle bDataCategory = getArguments();
        if(bDataCategory != null){
            maban = bDataCategory.getInt("maban");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonList.get(position).getMaLoai();
                String tenloai = loaiMonList.get(position).getTenLoai();
                MonFragmnet monFragmnet = new MonFragmnet();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putString("tenloai",tenloai);
                bundle.putInt("maban",maban);
                monFragmnet.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentView,monFragmnet).addToBackStack("hienthiloai");
                transaction.commit();
            }
        });
        registerForContextMenu(gridView);


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //xử lí context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiMonList.get(vitri).getMaLoai();

        if (id==R.id.itEdit) {

            Intent iEdit = new Intent(getActivity(), AddLoaiMon_Activity.class);
            iEdit.putExtra("maloai", maloai);
            resultLauncherCategory.launch(iEdit);
        }else if (id==R.id.itDelete) {
            boolean ktra = loaiMonDAO.XoaLoaiMon(maloai);
            if(ktra){
                HienThiDSLoai();
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                        ,Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                        ,Toast.LENGTH_SHORT).show();
            }
        }


        return true;
    }

    //khởi tạo nút thêm loại
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1,R.id.itAddCategory,1,R.string.addCategory);
        itAddCategory.setIcon(R.drawable.baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    //xử lý nút thêm loại
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.itAddCategory){

            Intent intent = new Intent(getActivity(), AddLoaiMon_Activity.class);
            resultLauncherCategory.launch(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSLoai(){
        loaiMonList = loaiMonDAO.LayDanhSachLoaiMon();
        adapterLoai = new LoaiMonAdapter(getActivity(),loaiMonList);
        gridView.setAdapter(adapterLoai);
        adapterLoai.notifyDataSetChanged();
    }
}
