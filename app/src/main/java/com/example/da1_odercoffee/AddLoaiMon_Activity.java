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

import com.example.da1_odercoffee.Dao.LoaiMonDao;
import com.example.da1_odercoffee.Dao.MonDao;
import com.example.da1_odercoffee.model.LoaiMon;
import com.example.da1_odercoffee.model.Mon;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddLoaiMon_Activity extends AppCompatActivity implements View.OnClickListener {
    Button BTN_loaimon_TaoLoai;
    ImageView IMG_addloaimon_back, IMG_addloaimon_ThemHinh;
    TextView TXT_addtenloai_title;
    TextInputLayout TXTL_addcategory_TenLoai;
    LoaiMonDao loaiMonDAO;
    int maloai = 0;
    Bitmap bitmapold;
    LoaiMon loaiMon;
    String sTenMon;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addloaimon_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loai_mon);
        BTN_loaimon_TaoLoai = (Button) findViewById(R.id.btn_addthemloai_TaoLoai);
        TXTL_addcategory_TenLoai = (TextInputLayout) findViewById(R.id.txtl_addloaimon_TenLoai);
        IMG_addloaimon_back = (ImageView) findViewById(R.id.img_addloai_back);
        IMG_addloaimon_ThemHinh = (ImageView) findViewById(R.id.img_addloaimon_ThemHinh);
        TXT_addtenloai_title = (TextView) findViewById(R.id.txt_addloai_title);
        loaiMonDAO = new LoaiMonDao(this);


        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addloaimon_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();
//        TXTL_addcategory_TenLoai.getEditText().setText(tenloai);
        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        maloai = getIntent().getIntExtra("maloai", 0);
        if(maloai != 0){
            TXT_addtenloai_title.setText("Sửa Loại Món");
            loaiMon = loaiMonDAO.LayLoaiMonTheoMa(maloai);

            TXTL_addcategory_TenLoai.getEditText().setText(loaiMon.getTenLoai());


            byte[] menuimage = loaiMon.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            IMG_addloaimon_ThemHinh.setImageBitmap(bitmap);



            BTN_loaimon_TaoLoai.setText("Sửa  Loại món");
        }

        //endregion

        IMG_addloaimon_ThemHinh.setOnClickListener(this);
        BTN_loaimon_TaoLoai.setOnClickListener(this);
        IMG_addloaimon_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        if (id== R.id.img_addloaimon_ThemHinh) {

            Intent iGetIMG = new Intent();
            iGetIMG.setType("image/*");
            iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG, getResources().getString(R.string.choseimg)));
        }else if (id==R.id.img_addloai_back) {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }else if (id==R.id.btn_addthemloai_TaoLoai) {
            //ktra validation
            if(!validateImage() | !validateName() ){
                return;
            }

            sTenMon = TXTL_addcategory_TenLoai.getEditText().getText().toString();


          LoaiMon mon=new LoaiMon();
            mon.setTenLoai(sTenMon);
            mon.setHinhAnh(imageViewtoByte(IMG_addloaimon_ThemHinh));
            if(maloai!= 0){
                ktra = loaiMonDAO.SuaLoaiMon(mon,maloai);
                chucnang = "sualoai";
            }else {
                ktra = loaiMonDAO.ThemLoaiMon(mon);
                chucnang = "themloai";
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
        BitmapDrawable drawable = (BitmapDrawable)IMG_addloaimon_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = TXTL_addcategory_TenLoai.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addcategory_TenLoai.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addcategory_TenLoai.setError(null);
            TXTL_addcategory_TenLoai.setErrorEnabled(false);
            return true;
        }
    }




}