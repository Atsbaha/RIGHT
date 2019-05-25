package com.example.saintmary.right.Interface;
//this is used to click Recycler View item-> we need to implement Onclick on Recycler View

import android.view.View;

public interface ItemClickListener {
    void onClick(View view,int position, boolean isLongClick);//We will add third parameter here
}
