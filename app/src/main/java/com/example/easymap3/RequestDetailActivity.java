package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDetailActivity extends AppCompatActivity {

    EditText etName,etEmail,etContact,etAddress;
    People people;
    Electrician electrician;
    String updateId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        people=(People)getIntent().getSerializableExtra("people");
        electrician=(Electrician)getIntent().getSerializableExtra("electrician");
        init();
    }

    private void init(){
        etName.setText("Name : "+people.getName());
        etEmail.setText("Email : "+people.getEmail());
        etContact.setText("Contact : "+people.getContact());
        etAddress.setText("Address : "+people.getAddress());
        etName.setEnabled(false);
        etEmail.setEnabled(false);
        etContact.setEnabled(false);
        etAddress.setEnabled(false);
    }
    public void deleteClick(View view) {
        updateId="";
        removeRequest();
    }

    private void removeRequest(){
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
                    Toast.makeText(RequestDetailActivity.this, "The Request is Removed", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(message.equals("not update")){
                    Toast.makeText(RequestDetailActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(RequestDetailActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewLocationClick(View view) {
        Intent intent=new Intent(RequestDetailActivity.this,SecondMapsActivity.class);
        intent.putExtra("latitude",people.getLatitude());
        intent.putExtra("longitude",people.getLongitude());
        intent.putExtra("name",people.getName());
        intent.putExtra("share","no");
        intent.putExtra("peopleId",-1);
        startActivity(intent);
    }

    public void acceptClick(View view) {
        //removeRequest();
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.acceptRequest(electrician.getId(),people.getId()+"");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(RequestDetailActivity.this, "The Request is Accepted", Toast.LENGTH_LONG).show();
                    finish();
                }
                else if(message.equals("not update")){
                    Toast.makeText(RequestDetailActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(RequestDetailActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
