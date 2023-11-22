package com.example.da1_odercoffee.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.da1_odercoffee.AddMon_Activity;
import com.example.da1_odercoffee.Dao.MonDao;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.SoluongMonActivity;
import com.example.da1_odercoffee.adapter.MonAdapter;
import com.example.da1_odercoffee.model.Mon;

import java.util.List;


public class MonFragmnet extends Fragment {
    int maloai, maban;
    String tenloai,tinhtrang;
    GridView gvfragmnetMon;
    MonDao monDAO;
    List<Mon> monList;
   MonAdapter adapterDisplayMenu;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themmon"))
                        {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSMon();
                                Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentmon,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Home_Activity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản Lý Món</font>"));
        monDAO = new MonDao(getActivity());

        gvfragmnetMon = (GridView)view.findViewById(R.id.gvDisplayMon);

        Bundle bundle = getArguments();
        if(bundle !=null){
            maloai = bundle.getInt("maloai");
            tenloai = bundle.getString("tenloai");
            maban = bundle.getInt("maban");
            HienThiDSMon();

            gvfragmnetMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //nếu lấy đc mã bàn mới mở
                    tinhtrang = monList.get(position).getTinhTrang();
                    if(maban != 0){
                        if(tinhtrang.equals("true")){
                            Intent iAmount = new Intent(getActivity(), SoluongMonActivity.class);
                            iAmount.putExtra("maban",maban);
                            iAmount.putExtra("mamon",monList.get(position).getMaMon());
                            startActivity(iAmount);
                        }else {
                            Toast.makeText(getActivity(),"Món đã hết, không thể thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        setHasOptionsMenu(true);
        registerForContextMenu(gvfragmnetMon);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getParentFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Tạo phần sửa và xóa trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamon = monList.get(vitri).getMaMon();

        if (id==R.id.itEdit) {

            Intent iEdit = new Intent(getActivity(), AddMon_Activity.class);
            iEdit.putExtra("mamon", mamon);
            iEdit.putExtra("maLoai", maloai);
            iEdit.putExtra("tenLoai", tenloai);
            resultLauncherMenu.launch(iEdit);
        }else if (id==R.id.itDelete){

            boolean ktra = monDAO.XoaMon(mamon);
            if(ktra){
                HienThiDSMon();
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                        ,Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                        ,Toast.LENGTH_SHORT).show();
            }

        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddMenu = menu.add(1,R.id.itAddMenu,1,"Thêm Món Thành Công");
        itAddMenu.setIcon(R.drawable.baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.itAddMenu){

            Intent intent = new Intent(getActivity(), AddMon_Activity.class);
            intent.putExtra("maLoai",maloai);
            intent.putExtra("tenLoai",tenloai);
            resultLauncherMenu.launch(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    private void HienThiDSMon(){
        monList = monDAO.LayDanhSachMonTheoLoai(maloai);
        adapterDisplayMenu = new MonAdapter(getActivity(),monList);
        gvfragmnetMon.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }
}
