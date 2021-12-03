package com.example.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private ArrayList<ContactsModel> list;
    private callerInterface contactCall;


    public ContactsAdapter(callerInterface contactCall, ArrayList<ContactsModel> list) {
        this.list = list;
        this.contactCall = contactCall;
    }
    @NonNull
    @Override
    public ContactsAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts, parent,false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ContactsViewHolder holder, int position) {
        holder.tv_contact_name.setText(list.get(position).getContactName());
        holder.tv_contact_phone.setText(list.get(position).getContactPhone());
        holder.btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCall.makeCall(holder.getAdapterPosition(), true);
            }
        });
        holder.btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactCall.makeCall(holder.getAdapterPosition(), false);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contact_name, tv_contact_phone;
        Button btn_call, btn_whatsapp;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_contact_name = itemView.findViewById(R.id.tv_item_contact_name);
            tv_contact_phone = itemView.findViewById(R.id.tv_item_contact_phone);
            btn_call = itemView.findViewById(R.id.btn_call);
            btn_whatsapp = itemView.findViewById(R.id.btn_whatsapp);
        }
    }
}
