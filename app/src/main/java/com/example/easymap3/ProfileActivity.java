package com.example.easymap3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    EditText etId,etName,etEmail,etPassword,etContact,etAddress;
    People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etId=findViewById(R.id.etId);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        people=(People)getIntent().getSerializableExtra("people");
        initialize();
    }

    public void initialize(){
        etId.setText("User Id : "+people.getId());
        etName.setText(people.getName());
        etEmail.setText(people.getEmail());
        etPassword.setText(people.getPassword());
        etContact.setText(people.getContact());
        etAddress.setText(people.getAddress());
    }

    public void saveClick(View view) {
        String name=etName.getText().toString();
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        String contact=etContact.getText().toString();
        String address=etAddress.getText().toString();
        if(name.equals("") || email.equals("") || password.equals("") || contact.equals("") || address.equals("")){
            Toast.makeText(this, "Please Make Sure Your Information", Toast.LENGTH_LONG).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Please Enter a valid email", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length()<8 || password.length()>15){
            Toast.makeText(this, "Password should be at least 8 characters", Toast.LENGTH_LONG).show();
            return;
        }
        if(!Patterns.PHONE.matcher(contact).matches()){
            Toast.makeText(this, "Contact number should be valid (+8801751362112)", Toast.LENGTH_LONG).show();
            return;
        }
        if(address.length()<20 || address.length()>50){
            Toast.makeText(this, "Address should be at in 20-50 characters...", Toast.LENGTH_LONG).show();
            return;
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.editProfile(people.getId(),etName.getText().toString(),
                etEmail.getText().toString(),etPassword.getText().toString(),etContact.getText().toString(),etAddress.getText().toString());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(ProfileActivity.this, "Your Profile is update", Toast.LENGTH_LONG).show();
                }
                else if(message.equals("not update")){
                    Toast.makeText(ProfileActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
