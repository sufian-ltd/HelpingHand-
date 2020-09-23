package com.example.easymap3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView ivIcon;
    //TextView tvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ivIcon=findViewById(R.id.ivIcon);
        //tvName=findViewById(R.id.tvName);
//        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.together);
//        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
//        ivIcon.startAnimation(animation1);
        //tvName.startAnimation(animation2);
    }

    public void homeServiceClick(View view) {
        Intent intent=new Intent(SplashScreenActivity.this,HomeServiceLoginActivity.class);
        startActivity(intent);
    }

    public void mapClick(View view) {
        Intent intent=new Intent(SplashScreenActivity.this,LoginActivity.class);
        startActivity(intent);
    }


    public void about(View view) {
        Intent intent=new Intent(SplashScreenActivity.this,AboutActivity.class);
        startActivity(intent);
    }
}
