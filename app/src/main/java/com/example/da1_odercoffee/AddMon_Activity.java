package com.example.da1_odercoffee;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_odercoffee.Dao.MonDao;
import com.example.da1_odercoffee.model.Mon;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMon_Activity extends AppCompatActivity implements View.OnClickListener {
    Button BTN_addmon_ThemMon,BTN_addmon_Huy;
    RelativeLayout layout_trangthaimon;
    ImageView IMG_addmenu_ThemHinh, IMG_addmon_back;
    TextView TXT_addmenu_title;
    TextInputLayout TXTL_addmon_TenMon,TXTL_addmon_GiaTien,TXTL_addmon_LoaiMon;
    RadioGroup RG_addmon_TinhTrang;
    RadioButton RD_addmon_ConMon, RD_addmon_HetMon;
    MonDao monDAO;
    String tenloai, sTenMon,sTinhTrang;
    Bitmap bitmapold;
    int GiaTien;
    int maloai;
    int mamon = 0;
    Mon mon;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mon);
        IMG_addmenu_ThemHinh = (ImageView)findViewById(R.id.img_addmon_ThemHinh);
//        IMG_addmenu_ThemHinh.setTag(R.drawable.add_photo_alternate);
        IMG_addmon_back = (ImageView)findViewById(R.id.img_addmon_back);
        TXTL_addmon_TenMon = (TextInputLayout)findViewById(R.id.txtl_addmon_TenMon);
        TXTL_addmon_GiaTien = (TextInputLayout)findViewById(R.id.txtl_addmon_GiaTien);
        TXTL_addmon_LoaiMon = (TextInputLayout)findViewById(R.id.txtl_addmon_LoaiMon);
        BTN_addmon_ThemMon = (Button)findViewById(R.id.btn_addmon_ThemMon);

        TXT_addmenu_title = (TextView)findViewById(R.id.txt_addmon_title);
        layout_trangthaimon = (RelativeLayout)findViewById(R.id.layout_trangthaimon);
        RG_addmon_TinhTrang = (RadioGroup)findViewById(R.id.rg_addmon_TinhTrang);
        RD_addmon_ConMon = (RadioButton)findViewById(R.id.rd_addmon_ConMon);
        RD_addmon_HetMon = (RadioButton)findViewById(R.id.rd_addmon_HetMon);
        Intent intent = getIntent();
        maloai = intent.getIntExtra("maLoai",-1);
        tenloai = intent.getStringExtra("tenLoai");

        monDAO = new MonDao(this);  //khởi tạo đối tượng dao kết nối csdl


        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();
        TXTL_addmon_LoaiMon.getEditText().setText(tenloai);
        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        mamon = getIntent().getIntExtra("mamon",0);
        if(mamon != 0){
            TXT_addmenu_title.setText("Sửa Món");
            mon = monDAO.LayMonTheoMa(mamon);

            TXTL_addmon_TenMon.getEditText().setText(mon.getTenMon());
            TXTL_addmon_GiaTien.getEditText().setText(mon.getGiaTien()+"");
            TXTL_addmon_LoaiMon.getEditText().setText(tenloai);

            byte[] menuimage = mon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);

            layout_trangthaimon.setVisibility(View.VISIBLE);
            String tinhtrang = mon.getTinhTrang();
            if(tinhtrang.equals("true")){
                RD_addmon_ConMon.setChecked(true);
            }else {
                RD_addmon_HetMon.setChecked(true);
            }

            BTN_addmon_ThemMon.setText("Sửa món");
        }

        //endregion

        IMG_addmenu_ThemHinh.setOnClickListener(this);
        BTN_addmon_ThemMon.setOnClickListener(this);
        IMG_addmon_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        if (id== R.id.img_addmon_ThemHinh) {

            Intent iGetIMG = new Intent();
            iGetIMG.setType("image/*");
            iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG, getResources().getString(R.string.choseimg)));
        }else if (id==R.id.img_addmon_back) {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }else if (id==R.id.btn_addmon_ThemMon) {
            //ktra validation
            if(!validateImage() | !validateName() | !validatePrice()){
                return;
            }

            sTenMon = TXTL_addmon_TenMon.getEditText().getText().toString();
            GiaTien = Integer.parseInt(TXTL_addmon_GiaTien.getEditText().getText().toString());
            if (RG_addmon_TinhTrang.getCheckedRadioButtonId() == R.id.rd_addmon_ConMon) {
                sTinhTrang = "true";
            } else if (RG_addmon_TinhTrang.getCheckedRadioButtonId() == R.id.rd_addmon_HetMon) {
                sTinhTrang = "false";
            }

            Mon mon = new Mon();
            mon.setMaLoai(maloai);
            mon.setTenMon(sTenMon);
            mon.setGiaTien(GiaTien);
            mon.setTinhTrang(sTinhTrang);
            mon.setHinhAnh(imageViewtoByte(IMG_addmenu_ThemHinh));
            if(mamon!= 0){
                ktra = monDAO.SuaMon(mon,mamon);
                chucnang = "suamon";
            }else {
                ktra = monDAO.ThemMon(mon);
                chucnang = "themmon";
            }

            //Thêm, sửa món dựa theo obj loaimonDTO
            Intent intent = new Intent();
            intent.putExtra("ktra",ktra);
            intent.putExtra("chucnang",chucnang);
            setResult(RESULT_OK,intent);
            finish();

        }


    }
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region Validate field
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addmon_TenMon.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmon_TenMon.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addmon_TenMon.setError(null);
            TXTL_addmon_TenMon.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice(){
        String val = TXTL_addmon_GiaTien.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmon_GiaTien.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_addmon_GiaTien.setError("Giá tiền không hợp lệ");
            return false;
        }else {
            TXTL_addmon_GiaTien.setError(null);
            TXTL_addmon_GiaTien.setErrorEnabled(false);
            return true;
        }
    }
    //endregion


}