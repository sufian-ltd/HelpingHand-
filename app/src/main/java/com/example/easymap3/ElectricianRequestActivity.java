package com.example.easymap3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricianRequestActivity extends AppCompatActivity {

    Electrician electrician;
    ListView listView;
    List<People> list;
    List<People> filterList=new ArrayList<People>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_request);
        electrician=(Electrician) getIntent().getSerializableExtra("electrician");
        listView=findViewById(R.id.listView);
        getAllPeople();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ElectricianRequestActivity.this,RequestDetailActivity.class);
                intent.putExtra("people",filterList.get(i));
                intent.putExtra("electrician",electrician);
                startActivity(intent);
            }
        });

    }
    private void getAllPeople(){
        if(electrician.getRequestUserId().equals("")){
            Toast.makeText(this, "No Request in your post", Toast.LENGTH_LONG).show();
            return;
        }
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<People>> call = apiInterface.getAllPeople();
        call.enqueue(new Callback<List<People>>() {
            @Override
            public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                list = response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<People>> call, Throwable t) {
                Toast.makeText(ElectricianRequestActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getPeopleById(){
        if(electrician.getRequestUserId().equals("")){
            Toast.makeText(this, "No Request in your post", Toast.LENGTH_LONG).show();
            return;
        }
        if(!electrician.getRequestUserId().contains(",")) {
            int id=Integer.parseInt(electrician.getRequestUserId());
            ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            Call<List<People>> call = apiInterface.getPeopleById(id);
            call.enqueue(new Callback<List<People>>() {
                @Override
                public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                    People people = response.body().get(0);
                    list.add(people);
                }

                @Override
                public void onFailure(Call<List<People>> call, Throwable t) {
                    Toast.makeText(ElectricianRequestActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(electrician.getRequestUserId().contains(",")){
            String str[] = electrician.getRequestUserId().split(",");
            for (String s:str) {
                int id=Integer.parseInt(s);
                ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                Call<List<People>> call = apiInterface.getPeopleById(id);
                call.enqueue(new Callback<List<People>>() {
                    @Override
                    public void onResponse(Call<List<People>> call, Response<List<People>> response) {
                        People people = response.body().get(0);
                        list.add(people);
                    }

                    @Override
                    public void onFailure(Call<List<People>> call, Throwable t) {
                        Toast.makeText(ElectricianRequestActivity.this, "Connection failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        Toast.makeText(this, "size = "+list.size(), Toast.LENGTH_LONG).show();
        setAdapter();
    }

    public void setAdapter(){
        if(!electrician.getRequestUserId().contains(",")) {
            String str[]=new String[1];
            for(int i=0;i<list.size();i++){
                if(list.get(i).getId()==Integer.parseInt(electrician.getRequestUserId())){
                    filterList.add(list.get(i));
//                    str[0]="Name:"+filterList.get(0).getName()+" Email:"+filterList.get(0).getEmail()+" Contact:"+filterList.get(0).getContact()+
//                            " Address:"+filterList.get(0).getAddress();
//                    ArrayAdapter<String> adapter =new ArrayAdapter<String>(ElectricianRequestActivity.this,
//                            android.R.layout.simple_list_item_1,str);
//                    listView.setAdapter(adapter);
                    PeopleAdapter peopleAdapter=new PeopleAdapter(this,filterList);
                    listView.setAdapter(peopleAdapter);
                    break;
                }
            }
        }
        else if(electrician.getRequestUserId().contains(",")){
            String id[]=electrician.getRequestUserId().split(",");
//            String str[]=new String[id.length];
//            int count=0;
            for(int j=0;j<list.size();j++) {
                for (int i = 0; i < id.length; i++) {
                    if(list.get(j).getId()==Integer.parseInt(id[i])){
                        filterList.add(list.get(j));
//                        str[count]="Name: "+list.get(j).getName()+"  Email: "+list.get(j).getEmail()+" Contact:"+list.get(j).getContact()+
//                                " Address:"+list.get(j).getAddress();
//                        ++count;
                    }

                }
            }
            PeopleAdapter peopleAdapter=new PeopleAdapter(this,filterList);
            listView.setAdapter(peopleAdapter);
//            ArrayAdapter<String> adapter =new ArrayAdapter<String>(ElectricianRequestActivity.this,
//                    android.R.layout.simple_list_item_1,str);
//            listView.setAdapter(adapter);
        }

    }

//    private void initRequestPeople(){
//        etName.setText("Name : " + people.getName());
//        etEmail.setText("Email : " + people.getEmail());
//        etContact.setText("Contact : " + people.getContact());
//        etAddress.setText("Address : " + people.getAddress());
//    }
}
