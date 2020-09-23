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

public class MyRequestActivity extends AppCompatActivity {

    ListView listView;
    List<Electrician> list;
    List<Electrician> filteredList=new ArrayList<Electrician>();
    People people;
    String type;
    Button btnTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);
        people= (People) getIntent().getSerializableExtra("people");
        type=getIntent().getStringExtra("type");
        listView=findViewById(R.id.listView);
        btnTitle=findViewById(R.id.btnTitle);
        btnTitle.setText("My Request to ("+type+") post");
        getAllPostByType();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MyRequestActivity.this,MyRequestDetailActivity.class);
                intent.putExtra("electrician",filteredList.get(i));
                intent.putExtra("people",people);
                intent.putExtra("type",type);
                finish();
                startActivity(intent);
            }
        });
    }

    private void getAllPostByType() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Electrician>> call=apiInterface.getAllPostByType(type);
        call.enqueue(new Callback<List<Electrician>>() {
            @Override
            public void onResponse(Call<List<Electrician>> call, Response<List<Electrician>> response) {
                list=response.body();
                filterList(list);
            }

            @Override
            public void onFailure(Call<List<Electrician>> call, Throwable t) {
                Toast.makeText(MyRequestActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filterList(List<Electrician> list) {
        for(Electrician electrician:list){
            if(!electrician.getRequestUserId().equals("")) {
                if (!electrician.getRequestUserId().contains(",") && people.getId() == Integer.parseInt(electrician.getRequestUserId())) {
                    filteredList.add(electrician);
                } else {
                    String str[] = electrician.getRequestUserId().split(",");
                    for (String s : str) {
                        if (people.getId() == Integer.parseInt(s)) {
                            filteredList.add(electrician);
                            break;
                        }
                    }
                }
            }
        }
        setAdapter();
    }

    private void setAdapter(){
        ServiceAdapter adapter=new ServiceAdapter(this,filteredList,"acceptreq"+people.getId());
        listView.setAdapter(adapter);
    }
}
