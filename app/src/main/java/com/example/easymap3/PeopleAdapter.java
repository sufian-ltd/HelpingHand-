package com.example.easymap3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PeopleAdapter extends ArrayAdapter<People> {

    Context context;
    List<People> list;
    public PeopleAdapter(Context context, List<People> list ) {
        super(context, R.layout.people_list_item, list);
        this.context = context;
        this.list = list;
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
            convertView = inflater.inflate(R.layout.people_list_item, parent, false);
            holder = new ViewHolder();
            holder.t1 = convertView.findViewById(R.id.tvName);
            holder.t2 = convertView.findViewById(R.id.tvContact);
            holder.t3 = convertView.findViewById(R.id.tvaddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t1.setText(list.get(position).getName());
        holder.t2.setText("Cell:"+list.get(position).getContact());
        holder.t3.setText("Address : "+list.get(position).getAddress());
        return convertView;
    }
}
