package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAcceptedUserActivity extends AppCompatActivity {

    ListView listView;
    List<Electrician> list;
    People people;
    String type;
    Button btnTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accepted_user);
        people= (People) getIntent().getSerializableExtra("people");
        type=getIntent().getStringExtra("type");
        listView=findViewById(R.id.listView);
        btnTitle=findViewById(R.id.btnTitle);
        btnTitle.setText("My Accepted User to ("+type+") post");
        getAllAcceptedPost();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MyAcceptedUserActivity.this,MyAcceptedUserProfileActivity.class);
                intent.putExtra("peopleId",list.get(i).getAcceptedId());
                startActivity(intent);
            }
        });
    }

    private void getAllAcceptedPost() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Electrician>> call=apiInterface.getAllAcceptedPost(type,people.getId());
        call.enqueue(new Callback<List<Electrician>>() {
            @Override
            public void onResponse(Call<List<Electrician>> call, Response<List<Electrician>> response) {
                list=response.body();
                if(list.size()==0){
                    Toast.makeText(MyAcceptedUserActivity.this, "No Accepted Post", Toast.LENGTH_SHORT).show();
                    return;
                }
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Electrician>> call, Throwable t) {
                Toast.makeText(MyAcceptedUserActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setAdapter(){
        ServiceAdapter adapter=new ServiceAdapter(this,list,"accept");
        listView.setAdapter(adapter);
    }
}
