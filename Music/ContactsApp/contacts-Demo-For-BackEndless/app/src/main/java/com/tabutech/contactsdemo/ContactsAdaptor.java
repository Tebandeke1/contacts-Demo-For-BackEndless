package com.tabutech.contactsdemo;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

public class ContactsAdaptor  extends ArrayAdapter<Contact> {

    private Context context;
    private List<Contact> contact;

    public ContactsAdaptor(Context context1, List<Contact> contactList){
        super(context1,R.layout.row_layout,contactList);

        context = context1;
        contact = contactList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.row_layout,parent,false);

        TextView tvChar = convertView.findViewById(R.id.tvChar);
        TextView tvName = convertView.findViewById(R.id.tvName3);
        TextView tvNumber = convertView.findViewById(R.id.tvNumber3);

        tvChar.setText(contact.get(position).getName().toUpperCase().charAt(0)+"");
        tvName.setText(contact.get(position).getName());
        tvNumber.setText(contact.get(position).getNumber());

        return convertView;
    }

}
