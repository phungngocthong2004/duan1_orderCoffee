package com.example.da1_odercoffee.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.da1_odercoffee.Dao.NhanVienDao;
import com.example.da1_odercoffee.Dao.QuyenDao;
import com.example.da1_odercoffee.DoiMatKhau;
import com.example.da1_odercoffee.Home_Activity;
import com.example.da1_odercoffee.R;
import com.example.da1_odercoffee.SignIn;
import com.example.da1_odercoffee.model.NhanVien;


public class ThongTinFragmnet extends Fragment {
    View view;
    LinearLayout lnDoiMK;
    TextView tvTenNV, tvChucVu, tvTinhTrang, tvDienThoai;
    Button btnSignOut;
    NhanVien nhanVien;
    NhanVienDao nhanVienDao;
    QuyenDao quyenDao;
    int manv, maQuyen;
    String tenQuyen;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((Home_Activity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Thông Tin</font>"));
        view = inflater.inflate(R.layout.fragmentthongtin,container,false);
        nhanVienDao = new NhanVienDao(getContext());
        quyenDao = new QuyenDao(getContext());
        initUI();
        getThongTin();
        nextActivity();
        dangXuat();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void initUI(){
        tvTenNV = view.findViewById(R.id.tv_TT_TenNV);
        tvChucVu = view.findViewById(R.id.tv_TT_ChucVu);
        tvTinhTrang = view.findViewById(R.id.tv_TT_TinhTrang);
        tvDienThoai = view.findViewById(R.id.tv_TT_DienThoai);
        btnSignOut = view.findViewById(R.id.btn_SignOut);
        lnDoiMK = view.findViewById(R.id.ln_DoiMK);
    }
    private void getThongTin(){
        Intent intent = getActivity().getIntent();
        manv = intent.getIntExtra("manv",0);

        nhanVien = nhanVienDao.LayNVTheoMa(manv);
        maQuyen = nhanVienDao.LayQuyenNV(manv);
        tenQuyen = quyenDao.LayTenQuyenTheoMa(maQuyen);
        tvTenNV.setText(nhanVien.getHoTenNV());
        tvChucVu.setText(tenQuyen);
        tvTinhTrang.setText("Đang hoạt động");
        tvDienThoai.setText(nhanVien.getSoDT());

    }
    private void nextActivity(){
        lnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoiMatKhau.class);
                intent.putExtra("manv",manv);
                startActivity(intent);
            }
        });
    }
    private void dangXuat(){
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performLogout();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
    private void performLogout() {
        // Thực hiện các thao tác đăng xuất tại đây
        // Khởi tạo Intent để chuyển đến màn hình đăng nhập
        Intent intent = new Intent(getContext(), SignIn.class);
        startActivity(intent);
    }
}
