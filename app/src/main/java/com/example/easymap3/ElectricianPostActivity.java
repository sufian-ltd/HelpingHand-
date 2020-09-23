package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricianPostActivity extends AppCompatActivity {

    EditText etAvailable,etExperience,etDetail,etCharge, etSubDiv;
    EditText etUserName,etEmail,etContact,etAddress;
    TextView tvTitle;
    Electrician electrician;
    People people;
    People peopleWhoPost;
    String type;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_post);
        people= (People) getIntent().getSerializableExtra("people");
        electrician= (Electrician) getIntent().getSerializableExtra("electrician");
        tvTitle=findViewById(R.id.tvTitle);
        etAvailable=findViewById(R.id.etAvailable);
        etExperience=findViewById(R.id.etExperience);
        etDetail=findViewById(R.id.etDetail);
        etCharge=findViewById(R.id.etCharge);
        etSubDiv=findViewById(R.id.etSubDiv);

        etUserName=findViewById(R.id.etUserName);
        etEmail=findViewById(R.id.etEmail);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        initCaption();
        getPeopleById();
        init();
    }

    private void initUser(){
        etUserName.setText("Name : "+peopleWhoPost.getName());
        etEmail.setText("Email : "+peopleWhoPost.getEmail());
        etContact.setText("Contact : "+peopleWhoPost.getContact());
        etAddress.setText("Address : "+peopleWhoPost.getAddress());
    }

    private void init() {
        etAvailable.setText("Available : "+electrician.getAvailable());
//        etExperience.setText("Experience : "+electrician.getExperience());
//        etDetail.setText("Work Details : "+electrician.getDetails());
//        etCharge.setText("Service Charge : "+electrician.getCharge());
        etSubDiv.setText("Location : "+electrician.getSubDivision());
    }

    public void sendRequest(View view) {
        String requestUserId=electrician.getRequestUserId();
        if(isAlreadySent(requestUserId,people.getId())){
            Toast.makeText(this, "You Already Sent Request...!!!", Toast.LENGTH_LONG).show();
            return;
        }
        requestUserId=requestUserId.isEmpty() ? people.getId()+"" : requestUserId+","+people.getId();
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.requestElectrician(electrician.getId(),requestUserId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(ElectricianPostActivity.this, "Your Request is Sent", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(message.equals("not update")){
                    Toast.makeText(ElectricianPostActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(ElectricianPostActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isAlreadySent(String requestUserId,int id){
        if(requestUserId.equals(""))
            return false;
        if(!requestUserId.contains(",")){
            if(id==Integer.parseInt(requestUserId))
                return true;
        }
        String str[]=requestUserId.split(",");
        for(String s : str){
            if(id==Integer.parseInt(s))
                return true;
        }
        return false;
    }

    public void viewLocation(View view) {
        Intent intent=new Intent(ElectricianPostActivity.this,SecondMapsActivity.class);
        intent.putExtra("latitude",peopleWhoPost.getLatitude());
        intent.putExtra("longitude",peopleWhoPost.getLongitude());
        intent.putExtra("name",peopleWhoPost.getName());
        intent.putExtra("share","no");
        intent.putExtra("peopleId",-1);
        startActivity(intent);
    }

    public void saveLocation(View view) {
        Intent intent=new Intent(ElectricianPostActivity.this,SecondMapsActivity.class);
        intent.putExtra("share","yes");
        intent.putExtra("peopleId",people.getId());
        startActivity(intent);
    }

    private void getPeopleById(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<People>> call=apiInterface.getPeopleById(electrician.getUserId());
        call.enqueue(new Callback<List<People>>() {
            @Override
            public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                peopleWhoPost=response.body().get(0);
                initUser();
            }

            @Override
            public void onFailure(Call<List<People>> call, Throwable t) {
                Toast.makeText(ElectricianPostActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initCaption() {
        type=electrician.getType();
        if(type.equals("electrician")){
            tvTitle.setText(Utils.electrician_post6);
            etExperience.setText(Utils.electrician_post3+" "+electrician.getExperience());
            etDetail.setText(Utils.electrician_post4+" "+electrician.getDetails());
            etCharge.setText(Utils.electrician_post5+" "+electrician.getCharge());
        }
        else if(type.equals("plumber")){
            tvTitle.setText(Utils.plamber_post6);
            etExperience.setText(Utils.plamber_post3+" "+electrician.getExperience());
            etDetail.setText(Utils.plamber_post4+" "+electrician.getDetails());
            etCharge.setText(Utils.plamber_post5+" "+electrician.getCharge());
        }
        else if(type.equals("ambulance")){
            tvTitle.setText(Utils.ambulance_post6);
            etExperience.setText(Utils.ambulance_post3+" "+electrician.getExperience());
            etDetail.setText(Utils.ambulance_post4+" "+electrician.getDetails());
            etCharge.setText(Utils.ambulance_post5+" "+electrician.getCharge());
        }
        else if(type.equals("homemaid")){
            tvTitle.setText(Utils.homemaid_post6);
            etExperience.setText(Utils.homemaid_post3+" "+electrician.getExperience());
            etDetail.setText(Utils.homemaid_post4+" "+electrician.getDetails());
            etCharge.setText(Utils.homemaid_post5+" "+electrician.getCharge());
        }
        else if(type.equals("deliveryman")){
            tvTitle.setText(Utils.deliveryman_post6);
            etExperience.setText(Utils.deliveryman_post3+" "+electrician.getExperience());
            etDetail.setText(Utils.deliveryman_post4+" "+electrician.getDetails());
            etCharge.setText(Utils.deliveryman_post5+" "+electrician.getCharge());
        }
    }
}
