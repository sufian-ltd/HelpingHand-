package com.example.easymap3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeServiceActivity extends AppCompatActivity {

    RadioGroup rgService;
    String email,password;
    People people;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service);
        rgService=findViewById(R.id.rgService);
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        getPeople();
    }

    public void newPostClick(View view) {
        Intent intent=new Intent(HomeServiceActivity.this,ElectricianNewPostActivity.class);
        intent.putExtra("type",getType());
        intent.putExtra("people",people);
        startActivity(intent);
    }

    public void myPostClick(View view) {
        Intent intent=new Intent(HomeServiceActivity.this,ElectricianMyPostActivity.class);
        intent.putExtra("type",getType());
        intent.putExtra("people",people);
        startActivity(intent);
    }

    public void allPostClick(View view) {
        Intent intent=new Intent(HomeServiceActivity.this,ElectricianAllPostActivity.class);
        intent.putExtra("type",getType());
        intent.putExtra("people",people);
        startActivity(intent);
    }

    private void getPeople(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<People>> call=apiInterface.getPeople(email,password);
        call.enqueue(new Callback<List<People>>() {
            @Override
            public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                people=response.body().get(0);
            }

            @Override
            public void onFailure(Call<List<People>> call, Throwable t) {
                Toast.makeText(HomeServiceActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getType(){
        if(rgService.getCheckedRadioButtonId()==R.id.rbElectrician)
            return "electrician";
        else if(rgService.getCheckedRadioButtonId()==R.id.rbPlamber)
            return "plumber";
        else if(rgService.getCheckedRadioButtonId()==R.id.rbDeliveryMan)
            return "deliveryman";
        else if(rgService.getCheckedRadioButtonId()==R.id.rbAmbulance)
            return "ambulance";
        else
            return "homemaid";
    }

    public void profileClick(View view) {
        Intent intent=new Intent(HomeServiceActivity.this,ProfileActivity.class);
        intent.putExtra("people",people);
        startActivity(intent);
    }

    public void myRequestClick(View view) {
        Intent intent=new Intent(HomeServiceActivity.this,MyRequestActivity.class);
        intent.putExtra("people",people);
        intent.putExtra("type",getType());
        startActivity(intent);
    }

    public void logoutClick(View view) {
        sharedPreferences=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
        finish();
    }

    public void myAcceptedClick(View view) {
        Intent intent=new Intent(HomeServiceActivity.this,MyAcceptedUserActivity.class);
        intent.putExtra("people",people);
        intent.putExtra("type",getType());
        startActivity(intent);
    }
}
