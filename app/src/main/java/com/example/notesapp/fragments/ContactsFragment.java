package com.example.notesapp.fragments;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.ContactsAdapter;
import com.example.notesapp.ContactsModel;
import com.example.notesapp.NotesAdapter;
import com.example.notesapp.R;
import com.example.notesapp.callerInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;


public class ContactsFragment extends Fragment implements callerInterface {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private RecyclerView rvContacts;
    private ContactsAdapter adapter;
    private TextView tv_contact_name, tv_contact_phone;
    private Button btn_Phone, btn_Message;
    ArrayList<ContactsModel> contactList = new ArrayList<>();
    Activity activity;
    String name, phoneNumber;
    callerInterface contactCaller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReadContactsPermission();
        initContactsList();
    }

    @SuppressLint("Range")
    private void initContactsList() {
        Context context = getContext();
        Cursor cursor = Objects.requireNonNull(context).getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactList.add(new ContactsModel(name,phoneNumber));
        }
        cursor.close();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            Context context = getContext();
            rvContacts = view.findViewById(R.id.rv_contacts);
            adapter = new ContactsAdapter(this, contactList);
            rvContacts.setLayoutManager(new LinearLayoutManager(activity));
            rvContacts.setAdapter(adapter);
    }

    private void ReadContactsPermission() {
        int hasReadContactsPermission = checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS);
        int hasPhoneCallPermission = checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);
        if (hasReadContactsPermission != PackageManager.PERMISSION_GRANTED || hasPhoneCallPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    public interface callerInterface{
        void makeCall(int position, boolean call);
    }

    public void makeCall(int position, boolean isCall) {
        if (isCall) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contactList.get(position).getContactPhone()));
            activity.startActivity(callIntent);
        } else {
            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + contactList.get(position).getContactPhone().toString();
            whatsappIntent.setData(Uri.parse(url));
            activity.startActivity(whatsappIntent);
        }
    }



    private void initViews(View view) {
        tv_contact_name = view.findViewById(R.id.tv_item_contact_name);
        tv_contact_phone = view.findViewById(R.id.tv_item_contact_phone);
        btn_Phone = view.findViewById(R.id.btn_call);
        btn_Message = view.findViewById(R.id.btn_whatsapp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        initViews(view);
        initContactsRecyler(view);
        return view;
    }

    private void initContactsRecyler(View view) {
        rvContacts = view.findViewById(R.id.rv_contacts);
        rvContacts.setAdapter(adapter);
    }
}