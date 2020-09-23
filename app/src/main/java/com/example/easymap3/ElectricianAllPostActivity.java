package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricianAllPostActivity extends AppCompatActivity {

    ListView listView;
    List<Electrician> list;
    Spinner spnSubDiv;
    People people;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_all_post);
        spnSubDiv=findViewById(R.id.spnSubDiv);
        people= (People) getIntent().getSerializableExtra("people");
        type=getIntent().getStringExtra("type");
        listView=findViewById(R.id.listView);
        init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ElectricianAllPostActivity.this,ElectricianPostActivity.class);
                intent.putExtra("electrician",list.get(i));
                intent.putExtra("people",people);
                finish();
                startActivity(intent);
            }
        });
        spnSubDiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spnSubDiv.getSelectedItem().toString().equals("Select Location"))
                    getAllElectricianPost();
                else
                    getAllElectricianPostBySubDiv();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAllElectricianPost() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Electrician>> call=apiInterface.getAllElectricianPost(type,people.getId());
        call.enqueue(new Callback<List<Electrician>>() {
            @Override
            public void onResponse(Call<List<Electrician>> call, Response<List<Electrician>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Electrician>> call, Throwable t) {
                Toast.makeText(ElectricianAllPostActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAllElectricianPostBySubDiv() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Electrician>> call=apiInterface.getAllElectricianPostBySubDiv(type,spnSubDiv.getSelectedItem().toString(),people.getId());
        call.enqueue(new Callback<List<Electrician>>() {
            @Override
            public void onResponse(Call<List<Electrician>> call, Response<List<Electrician>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Electrician>> call, Throwable t) {
                Toast.makeText(ElectricianAllPostActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setAdapter(){
//        String str[]=new String[list.size()];
//        for(int i=0;i<list.size();i++){
//            str[i]="Available:"+list.get(i).getAvailable()+" Charge:"+list.get(i).getCharge()+" Loc:"+list.get(i).getSubDivision();
//        }
//        ArrayAdapter<String> adapter =new ArrayAdapter<String>(ElectricianAllPostActivity.this,
//                android.R.layout.simple_list_item_1,str);
//        listView.setAdapter(adapter);

        ServiceAdapter adapter=new ServiceAdapter(this,list,"all");
        listView.setAdapter(adapter);
    }

    private void init() {
        String str[]= Utils.location;
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,str);
        spnSubDiv.setAdapter(adapter);
    }
}
