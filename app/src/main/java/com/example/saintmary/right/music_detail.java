package com.example.saintmary.right;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//TextDrawable->is used to create Text Drawable image

//import com.example.saintmary.right.Database.Database;//wait until Database class is created
//import com.example.saintmary.right.Database.Database;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
//        import com.example.saintmary.right.Database.Database;
import com.example.saintmary.right.Database.Database;
//        import com.example.saintmary.right.Database.rightDBHelper;
import com.example.saintmary.right.Model.Food;
//import com.example.saintmary.project.Model.Order; wait until you create an order class
// import com.example.saintmary.right.Model.Order;
import com.example.saintmary.right.Model.Music;
import com.example.saintmary.right.Model.Order;
import com.example.saintmary.right.ViewHolder.FoodViewHolder;
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;//wait until you snc the firebase added liabraries online
import com.example.saintmary.right.ViewHolder.MusicViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class music_detail extends AppCompatActivity {

    TextView music_name;
    ImageView music_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    FirebaseDatabase database;
    DatabaseReference music;
    Music currentMusic;

    String musicId = "";
    FirebaseRecyclerAdapter<Music, MusicViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        music = database.getReference("Musics");

        //Init View
//        numberButton=(ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        //the following will be added in the next video

        //this button is used to add  the details to cart
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the addToCart(new Order)  is found in the Database class int the method public void addToCart(Order order)

                /*new Database(getBaseContext()).addToCart(new Order(
//                new Database(getBaseContext()).addToCart(new Order(
                        musicId,
                        null,
                        null,
                        null,
//                        currentMusic.getMusicianName()



                ));*/

                Toast.makeText(music_detail.this, "Added to chart", Toast.LENGTH_SHORT).show();
            }
        });
//        music_name = (TextView) findViewById(R.id.txtNameOfMusic);
//        music_image = (ImageView) findViewById(R.id.imageOfMusic);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        //Get food ID from Intent
        if(getIntent() != null)
            musicId = getIntent().getStringExtra("MusicId");
        if(!musicId.isEmpty()){
            getDetailMusic(musicId);
        }

    }

    private void getDetailMusic(String musicId) {
        music.child(musicId).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                currentMusic = dataSnapshot.getValue(Music.class);

                //Set Image
                //picasso is used to load image
//                Picasso.with(getBaseContext()).load(currentMusic.getMp3Music()).into(music_image);

//                collapsingToolbarLayout.setTitle(currentMusic.getMusicianName());

//                music_name.setText(currentMusic.getMusicianName());


            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }
}

