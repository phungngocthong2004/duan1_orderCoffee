package com.example.da1_odercoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class Splash extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    Button btnSplash;
    LinearLayout imgLogo, text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Animation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        imgLogo = findViewById(R.id.img_logo);
        text = findViewById(R.id.text);

        imgLogo.setAnimation(topAnim);
        text.setAnimation(bottomAnim);
        // Chuyển Activity
        btnSplash = findViewById(R.id.btn_Splash);
        btnSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
    }
}