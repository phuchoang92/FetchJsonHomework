package com.example.fetchjsonhomework;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private Context context;
    private List<PersonInfo> personInfoList;

    public ContactAdapter(Context context, List<PersonInfo> personInfoList) {
        this.context = context;
        this.personInfoList = personInfoList;
    }

    @Override
    public int getCount() {
        return personInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return personInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_item, null);
            viewHolder = new PersonViewHolder();
            viewHolder.iconAlphabet = convertView.findViewById(R.id.icon_person);
            viewHolder.nameTitle = convertView.findViewById(R.id.nameTitle);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (PersonViewHolder) convertView.getTag();

        PersonInfo item = personInfoList.get(position);

        viewHolder.iconAlphabet.setText(String.valueOf(item.getName().charAt(0)));
        viewHolder.nameTitle.setText(item.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ContactDetailActivity.class);
                i.putExtra("name",item.getName() );
                i.putExtra("phone", item.getPhoneNumber());
                i.putExtra("email", item.getEmail());
                context.startActivity(i);
            }
        });

        return convertView;
    }

    static class PersonViewHolder{
        TextView iconAlphabet;
        TextView nameTitle;
    }
}
