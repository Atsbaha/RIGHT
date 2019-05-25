package com.example.saintmary.right;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.CollapsingToolbarLayout;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;
        import android.widget.Toast;

        //TextDrawable->is used to create Text Drawable image

        //import com.example.saintmary.right.Database.Database;//wait until Database class is created
        //import com.example.saintmary.right.Database.Database;
        import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
//        import com.example.saintmary.right.Database.Database;
        import com.example.saintmary.right.Common.Common;
        import com.example.saintmary.right.Database.Database;
//        import com.example.saintmary.right.Database.rightDBHelper;
        import com.example.saintmary.right.Model.Food;
//import com.example.saintmary.project.Model.Order; wait until you create an order class
       // import com.example.saintmary.right.Model.Order;
        import com.example.saintmary.right.Model.Order;
        import com.example.saintmary.right.Model.Rating;
        import com.example.saintmary.right.ViewHolder.FoodViewHolder;
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;//wait until you snc the firebase added liabraries online
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
        import com.squareup.picasso.Picasso;
        import com.stepstone.apprating.AppRatingDialog;
        import com.stepstone.apprating.listener.RatingDialogListener;

        import java.util.Arrays;


public class FoodDetail extends AppCompatActivity implements RatingDialogListener {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart,btnRating;
    ElegantNumberButton numberButton;
    RatingBar ratingBar;
   //Button numberButton;//you will use simply Button here
    FirebaseDatabase database;
    DatabaseReference foods;
    DatabaseReference ratingTbl;
    Food currentFood;

    String foodId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        ratingTbl=database.getReference("Rating");

        //Init View
        numberButton=(ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        btnRating=(FloatingActionButton)findViewById(R.id.btn_rating);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);


      btnRating.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
             showRatingDialog(); 
          }
      });
        //the following will be added in the next video

       //this button is used to add  the details to cart
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the addToCart(new Order)  is found in the Database class int the method public void addToCart(Order order)

                new Database(getBaseContext()).addToCart(new Order(
//                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(), //null,//use null to check or to run the program
                        currentFood.getPrice(),
                        currentFood.getDiscount()


                ));

                Toast.makeText(FoodDetail.this, "Added to chart", Toast.LENGTH_SHORT).show();
            }
        });

        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        //Get food ID from Intent
        if(getIntent() != null)
            foodId = getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty()){
            getDetailFood(foodId);
            getRatingFood(foodId);
        }

    }

    private void getRatingFood(String foodId) {
        com.google.firebase.database.Query foodRating=ratingTbl.orderByChild("foodId").equalTo(foodId);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count=0,sum=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
            {
              Rating item=postSnapshot.getValue(Rating.class);
              sum+=Integer.parseInt(item.getRateValue());
              count++;
            }
            if(count!=0)
                {
                    float average = sum / count;
                    ratingBar.setRating(average);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showRatingDialog() {
        //ab dependecy add zgebernayo library sync ms genernayo kstekakel eyu
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Quite Ok","Very Good","Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate This Food")
                .setDescription("please select some stars and give your feedback")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimary)
                .setWindowAnimation(R.style.RatingDialogFadeAmin)
                .create(FoodDetail.this)
                .show();


    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                currentFood = dataSnapshot.getValue(Food.class);

                //Set Image
                //picasso is used to load image
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());

                food_price.setText(currentFood.getPrice());

                food_name.setText(currentFood.getName());

                food_description.setText(currentFood.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }

    @Override
    public void onPositiveButtonClicked(int value,String comments)
    {
//Get Rating and upload to firebase
        final Rating rating=new Rating(Common.currentUser.getPhone(),
                foodId,
                String.valueOf(value),
               comments);

        ratingTbl.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Common.currentUser.getPhone()).exists())
                {
                    //remove old value
                    ratingTbl.child(Common.currentUser.getPhone()).removeValue();
                    //Update new Value
                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
                }
                else
                {
                    //update new Value
                    ratingTbl.child(Common.currentUser.getPhone()).setValue(rating);
                }
                Toast.makeText(FoodDetail.this,"Thank you for submiting rating",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onNegativeButtonClicked()
    {

    }
}

