package com.example.notesapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesapp.NotesAdapter;
import com.example.notesapp.NotesModel;
import com.example.notesapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NotesFragment extends Fragment implements NotesAdapter.onClick {

    private RecyclerView rvNotes;
    private NotesAdapter adapter;
    private FloatingActionButton btnOpenAddActivity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NotesAdapter(getContext(), this);
    }

    private void initViews(View view) {
        btnOpenAddActivity = view.findViewById(R.id.btn_open_add_activity);
        btnOpenAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new AddNotesFragment()).addToBackStack("AddNotesFragment").commit();
            }
        });
    }

    private void initNotesRecycler(View view) {
        rvNotes = view.findViewById(R.id.rv_notes);
        rvNotes.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        initViews(view);
        initNotesRecycler(view);
        listenNote();
        return view;
    }

    private void listenNote() {
        getActivity().getSupportFragmentManager().setFragmentResultListener("note", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NotesModel model = new NotesModel(result.getString("title"), result.getString("description"), "");
                adapter.addNote(model);
            }
        });
        getActivity().getSupportFragmentManager().setFragmentResultListener("edit", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NotesModel model = new NotesModel(result.getString("title"), result.getString("description"), "");
                adapter.editNote(model, result.getInt("pos"));
            }
        });
    }

    @Override
    public void click(int position) {
        Log.d("Edit note", "Crash");
        String title = adapter.getList().get(position).getTitle();
        String description = adapter.getList().get(position).getDescription();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putBoolean("isEdit", true);
        bundle.putInt("pos", position);
        AddNotesFragment addFragment = new AddNotesFragment();
        addFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addFragment).addToBackStack("AddNotesFragment").commit();
    }
}