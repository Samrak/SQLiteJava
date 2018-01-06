package com.samrak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.samrak.model.EntityPerson;
import com.samrak.sqlitejava.R;

import java.util.ArrayList;


/**
 * Created by Samet OZTOPRAK on 25.4.2016.
 */
public class CustomPersonAdaptor extends BaseAdapter {
    private ArrayList<EntityPerson> listData;
    private LayoutInflater layoutInflater;

    public CustomPersonAdaptor(Context aContext, ArrayList<EntityPerson> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_subscriber_layout, null);
            holder = new ViewHolder();
            holder.txtId = (TextView) convertView.findViewById(R.id.txtId);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtData = (TextView) convertView.findViewById(R.id.txtData);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtId.setText(String.valueOf(listData.get(position).getKeyId()));
        holder.txtName.setText(listData.get(position).getKeyName());
        holder.txtData.setText(listData.get(position).getKeySurname());
        return convertView;
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtName;
        TextView txtData;
    }
}
