package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAcceptedUserProfileActivity extends AppCompatActivity {

    EditText etName,etEmail,etContact,etAddress;
    String peopleId;
    People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accepted_user_profile);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        peopleId=getIntent().getStringExtra("peopleId");
        getPeopleId();
    }

    private void getPeopleId() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<People>> call=apiInterface.getPeopleById(Integer.parseInt(peopleId));
        call.enqueue(new Callback<List<People>>() {
            @Override
            public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                people=response.body().get(0);
                init();
            }

            @Override
            public void onFailure(Call<List<People>> call, Throwable t) {
                Toast.makeText(MyAcceptedUserProfileActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init(){
        etName.setText("Name : "+people.getName());
        etEmail.setText("Email : "+people.getEmail());
        etContact.setText("Contact : "+people.getContact());
        etAddress.setText("Address : "+people.getAddress());
    }

    public void viewLocationClick(View view) {
        Intent intent=new Intent(MyAcceptedUserProfileActivity.this,SecondMapsActivity.class);
        intent.putExtra("latitude",people.getLatitude());
        intent.putExtra("longitude",people.getLongitude());
        intent.putExtra("name",people.getName());
        intent.putExtra("share","no");
        intent.putExtra("peopleId",-1);
        startActivity(intent);
    }
}
