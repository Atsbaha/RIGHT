
package com.example.saintmary.right.ViewHolder;

        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.saintmary.right.Interface.ItemClickListener;
        import com.example.saintmary.right.R;

public class MusicOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, /*txtOrderAddress,*/txtOrderedMusicName,textMusicianName;
    public ImageView btn_delete;

    private ItemClickListener itemClickListener;

    public  MusicOrderViewHolder(View itemView){
        super(itemView);

//        txtOrderAddress = itemView.findViewById(R.id.order_address);//this is found in the order_layout.xml
        txtOrderId = itemView.findViewById(R.id.music_order_id);
        txtOrderStatus = itemView.findViewById(R.id.music_order_status);
//        txtOrderPhone = itemView.findViewById(R.id.music_order_address);
        btn_delete=(ImageView)itemView.findViewById(R.id.music_btn_delete);
        txtOrderedMusicName=itemView.findViewById(R.id.order_music_name);
        textMusicianName=itemView.findViewById(R.id.ordered_Musician_name);

//        txtOrderedFoodName=itemView.findViewById(R.id.ordered_food_name) ;



        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view,getAdapterPosition(),false);//this is an error for the newly created category
    }
}


