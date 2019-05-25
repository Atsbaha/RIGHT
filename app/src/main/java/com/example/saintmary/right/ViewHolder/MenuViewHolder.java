package com.example.saintmary.right.ViewHolder;



import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import  com.example.saintmary.right.Interface.ItemClickListener;

import  com.example.saintmary.right.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;
    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        //the menu_name and menu_image are found in the menu_item layout resources with cardview
        txtMenuName=(TextView)itemView.findViewById(R.id.menu_name);
        imageView=(ImageView)itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        // itemClickListener.onClick(View,)
        itemClickListener.onClick(view,getAdapterPosition(),false);//but this is according to the video that  i have seen
        // itemClickListener.onClick(view,false);its original was this

    }
}
