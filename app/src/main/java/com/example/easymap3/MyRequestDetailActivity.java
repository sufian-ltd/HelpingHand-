package com.example.easymap3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRequestDetailActivity extends AppCompatActivity {

    EditText etAvailable,etExperience,etDetail,etCharge, etSubDiv;
    TextView tvTitle;
    Electrician electrician;
    People people;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_detail);
        people= (People) getIntent().getSerializableExtra("people");
        electrician= (Electrician) getIntent().getSerializableExtra("electrician");
        type=getIntent().getStringExtra("type");
        tvTitle=findViewById(R.id.tvTitle);
        etAvailable=findViewById(R.id.etAvailable);
        etExperience=findViewById(R.id.etExperience);
        etDetail=findViewById(R.id.etDetail);
        etCharge=findViewById(R.id.etCharge);
        etSubDiv=findViewById(R.id.etSubDiv);
        init();
        initCaption();
    }

    private void init() {
        etAvailable.setText("Available : "+electrician.getAvailable());
        etSubDiv.setText("Location : "+electrician.getSubDivision());
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
    public void removeRequest(View view) {
        String updateId="";
        if(electrician.getRequestUserId().contains(",")){
            String str[]=electrician.getRequestUserId().split(",");
            for(int i=0;i<str.length;i++){
                if(!str[i].equals(people.getId()+"")){
                    updateId+=str[i];
                    updateId+=",";
                }
            }
            updateId=updateId.substring(0,updateId.length()-1);
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.requestElectrician(electrician.getId(),updateId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(MyRequestDetailActivity.this, "The Request is Removed", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(message.equals("not update")){
                    Toast.makeText(MyRequestDetailActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(MyRequestDetailActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
