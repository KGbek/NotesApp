package com.example.notesapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.LongDef;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;


public class AddNotesFragment extends Fragment {

    private EditText etTitle, etDescription;
    private Button btnSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initViews(View view) {
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_description);
        btnSave = view.findViewById(R.id.btn_save);
    }

    private void checkIsEdit() {
        Log.d("Edit note", "Method checkIsEdit");
        if (getArguments() != null){
            if (getArguments().getBoolean("isEdit", false)){
                etTitle.setText(getArguments().getString("title"));
                etDescription.setText(getArguments().getString("description"));
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = etTitle.getText().toString().trim();
                        String description = etDescription.getText().toString().toString();
                        Bundle editNoteBundle = new Bundle();
                        editNoteBundle.putString("title", title);
                        editNoteBundle.putString("description", description);
                        editNoteBundle.putInt("pos",getArguments().getInt("pos",0));
                        getActivity().getSupportFragmentManager().setFragmentResult("edit", editNoteBundle);
                        getActivity().getSupportFragmentManager().popBackStack();

                    }
                });
            }
        } else {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = etTitle.getText().toString().trim();
                    String description = etDescription.getText().toString().trim();
                    Bundle createNoteBundle = new Bundle();
                    createNoteBundle.putString("title", title);
                    createNoteBundle.putString("description", description);
                    getActivity().getSupportFragmentManager().setFragmentResult("note", createNoteBundle);
                    getActivity().getSupportFragmentManager().popBackStack();

                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_notes, container, false);
        initViews(view);
        checkIsEdit();
        return view;
    }
}