package com.example.easymap3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    ImageView ivIcon;
    TextView tvJoin;
    Button btnMosque,btnSchool,btnHospital,btnCriteria,btnUsers;
    String email,password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ivIcon=findViewById(R.id.ivIcon);
        tvJoin=findViewById(R.id.tvJoin);
        btnMosque=findViewById(R.id.btnMosque);
        btnHospital=findViewById(R.id.btnHospital);
        btnSchool=findViewById(R.id.btnSchool);
        btnCriteria=findViewById(R.id.btnCriteria);
        btnUsers=findViewById(R.id.btnUsers);
        setAnimation();
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        if(email.equals("none") && password.equals("none"))
            btnUsers.setVisibility(View.GONE);
    }
    private void setAnimation(){
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
        ivIcon.startAnimation(animation2);
        btnMosque.startAnimation(animation2);
        btnHospital.startAnimation(animation2);
        btnCriteria.startAnimation(animation2);
        btnSchool.startAnimation(animation2);
        btnUsers.startAnimation(animation2);
    }
    private void gotoMap(String name){
        Intent intent=new Intent(DashboardActivity.this,MapsActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("keyword","");
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    public void criteriaClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,SearchCriteriaActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    public void searchClick(View view) {
        if(view.getId()==R.id.btnMosque)
            gotoMap("mosque");
        else if(view.getId()==R.id.btnHospital)
            gotoMap("hospital");
        else if(view.getId()==R.id.btnSchool){
            gotoMap("school");
        }
    }

    public void showUsers(View view) {
        Intent intent=new Intent(DashboardActivity.this,UsersManagementActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    public void logoutClick(View view) {
        sharedPreferences=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("userEmail");
        editor.remove("userPassword");
        editor.apply();
        finish();
    }
}
