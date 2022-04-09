package com.ytokmak.artbookjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ytokmak.artbookjava.databinding.RcviewBinding;

import java.util.ArrayList;

public class mAdapter extends RecyclerView.Adapter<mAdapter.mHolder> {
    ArrayList<Details> arrayList;

    public mAdapter(ArrayList<Details> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public mHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RcviewBinding bind = RcviewBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new mHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull mHolder holder, int position) {
       holder.bind.numberText.setText(""+arrayList.get(position).id);
       holder.bind.nameText.setText( arrayList.get(position).name);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), InfoActivity.class);
                intent.putExtra("ArtName",arrayList.get(position).id);
                intent.putExtra("Old","Old");
                holder.itemView.getContext().startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class mHolder extends RecyclerView.ViewHolder {
        RcviewBinding bind;
        public mHolder(@NonNull RcviewBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }
    }
}