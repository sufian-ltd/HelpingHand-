package com.example.easymap3;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceAdapter extends ArrayAdapter<Electrician> {
    Context context;
    List<Electrician> list;
    String type="";
    public ServiceAdapter(Context context, List<Electrician> list ,String type) {
        super(context, R.layout.list_item, list);
        this.context = context;
        this.list = list;
        this.type=type;
    }

    class ViewHolder {
        TextView t1, t2,t3;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.t1 = convertView.findViewById(R.id.tvAvl);
            holder.t2 = convertView.findViewById(R.id.tvCh);
            holder.t3 = convertView.findViewById(R.id.tvReq);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t1.setText("Available Time : "+list.get(position).getAvailable().trim());
        holder.t2.setText("Service Charge : "+list.get(position).getCharge());
        if(type.equals("all")){
            holder.t3.setVisibility(View.GONE);
        }
        else if(type.equals("accept")){
            if(!list.get(position).getAcceptedId().equals("0"))
                holder.t3.setText("Request Accepted");
        }
        else if(type.equals("my")){
            if(list.get(position).getRequestUserId().equals(""))
                holder.t3.setText("No Request");
            else
                holder.t3.setText("Request Found");
        }
        else if(type.substring(0,9).equals("acceptreq") && type.substring(9,type.length()).equals(list.get(position).getAcceptedId())){
            holder.t3.setText("Request Accepted");
        }
        return convertView;
    }


}
