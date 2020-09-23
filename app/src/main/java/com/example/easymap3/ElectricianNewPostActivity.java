package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricianNewPostActivity extends AppCompatActivity {

    EditText etDetail,etCharge;
    Spinner spnSubDiv,spnFirstTime,spnSecondTime,spnExperience;
    TextView tvTitle;
    TimePicker tpFirstTime,tpSecondTime;

    Double latitude=0.0,longitude=0.0;
    String firstTime="",secondTime="";

    People people;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_new_post);
        people= (People) getIntent().getSerializableExtra("people");
        type=getIntent().getStringExtra("type");
        tvTitle=findViewById(R.id.tvTitle);
//        etExperience=findViewById(R.id.etExperience);
        etDetail=findViewById(R.id.etDetail);
        etCharge=findViewById(R.id.etCharge);
        spnSubDiv=findViewById(R.id.spnSubDiv);
        spnExperience=findViewById(R.id.spnExperience);
        tpFirstTime=findViewById(R.id.tpFirstTime);
        tpSecondTime=findViewById(R.id.tpSecondTime);
        tpFirstTime.setIs24HourView(true);
        tpSecondTime.setIs24HourView(true);
        tpFirstTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                firstTime=i+"";
            }
        });
        tpSecondTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                secondTime=i+"";
            }
        });
//        spnFirstTime=findViewById(R.id.spnFirstTime);
//        spnSecondTime=findViewById(R.id.spnSecondTime);
        init();
        initCaption();
    }

    private void init() {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Utils.location);
        spnSubDiv.setAdapter(adapter);

        String str2[]={
                "1 Years","2 Years","3 Years","4 Years","5 Years","More Than 5 Years"
        };
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,str2);
        spnExperience.setAdapter(adapter2);
    }

    public void savePost(View view) {
        if(etDetail.getText().toString().equals("")
                || etCharge.getText().toString().equals("") || spnSubDiv.getSelectedItem().toString().equals("Select Location")){
            Toast.makeText(this, "Please Fill All", Toast.LENGTH_LONG).show();
            return;
        }
        if(firstTime.equals("") || secondTime.equals("")){
            Toast.makeText(this, "Please select time", Toast.LENGTH_LONG).show();
            return;
        }
        if(etCharge.getText().toString().length()>5){
            Toast.makeText(this, "Amount Limit Exceed...!!!", Toast.LENGTH_LONG).show();
            return;
        }
        String avl=firstTime+".00 - "+secondTime+".00";
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.saveElectricianPost(type,avl,spnExperience.getSelectedItem().toString()
                ,etDetail.getText().toString(),etCharge.getText().toString(),"Chittagong",spnSubDiv.getSelectedItem().toString(),
                latitude,longitude,people.getId(),"");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("inserted")){
                    Toast.makeText(ElectricianNewPostActivity.this, "Post Added Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(ElectricianNewPostActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(ElectricianNewPostActivity.this, "Internal Server Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void shareLocation(View view) {
        Intent intent=new Intent(ElectricianNewPostActivity.this,SecondMapsActivity.class);
        intent.putExtra("share","yes");
        intent.putExtra("peopleId",people.getId());
        startActivity(intent);
    }

    private void initCaption() {
        if(type.equals("electrician")){
            tvTitle.setText(Utils.electrician_post1);
//            etExperience.setHint(Utils.electrician_post3);
            etDetail.setHint(Utils.electrician_post4);
            etCharge.setHint(Utils.electrician_post5);
        }
        else if(type.equals("plumber")){
            tvTitle.setText(Utils.plamber_post1);
//            etExperience.setHint(Utils.plamber_post3);
            etDetail.setHint(Utils.plamber_post4);
            etCharge.setHint(Utils.plamber_post5);
        }
        else if(type.equals("ambulance")){
            tvTitle.setText(Utils.ambulance_post1);
//            etExperience.setHint(Utils.ambulance_post3);
            etDetail.setHint(Utils.ambulance_post4);
            etCharge.setHint(Utils.ambulance_post5);
        }
        else if(type.equals("homemaid")){
            tvTitle.setText(Utils.homemaid_post1);
//            etExperience.setHint(Utils.homemaid_post3);
            etDetail.setHint(Utils.homemaid_post4);
            etCharge.setHint(Utils.homemaid_post5);
        }
        else if(type.equals("deliveryman")){
            tvTitle.setText(Utils.deliveryman_post1);
//            etExperience.setHint(Utils.deliveryman_post3);
            etDetail.setHint(Utils.deliveryman_post4);
            etCharge.setHint(Utils.deliveryman_post5);
        }
    }
}
