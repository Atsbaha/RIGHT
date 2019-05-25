package com.example.saintmary.right;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
//        import android.telecom.Call;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.saintmary.right.Common.Common;
//        import com.example.saintmary.right.Database.Database;
        import com.example.saintmary.right.Database.Database;
//        import com.example.saintmary.right.Database.rightDBHelper;
        import com.example.saintmary.right.Model.MusicRequest;
        import com.example.saintmary.right.Model.MyResponse;
        import com.example.saintmary.right.Model.Notification;
        import com.example.saintmary.right.Model.Order;
        import com.example.saintmary.right.Model.Request;
        // import com.example.saintmary.right.Model.Receipt;
        import com.example.saintmary.right.Model.Sender;
        import com.example.saintmary.right.Model.Token;
        import com.example.saintmary.right.Remote.APIService;
        import com.example.saintmary.right.ViewHolder.CartAdapter;
//        import com.google.android.gms.common.api.Response;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.text.NumberFormat;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

//        import javax.security.auth.callback.Callback;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


public class music_cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;
    float totalPrice;

   /* List<Order> cart = new ArrayList<>();
    CartAdapter adapter;*/

    APIService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_cart);

        mService= Common.getFCMService();

        //thread running in 1 hour interval
//        executor.scheduleAtFixedRate(inventorylistthread, 0, 60, TimeUnit.MINUTES);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("MusicRequests");

        //Init
        recyclerView = findViewById(R.id.musicListCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnPlace = findViewById(R.id.musicbtnPlaceOrder);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });




//        loadListFood();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(music_cart.this);
        alertDialog.setTitle("One more Step");
        alertDialog.setMessage("Enter Name of Music and Name of Musician");

        final EditText edtOrder=new EditText(music_cart.this);
        edtOrder.setHint("Name of Music and Musician");

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtOrder.setLayoutParams(lp);
        alertDialog.setView(edtOrder);//add edit text to alert dialog

        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);


        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //create new request

                String cart="";
                MusicRequest request=new MusicRequest(
                        Common.currentUser.getPhone(),
                        /*Common.currentUser.getMu(),
                        Common.currentUser.getLasttName(),*/
                        edtOrder.getText().toString(),
                        cart
                );
                //submit to firebase
                //we will use System.currentMill to key
                String order_number=(String.valueOf(System.currentTimeMillis()));
                requests.child(order_number)
                        .setValue(request);

                //Delete cart
                new Database(getBaseContext()).cleanCart();//this is found in the Database class
//                new Database(getBaseContext()).cleanCart();//this is found in the Database class
//                Toast.makeText(Cart.this,"Thank you,Order place",Toast.LENGTH_SHORT).show();
//                finish();
                sendNotificationOrder(order_number);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        alertDialog.show();

    }

    private void sendNotificationOrder(final String order_number) {
        DatabaseReference tokens=FirebaseDatabase.getInstance().getReference("Tokens");
        Query data=tokens.orderByChild("isServerToken").equalTo(true);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                {
                    Token serverToken=postSnapShot.getValue(Token.class);
                    //create raw payload to send
                    Notification notification=new Notification("Right","You have new Order"+order_number);
                    Sender content=new Sender(serverToken.getToken(),notification);

                    mService.sendNotification(content)
                            .enqueue(new Callback<MyResponse>(){
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response){
                                    if(response.body().success == 1)
                                    {
                                        Toast.makeText(music_cart.this,"Thank you,Order place",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(music_cart.this,"Failed",Toast.LENGTH_SHORT).show();

                                    }
                                }
                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t){
                                    Log.e("ERROR",t.getMessage());
                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


   /* private void loadListFood() {
//        cart = new Database(this).getCarts();
        // orderList.add(cart);
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        //Calculate total price
        int total = 0;
        for (Order order : cart)
            total += (float) (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuanlity()));
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);


        // add tax, profit to total, do we need to show the tax and profit on the app??
       /* float tax = (float) (total * 0.06);
        float profit = (float) (total * 0.3);
        total += tax + profit;

        totalPrice = total;///////

        txtTotalPrice.setText(fmt.format(total));

    }*/
}





