package com.example.da1_odercoffee.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.fragment.app.FragmentManager;

import com.example.da1_odercoffee.AddMon_Activity;
import com.example.da1_odercoffee.Dao.ChiTietHoaDonDao;
import com.example.da1_odercoffee.Dao.HoaDonDao;
import com.example.da1_odercoffee.Dao.MonDao;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.SoluongMonActivity;
import com.example.da1_odercoffee.adapter.MonAdapter;
import com.example.da1_odercoffee.model.ChiTietHoaDon;
import com.example.da1_odercoffee.model.Mon;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class MonFragmnet extends Fragment {
    int maloai, maban;
    String tenloai, tinhtrang;
    GridView gvfragmnetMon;
    MonDao monDAO;
    List<Mon> monList;
    MonAdapter adapterDisplayMenu;
    HoaDonDao hoaDonDao;
    ChiTietHoaDonDao chiTietHoaDonDAO;
    TextInputLayout TXTL_soluong_SoLuong;
    Button BTN_soluong_DongY;
    int maquyen;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra", false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if (chucnang.equals("themmon")) {
                            if (ktra) {
                                HienThiDSMon();
                                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (ktra) {
                                HienThiDSMon();
                                Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentmon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Home_Activity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Quản Lý Món</font>"));
        monDAO = new MonDao(getActivity());
        hoaDonDao = new HoaDonDao(getContext());
        chiTietHoaDonDAO = new ChiTietHoaDonDao(getContext());

        gvfragmnetMon = (GridView) view.findViewById(R.id.gvDisplayMon);

        Bundle bundle = getArguments();
        if (bundle != null) {
            maloai = bundle.getInt("maloai");
            tenloai = bundle.getString("tenloai");
            maban = bundle.getInt("maban");
            HienThiDSMon();

            gvfragmnetMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //nếu lấy đc mã bàn mới mở
                    tinhtrang = monList.get(position).getTinhTrang();
                    if (maban != 0) {
                        if (tinhtrang.equals("true")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = inflater.inflate(R.layout.activity_soluong, null);
                            builder.setView(v);
                            Dialog dialog = builder.create();
                            dialog.show();

                            TXTL_soluong_SoLuong = v.findViewById(R.id.txtl_Soluong_SoLuong);
                            BTN_soluong_DongY = v.findViewById(R.id.btn_Soluong_DongY);

                            BTN_soluong_DongY.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!validateSoluong()) {
                                        return;
                                    }
                                    int mahoadon = (int) hoaDonDao.LayMaDonTheoMaBan(maban, "false");
                                    boolean ktra = chiTietHoaDonDAO.KiemTraMonTonTai(mahoadon, monList.get(position).getMaMon());
                                    if (ktra) {
                                        //update số lượng món đã chọn
                                        int sluongcu = chiTietHoaDonDAO.LaySLMonTheoMaDon(mahoadon, monList.get(position).getMaMon());
                                        int sluongmoi = Integer.parseInt(TXTL_soluong_SoLuong.getEditText().getText().toString());
                                        int tongsl = sluongcu + sluongmoi;

                                        ChiTietHoaDon chiTietDonDatDTO = new ChiTietHoaDon();
                                        chiTietDonDatDTO.setMaMon(monList.get(position).getMaMon());
                                        chiTietDonDatDTO.setMaHoaDon(mahoadon);
                                        chiTietDonDatDTO.setSoLuong(tongsl);

                                        boolean ktracapnhat = chiTietHoaDonDAO.CapNhatSL(chiTietDonDatDTO);
                                        if (ktracapnhat) {
                                            Toast.makeText(getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Cập Nhật Thất Bại", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        //thêm số lượng món nếu chưa chọn món này
                                        int sluong = Integer.parseInt(TXTL_soluong_SoLuong.getEditText().getText().toString());
                                        ChiTietHoaDon chiTiethoadon = new ChiTietHoaDon();
                                        chiTiethoadon.setMaMon(monList.get(position).getMaMon());
                                        chiTiethoadon.setMaHoaDon(mahoadon);
                                        chiTiethoadon.setSoLuong(sluong);

                                        long ktracapnhat = chiTietHoaDonDAO.ThemChiTietDonDat(chiTiethoadon);
                                        if (ktracapnhat > 0) {
                                            Toast.makeText(getContext(), getResources().getString(R.string.add_sucessful), Toast.LENGTH_SHORT).show();
//                                            startActivity(new Intent(getContext(), Home_Activity.class));
                                            dialog.dismiss();
                                        } else {
                                            Toast.makeText(getContext(), getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Món đã hết, không thể thêm", Toast.LENGTH_SHORT).show();
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
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    getParentFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    //Tạo phần sửa và xóa trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamon = monList.get(vitri).getMaMon();
        if (id == R.id.itEdit) {
            if (maquyen == 1) {
                Intent iEdit = new Intent(getActivity(), AddMon_Activity.class);
                iEdit.putExtra("mamon", mamon);
                iEdit.putExtra("maLoai", maloai);
                iEdit.putExtra("tenLoai", tenloai);
                resultLauncherMenu.launch(iEdit);
            } else {
                Toast.makeText(getContext(), "Nhân Viên Không có Quyền Sửa", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.itDelete) {
            if (maquyen == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xóa Món");
                builder.setMessage("Bạn có chắc chắn muốn Xóa?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean ktra = monDAO.XoaMon(mamon);
                        if (ktra) {
                            HienThiDSMon();
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_sucessful)
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_failed)
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            } else {
                Toast.makeText(getContext(), "Nhân Viên Không có Quyền Xóa", Toast.LENGTH_SHORT).show();
            }


        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddMenu = menu.add(1, R.id.itAddMenu, 1, "Thêm Món Thành Công");
        itAddMenu.setIcon(R.drawable.add);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itAddMenu) {
            if (maquyen == 1) {
                Intent intent = new Intent(getActivity(), AddMon_Activity.class);
                intent.putExtra("maLoai", maloai);
                intent.putExtra("tenLoai", tenloai);
                resultLauncherMenu.launch(intent);
            } else {
                Toast.makeText(getContext(), "Nhân Viên Không có Quyền Thêm Món", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSMon() {
        monList = monDAO.LayDanhSachMonTheoLoai(maloai);
        adapterDisplayMenu = new MonAdapter(getActivity(), monList);
        gvfragmnetMon.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }


    private boolean validateSoluong() {
        String val = TXTL_soluong_SoLuong.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            TXTL_soluong_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        } else if (!val.matches(("\\d+(?:\\.\\d+)?"))) {
            TXTL_soluong_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        } else {
            TXTL_soluong_SoLuong.setError(null);
            TXTL_soluong_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}