package com.example.easymap3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeServiceRegisterActivity extends AppCompatActivity {

    EditText etName,etEmail,etPassword,etContact,etNID,etAddress,etQues;
    Spinner spnQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_service_register);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etContact=findViewById(R.id.etContact);
        etNID=findViewById(R.id.etNID);
        etAddress=findViewById(R.id.etAddress);
        etQues=findViewById(R.id.etQues);
        spnQues=findViewById(R.id.spnQues);
        initSpinner();
    }

    public void registerClick(View view) {

        if(etName.getText().toString().equals("") || etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("") ||
                etContact.getText().toString().equals("") || etAddress.getText().toString().equals("") || etNID.getText().toString().equals(""))
            return;
        if(spnQues.getSelectedItem().toString().equals("Select Password Recovery Question") || etQues.getText().toString().equals("")){
            Toast.makeText(this, "Please confirm password recovery option..!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String email=etEmail.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Please Enter a valid email", Toast.LENGTH_LONG).show();
            return;
        }
        if(etPassword.getText().toString().length()<8 || etPassword.getText().toString().length()>15){
            Toast.makeText(this, "Password should be at least 8 characters", Toast.LENGTH_LONG).show();
            return;
        }
        if(!Patterns.PHONE.matcher(etContact.getText().toString()).matches() || etContact.getText().toString().length()!=11){
            Toast.makeText(this, "Contact number should be valid (+8801751362112)", Toast.LENGTH_LONG).show();
            return;
        }
        if(etAddress.getText().toString().length()<20 || etAddress.getText().toString().length()>50){
            Toast.makeText(this, "Address should be at in 20-50 characters...", Toast.LENGTH_LONG).show();
            return;
        }
        if(etNID.getText().toString().length()==10 || etNID.getText().toString().length()==13) {
            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<ServerResponse> call = apiInterface.savePeople(etName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etContact.getText().toString(),etNID.getText().toString(), etAddress.getText().toString(), spnQues.getSelectedItem().toString(), etQues.getText().toString());
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    ServerResponse serverResponse = response.body();
                    String message = serverResponse.getResponse();
                    if (message.equals("exist")) {
                        Toast.makeText(HomeServiceRegisterActivity.this, "This user is already exist", Toast.LENGTH_LONG).show();
                    } else if (message.equals("inserted")) {
                        Toast.makeText(HomeServiceRegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        finish();
                    } else if (message.equals("not inserted")) {
                        Toast.makeText(HomeServiceRegisterActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(HomeServiceRegisterActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
        else
            Toast.makeText(this, "NID Should be at 13 digits and Smart Card ID 10 digits", Toast.LENGTH_LONG).show();
    }
    private void initSpinner(){
        String str[]={"Select Recovery Option","Your elder brother name?","Your favorite color?",
                "Your first school name?","Your cousin brother name?","Your college roll number?",
                "Your birth month?","Your favorite food?","Your birth place?","Your best friend?","Your favorite place?"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,str);
        spnQues.setAdapter(adapter);
    }
}
