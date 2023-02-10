package com.drawing.keywordpick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(View view, int position, String menuName);
    }
    public ArrayList<String> menuArr = null;
    public OnItemClickListener listener = null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    RecyclerAdapter(ArrayList<String> menuArr){
        this.menuArr = menuArr;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.list_recycler_item,parent,false);
        RecyclerAdapter.ViewHolder vh = new RecyclerAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String menu = menuArr.get(position);
        holder.tv_item.setText(menu);
    }

    @Override
    public int getItemCount() {
        return menuArr.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item;

        ViewHolder(final View itemView) {
            super(itemView);
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        if(listener != null){
                            String menu = menuArr.get(position);
                            listener.onItemClick(v,position,menu);

                        }
                    }
                }
            });
        }
    }

}
