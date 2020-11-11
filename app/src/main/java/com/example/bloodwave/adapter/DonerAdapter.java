package com.example.bloodwave.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodwave.CreateDonerModel;
import com.example.bloodwave.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DonerAdapter extends RecyclerView.Adapter<DonerAdapter.ViewHolder>{
    private ArrayList listdata;

    // RecyclerView recyclerView;
    public DonerAdapter(ArrayList listdata) {
        this.listdata = listdata;
    }



//    public DonerAdapter(ArrayList data) {
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_doner_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final HashMap<String,String> myListData = (HashMap<String, String>) listdata.get(position);

        holder.textViewName.setText( myListData.get("name"));
        holder.textViewBloodGroup.setText( myListData.get("bloodgroup"));
        holder.textViewMobile.setText( myListData.get("phone"));
        if(myListData.get("date") !=null){
            holder.textViewDate.setText( myListData.get("date"));

        }
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewBloodGroup;
        public TextView textViewMobile;
        public TextView textViewDate;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewBloodGroup = (TextView) itemView.findViewById(R.id.textViewBloodGroup);
            this.textViewMobile = (TextView) itemView.findViewById(R.id.textViewMobile);
            this.textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        }
    }
}

