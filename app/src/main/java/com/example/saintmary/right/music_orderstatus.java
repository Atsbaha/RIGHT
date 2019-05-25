


package com.example.saintmary.right;

//        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.Toast;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;

        import com.example.saintmary.right.Common.Common;
        import com.example.saintmary.right.Model.Category;
        import com.example.saintmary.right.Model.MusicRequest;
        import com.example.saintmary.right.Model.Request;
        import com.example.saintmary.right.ViewHolder.MusicOrderViewHolder;
        import com.example.saintmary.right.ViewHolder.OrderViewHolder;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class music_orderstatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<MusicRequest, MusicOrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_orderstatus);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("MusicRequests");

        recyclerView = findViewById(R.id.music_listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!=null)
            loadMusicOrders(Common.currentUser.getPhone());

        else
            loadMusicOrders(getIntent().getStringExtra("userPhone"));

    }

    private void loadMusicOrders(String phone) {
        //<Request, OrderViewHolder> ezi malet the parameters we used are from these classes or of type these classes
        adapter = new FirebaseRecyclerAdapter<MusicRequest, MusicOrderViewHolder>(
                MusicRequest.class,
                R.layout.music_order_layout,
                MusicOrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(MusicOrderViewHolder viewHolder, MusicRequest model, final int position) {
                Category c=new Category();
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderedMusicName.setText(model.getMusicName());
                viewHolder.textMusicianName.setText(model.getMusicianName());

                viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(adapter.getItem(position).getStatus().equals("0"))
                            deleteOrder(adapter.getRef(position).getKey());
                        else
                            Toast.makeText(music_orderstatus.this,"you can not delete the order",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        };

        recyclerView.setAdapter(adapter);

    }

    //    private void deleteOrder(DatabaseReference ref) {
    private void deleteOrder(final String key) {
        requests.child(key)
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(music_orderstatus.this,new StringBuilder("Order")
                        .append(key)
                        .append("has been deleted").toString(),Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(music_orderstatus.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
