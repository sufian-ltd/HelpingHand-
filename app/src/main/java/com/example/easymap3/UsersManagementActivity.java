package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersManagementActivity extends AppCompatActivity {

    ListView listView;
    List<User> list;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_management);
        listView=findViewById(R.id.listView);
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        getUserList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user=list.get(i);
                Intent intent=new Intent(UsersManagementActivity.this,SecondMapsActivity.class);
                intent.putExtra("latitude",user.getLat());
                intent.putExtra("longitude",user.getLon());
                intent.putExtra("name",user.getEmail());
                intent.putExtra("share","no");
                intent.putExtra("peopleId",-1);
                startActivity(intent);
            }
        });
    }

    private void getUserList(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<User>> call=apiInterface.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(UsersManagementActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void setAdapter(){
        String str[]=new String[list.size()];
        //int count=0;
        for(int i=0;i<list.size();i++){
            //if(!email.equals(list.get(i).getEmail()) && !password.equals(list.get(i).getPassword())) {
                str[i] = "Username : " + list.get(i).getEmail();
                //count++;
            //}
        }
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(UsersManagementActivity.this,
                android.R.layout.simple_list_item_1,str);
        listView.setAdapter(adapter);
    }
}
