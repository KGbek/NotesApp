package com.example.notesapp;

import static android.media.CamcorderProfile.get;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.fragments.AddNotesFragment;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private LayoutInflater inflater;
    private List<NotesModel> list = new ArrayList<>();
    private onClick onClick;

    public NotesAdapter(Context context, onClick click){
        this.inflater = LayoutInflater.from(context);
        this.onClick = click;
    }

    public interface onClick {
        void click(int position);
    }

    public void addNote(NotesModel model){
        list.add(model);
        notifyDataSetChanged();
    }

    public List<NotesModel> getList(){
        return list;
    }

    public void editNote(NotesModel model, int position){
        list.set(position, model);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_notes, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtDescription.setText(list.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtDescription;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_item_title);
            txtDescription = itemView.findViewById(R.id.txt_item_description);
        }
    }
}
