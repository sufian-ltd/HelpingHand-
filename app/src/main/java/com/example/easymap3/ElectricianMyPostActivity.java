package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricianMyPostActivity extends AppCompatActivity {

    ListView listView;
    List<Electrician> list;
    People people;
    String type;
    Button btnTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_my_post);
        people= (People) getIntent().getSerializableExtra("people");
        type=getIntent().getStringExtra("type");
        listView=findViewById(R.id.listView);
        btnTitle=findViewById(R.id.btnTitle);
        initCaption();
        getMyElectricianPost();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ElectricianMyPostActivity.this,EditElectricianMyPostActivity.class);
                intent.putExtra("electrician",list.get(i));
                intent.putExtra("people",people);
                finish();
                startActivity(intent);
            }
        });
    }

    private void getMyElectricianPost() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Electrician>> call=apiInterface.getMyElectricianPost(type,people.getId());
        call.enqueue(new Callback<List<Electrician>>() {
            @Override
            public void onResponse(Call<List<Electrician>> call, Response<List<Electrician>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Electrician>> call, Throwable t) {
                Toast.makeText(ElectricianMyPostActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setAdapter(){
//        String str[]=new String[list.size()];
//        for(int i=0;i<list.size();i++){
//            if(list.get(i).getRequestUserId().isEmpty())
//                str[i]="ID : "+list.get(i).getId()+ " Request : No Request";
//            else
//                str[i]="ID : "+list.get(i).getId()+ " Request : Yes(Click To See details)";
//        }
//        ArrayAdapter<String> adapter =new ArrayAdapter<String>(ElectricianMyPostActivity.this,
//                android.R.layout.simple_list_item_1,str);
//        listView.setAdapter(adapter);

        ServiceAdapter adapter=new ServiceAdapter(this,list,"my");
        listView.setAdapter(adapter);
    }

    private void initCaption() {
        if(type.equals("electrician")){
            btnTitle.setText(Utils.electrician_mypost1);
        }
        else if(type.equals("plumber")){
            btnTitle.setText(Utils.plamber_mypost1);
        }
        else if(type.equals("ambulance")){
            btnTitle.setText(Utils.ambulance_mypost1);
        }
        else if(type.equals("homemaid")){
            btnTitle.setText(Utils.homemaid_mypost1);
        }
        else if(type.equals("deliveryman")){
            btnTitle.setText(Utils.deliveryman_mypost1);
        }
    }
}
