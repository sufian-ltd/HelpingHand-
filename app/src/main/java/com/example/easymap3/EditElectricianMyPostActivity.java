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

public class EditElectricianMyPostActivity extends AppCompatActivity {

    Electrician electrician;
    People people;

    EditText etAvailable,etLoc,etExperience,etDetail,etCharge;
    TimePicker tpFirstTime,tpSecondTime;
    Spinner spnSubDiv,spnExperience;
    TextView tvTitle;
    Double latitude=0.0,longitude=0.0;
    String firstTime="",secondTime="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_electrician_my_post_acitvity);
        electrician= (Electrician) getIntent().getSerializableExtra("electrician");
        people=(People) getIntent().getSerializableExtra("people");
        etExperience=findViewById(R.id.etExperience);
        etDetail=findViewById(R.id.etDetail);
        etCharge=findViewById(R.id.etCharge);
        spnExperience=findViewById(R.id.spnExperience);
        etLoc=findViewById(R.id.etLoc);
        etAvailable=findViewById(R.id.etAvailable);
        spnSubDiv=findViewById(R.id.spnSubDiv);
        tvTitle=findViewById(R.id.tvTitle);
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
                secondTime=""+i;
            }
        });
        init();
        initCaption();
    }

    public void savePost(View view) {
        if(spnExperience.getSelectedItem().toString().equals("") || etDetail.getText().toString().equals("")
                || etCharge.getText().toString().equals("")){
            Toast.makeText(this, "Please Fill All", Toast.LENGTH_LONG).show();
            return;
        }
        if(etCharge.getText().toString().length()>5){
            Toast.makeText(this, "Amount Limit Exceed...!!!", Toast.LENGTH_LONG).show();
            return;
        }
        String loc=spnSubDiv.getSelectedItem().toString().equals("Select Location")?electrician.getSubDivision():spnSubDiv.getSelectedItem().toString();
        String experience=spnExperience.getSelectedItem().toString().equals("Select Experience")?
                electrician.getExperience():spnExperience.getSelectedItem().toString();
        String avl=(firstTime.equals("") && secondTime.equals(""))?electrician.getAvailable():(firstTime+".00 - "+secondTime+".00");
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.updateElectricianPost(electrician.getId(),avl,experience
                ,etDetail.getText().toString(),etCharge.getText().toString(),"Chittagong",loc,
                latitude,longitude,electrician.getUserId(),electrician.getRequestUserId());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(EditElectricianMyPostActivity.this, "Your Post is update", Toast.LENGTH_LONG).show();
                }
                else if(message.equals("not update")){
                    Toast.makeText(EditElectricianMyPostActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(EditElectricianMyPostActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void shareLocation(View view) {
        Intent intent=new Intent(EditElectricianMyPostActivity.this,SecondMapsActivity.class);
        intent.putExtra("share","yes");
        intent.putExtra("peopleId",people.getId());
        startActivity(intent);
    }

    public void deletePost(View view) {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.deleteElectricianPost(electrician.getId());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("delete")){
                    Toast.makeText(EditElectricianMyPostActivity.this, "deleted", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(EditElectricianMyPostActivity.this, "Please check internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        String temp[]=electrician.getAvailable().split("-");
        firstTime=temp[0].trim();
        secondTime=temp[1].trim();
        etAvailable.setText("Available Time : "+electrician.getAvailable());
        etExperience.setText("Experience:"+electrician.getExperience());
        etCharge.setText(electrician.getCharge());
        etDetail.setText(electrician.getDetails());
        etLoc.setText("Location : "+electrician.getSubDivision());
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Utils.location);
        spnSubDiv.setAdapter(adapter);
        String str2[]={
                "Select Experience","1 Years","2 Years","3 Years","4 Years","5 Years","More Than 5 Years"
        };
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,str2);
        spnExperience.setAdapter(adapter2);
        int d=0;
        for(String str:str2){
            if(str.equals(electrician.getExperience())){
                spnExperience.setSelection(d);
                break;
            }
            ++d;
        }
        int c=0;
        for (String str:Utils.location){
            if(str.equals(electrician.getSubDivision())){
                spnSubDiv.setSelection(c);
                break;
            }
            ++c;
        }
    }

    public void viewRequest(View view) {
        Intent intent=new Intent(EditElectricianMyPostActivity.this,ElectricianRequestActivity.class);
        intent.putExtra("electrician",electrician);
        startActivity(intent);
    }

    private void initCaption() {
        String type=electrician.getType();
        if(type.equals("electrician")){
            tvTitle.setText(Utils.electrician_editpost1);
//            etExperience.setHint(Utils.electrician_post3);
            etDetail.setHint(Utils.electrician_post4);
            etCharge.setHint(Utils.electrician_post5);
        }
        else if(type.equals("plumber")){
            tvTitle.setText(Utils.plamber_editpost1);
//            etExperience.setHint(Utils.plamber_post3);
            etDetail.setHint(Utils.plamber_post4);
            etCharge.setHint(Utils.plamber_post5);
        }
        else if(type.equals("ambulance")){
            tvTitle.setText(Utils.ambulance_editpost1);
//            etExperience.setHint(Utils.ambulance_post3);
            etDetail.setHint(Utils.ambulance_post4);
            etCharge.setHint(Utils.ambulance_post5);
        }
        else if(type.equals("homemaid")){
            tvTitle.setText(Utils.homemaid_editpost1);
//            etExperience.setHint(Utils.homemaid_post3);
            etDetail.setHint(Utils.homemaid_post4);
            etCharge.setHint(Utils.homemaid_post5);
        }
        else if(type.equals("deliveryman")){
            tvTitle.setText(Utils.deliveryman_editpost1);
//            etExperience.setHint(Utils.deliveryman_post3);
            etDetail.setHint(Utils.deliveryman_post4);
            etCharge.setHint(Utils.deliveryman_post5);
        }
    }
}
