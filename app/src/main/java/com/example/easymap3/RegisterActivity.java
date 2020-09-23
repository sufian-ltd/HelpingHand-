package com.example.easymap3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    CheckBox chYes;
    EditText etEmail,etPassword,etQues;
    Spinner spnQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        chYes=findViewById(R.id.chYes);
        etQues=findViewById(R.id.etQues);
        spnQues=findViewById(R.id.spnQues);
        initSpinner();
    }

    public void registerClick(View view) {
        if(etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("") || !chYes.isChecked())
            return;
        if(spnQues.getSelectedItem().toString().equals("Select Password Recovery Question") || etQues.getText().toString().equals("")){
            Toast.makeText(this, "Please confirm password recovery option..!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(etPassword.getText().toString().length()<8 || etPassword.getText().toString().length()>15){
            Toast.makeText(this, "Password should be at least 8 characters", Toast.LENGTH_LONG).show();
            return;
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.saveUser(etEmail.getText().toString(),etPassword.getText().toString()
                ,0.0,0.0,"none",spnQues.getSelectedItem().toString(), etQues.getText().toString());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("exist")){
                    Toast.makeText(RegisterActivity.this, "This user is already exist", Toast.LENGTH_LONG).show();
                }
                else if(message.equals("inserted")){
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Connection Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initSpinner(){
        String str[]={"Select Recovery Option","Your elder brother name?","Your favorite color?",
                "Your first school name?","Your cousin brother name?","Your college roll number?",
                "Your birth month?","Your favorite food?","Your birth place?","Your best friend?","Your favorite place?"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,str);
        spnQues.setAdapter(adapter);
    }
}
