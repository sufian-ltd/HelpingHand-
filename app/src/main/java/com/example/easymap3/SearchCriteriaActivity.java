package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchCriteriaActivity extends AppCompatActivity {

    Spinner spnBank,spnBranch;
    Button btnSearch;
    ImageView ivIcon;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_criteria);
        ivIcon=findViewById(R.id.ivIcon);
        spnBank=findViewById(R.id.spnBank);
        spnBranch=findViewById(R.id.spnBranch);
        btnSearch=findViewById(R.id.btnSearch);
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        setAnimation();
        initialize();
    }

    private void setAnimation(){
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
        ivIcon.startAnimation(animation2);
        spnBank.startAnimation(animation2);
        spnBranch.startAnimation(animation2);
        btnSearch.startAnimation(animation2);
    }

    public void searchClick(View view) {
        String name=spnBank.getSelectedItem().toString();
//        String keyword=spnBranch.getSelectedItem().toString();
        gotoMap(name);
    }

    private void gotoMap(String name){
        Intent intent=new Intent(SearchCriteriaActivity.this,MapsActivity.class);
        intent.putExtra("keyword",name);
        intent.putExtra("name","bank");
        intent.putExtra("email",email);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    public void initialize(){
        String bank[]={
                "AB Bank",
                "UCB Bank",
                "Prime Bank",
                "Pubali Bank",
                "One Bank",
                "Mutual Trust Bank",
                "Standard Bank"
        };
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this, R.layout.spinner_items,bank);
        spnBank.setAdapter(adapter1);
        String branch[]={
                "Probortok",
                "Chokbajar",
                "Andorkilla",
                "GEC",
                "Muradpur",
                "Agrabad"
        };
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, R.layout.spinner_items,branch);
        spnBranch.setAdapter(adapter2);
//        String radius[]={
//                "500 Meter",
//                "1000 Meter",
//                "1500 Meter",
//                "2000 Meter",
//                "5000 Meter",
//                "10000 Meter"
//        };
//        ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this, R.layout.spinner_items,radius);
//        spnradius.setAdapter(adapter3);
    }
}
