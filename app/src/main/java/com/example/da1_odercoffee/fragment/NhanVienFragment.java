package com.example.da1_odercoffee.fragment;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.da1_odercoffee.AddNhanVien_activity;
import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.DoiMatKhau;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.adapter.NhanVienAdapter;
import com.example.da1_odercoffee.model.NhanVien;

import java.util.List;

public class NhanVienFragment extends Fragment {
    ListView gvStaff;
    List<NhanVien>listnhanvien;
    NhanVienDao nvienDao;
    NhanVienAdapter nhanVienAdapter;
    SearchView searchView;
    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        long ktra = intent.getLongExtra("ketquaktra",0);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themnv"))
                        {
                            if(ktra != 0){
                                HienThiDSNV();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra != 0){
                                HienThiDSNV();
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
        return inflater.inflate(R.layout.fragmnetnhanvien,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Home_Activity)getActivity()).getSupportActionBar().setTitle("Quản lý nhân viên");
        setHasOptionsMenu(true);
        gvStaff=view.findViewById(R.id.gvStaff);
        searchView=view.findViewById(R.id.seaview);
        searchView.clearFocus();
        searchView.setIconifiedByDefault(false);

        nvienDao = new NhanVienDao(getActivity());
        HienThiDSNV();

        registerForContextMenu(gvStaff);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listnhanvien.clear();
                listnhanvien.addAll(nvienDao.getimKiem(newText));
                if (newText.isEmpty()) {
                    listnhanvien.clear();
                    listnhanvien.addAll(nvienDao.LayDanhsachNhanVien());
                }
                nhanVienAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manv = listnhanvien.get(vitri).getMaNV();

        if (id==R.id.itEdit) {

            Intent iEdit = new Intent(getActivity(), AddNhanVien_activity.class);
            iEdit.putExtra("manv", manv);
            resultLauncherAdd.launch(iEdit);

        } else if (id == R.id.itDelete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên này?");
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean ktra = nvienDao.XoaNV(manv);
                    if (ktra) {
                        HienThiDSNV();
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_sucessful),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_failed),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Hủy bỏ", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddStaff = menu.add(1,R.id.itAddStaff,1,"Thêm nhân viên");
        itAddStaff.setIcon(R.drawable.add);
        itAddStaff.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.itAddStaff){

            Intent iDangky = new Intent(getActivity(), AddNhanVien_activity.class);
            resultLauncherAdd.launch(iDangky);

        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSNV(){
        listnhanvien = nvienDao.LayDanhsachNhanVien();
        nhanVienAdapter = new NhanVienAdapter(getContext(),listnhanvien);
        gvStaff.setAdapter(nhanVienAdapter);
        nhanVienAdapter.notifyDataSetChanged();
    }
}
