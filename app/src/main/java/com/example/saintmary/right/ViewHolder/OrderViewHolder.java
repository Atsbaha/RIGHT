package com.example.saintmary.right.ViewHolder;

        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.saintmary.right.Interface.ItemClickListener;
        import com.example.saintmary.right.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress,txtOrderedFoodName;
    public ImageView btn_delete;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView){
        super(itemView);
        txtOrderId = itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderAddress = itemView.findViewById(R.id.order_address);//this is found in the order_layout.xml
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderedFoodName=itemView.findViewById(R.id.ordered_food_name) ;

        btn_delete=(ImageView)itemView.findViewById(R.id.btn_delete);





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


