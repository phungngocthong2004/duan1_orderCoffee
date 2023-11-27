package com.example.da1_odercoffee.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.da1_odercoffee.AddBan_Activity;
import com.example.da1_odercoffee.Dao.BanDao;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.adapter.BanAdapter;
import com.example.da1_odercoffee.model.Ban;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BanFragment extends Fragment {
    GridView gridView;
    List<Ban> banlist;
    BanDao banDao;
    BanAdapter banAdapter;
    TextInputLayout TXTL_addtable_tenban;
    Button BTN_addtable_TaoBan;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentban, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((Home_Activity)getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản lý bàn</font>"));
        gridView = view.findViewById(R.id.gv_fragmnet_table);

        banDao = new BanDao(getContext());
        HienThiDSBan();

        registerForContextMenu(gridView);

    }
    //tạo ra context menu khi longclick
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Xử lí cho từng trường hợp trong contextmenu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = banlist.get(vitri).getMaBan();
        if(id==R.id.itEdit) {

            Intent intent = new Intent(getActivity(), AddBan_Activity.class);
            intent.putExtra("maban", maban);
//            resultLauncherEdit.launch(intent);
        } else if (id==R.id.itDelete) {

            boolean ktraxoa = banDao.XoaBanTheoMa(maban);
            if(ktraxoa){
                HienThiDSBan();
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful),Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed),Toast.LENGTH_SHORT).show();
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddTable = menu.add(1,R.id.itAddTable,1,R.string.addTable);
        itAddTable.setIcon(R.drawable.baseline_add_24);
        itAddTable.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==R.id.itAddTable){

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.activity_add_ban, null);
            builder.setView(v);
            Dialog dialog = builder.create();
            dialog.show();

           BTN_addtable_TaoBan=v.findViewById(R.id.btn_addtable_TaoBan);
            TXTL_addtable_tenban=v.findViewById(R.id.txt_addtable_tenban);
            BTN_addtable_TaoBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sTenBanAn = TXTL_addtable_tenban.getEditText().getText().toString();
                    if(validatvName()){
                        boolean ktra = banDao.ThemBanAn(sTenBanAn);

                        if (ktra){
                            HienThiDSBan();
                            Toast.makeText(getContext(), "Thêm bàn Thành Công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Thêm bàn Thất Bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        banAdapter.notifyDataSetChanged();
    }
    private void HienThiDSBan() {

        banlist = banDao.LayTatCaBan();
        banAdapter = new BanAdapter(getActivity(), banlist);
        gridView.setAdapter(banAdapter);
        banAdapter.notifyDataSetChanged();
    }
    private boolean validatvName(){
        String val = TXTL_addtable_tenban.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addtable_tenban.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addtable_tenban.setError(null);
            TXTL_addtable_tenban.setErrorEnabled(false);
            return true;
        }
    }

}
