package com.example.easymap3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tvRegister,tvForgotPassword;
    EditText etEmail,etPassword;
    SharedPreferences sharedPreferences;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvRegister=findViewById(R.id.tvRegister);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        tvForgotPassword=findViewById(R.id.tvForgotPassword);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFirstDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("email"))
            etEmail.setText(sharedPreferences.getString("userEmail",""));
        if(sharedPreferences.contains("password"))
            etPassword.setText(sharedPreferences.getString("userPassword",""));
    }

    private void showForgotPassDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(user.getQuestion());
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editTextAns=new EditText(this);
        editTextAns.setHint("Your Answer");
        editTextAns.setWidth(950);
        final Button button=new Button(this);
        button.setText("Check Answer");
        button.setWidth(950);
        final EditText editTextPass=new EditText(this);
        editTextPass.setEnabled(false);
        editTextPass.setHint("New Password");
        editTextPass.setWidth(950);
        linearLayout.addView(editTextAns);
        linearLayout.addView(button);
        linearLayout.addView(editTextPass);
        linearLayout.setPadding(10,10,10,10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextAns.getText().toString().trim().equalsIgnoreCase(user.getAnswer().trim())){
                    editTextPass.setEnabled(true);
                }
            }
        });
        builder.setView(linearLayout);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editTextPass.getText().toString().isEmpty())
                    recovery(user.getEmail(),editTextPass.getText().toString());
                else
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void showFirstDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Password Recovery");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText editTextEmail=new EditText(this);
        editTextEmail.setHint("Your Username");
        editTextEmail.setWidth(950);
        linearLayout.addView(editTextEmail);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editTextEmail.getText().toString().isEmpty())
                    getQuestionAndAnswer(editTextEmail.getText().toString());
                else
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void recovery(String email,String newPassword) {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.updateUserPassword(email,newPassword);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(LoginActivity.this, "Your Password is update", Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("not update")){
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

//        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(LoginActivity.this, "Main Sent To Your Account", Toast.LENGTH_SHORT).show();
//                }
//            }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void getQuestionAndAnswer(String email){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<User>> call=apiInterface.getUserByEmail(email);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.body()!=null) {
                    user = response.body().get(0);
                    showForgotPassDialog();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginClick(View view) {
        final String email=etEmail.getText().toString();
        final String password=etPassword.getText().toString();
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.isUser(email,password);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                if(serverResponse.getResponse().equals("exist")){
                    Toast.makeText(LoginActivity.this, "Login Successful",
                            Toast.LENGTH_SHORT).show();
                    setLoginData(email,password);
                    Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);
                    startActivity(intent);
                }
                else if(serverResponse.getResponse().equals("not exist")){
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void skipClick(View view) {
        Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
        intent.putExtra("email","none");
        intent.putExtra("password","none");
        startActivity(intent);
    }

    private void setLoginData(String email,String password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userEmail",email);
        editor.putString("userPassword",password);
        editor.commit();
        etEmail.setText("");
        etPassword.setText("");
    }
}
