package com.example.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.data.Data;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Data> dataList;

    public MyAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView price;
        TextView amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_time);
            price = itemView.findViewById(R.id.tv_price);
            amount = itemView.findViewById(R.id.tv_amount);
        }

        public void setText(Data data) {
            time.setText(data.getTime());
            price.setText(data.getPrice());
            amount.setText(data.getNumber());
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setText(dataList.get(position));
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
