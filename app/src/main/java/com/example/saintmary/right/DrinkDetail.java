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
import com.example.saintmary.right.Database.DrinkDatabase;
import com.example.saintmary.right.Model.Drink;
import com.example.saintmary.right.Model.DrinkOrder;
import com.example.saintmary.right.Model.Food;
//import com.example.saintmary.project.Model.Order; wait until you create an order class
// import com.example.saintmary.right.Model.Order;
import com.example.saintmary.right.Model.Order;
import com.example.saintmary.right.ViewHolder.DrinkViewHolder;
import com.example.saintmary.right.ViewHolder.FoodViewHolder;
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;//wait until you snc the firebase added liabraries online
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class DrinkDetail extends AppCompatActivity {

    TextView drink_name, drink_price, drink_description;
    ImageView drink_image;
    CollapsingToolbarLayout drinkcollapsingToolbarLayout;
    FloatingActionButton drink_btnCart;
    ElegantNumberButton drink_numberButton;
    //Button numberButton;//you will use simply Button here
    FirebaseDatabase database;
    DatabaseReference drinks;
    Drink currentDrink;

    String drinkId = "";
    FirebaseRecyclerAdapter<Drink, DrinkViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        drinks = database.getReference("Drinks");

        //Init View
        drink_numberButton=(ElegantNumberButton) findViewById(R.id.drink_number_button);
        drink_btnCart = (FloatingActionButton) findViewById(R.id.drink_btnCart);
        //the following will be added in the next video

        //this button is used to add  the details to cart
        drink_btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the addToCart(new Order)  is found in the Database class int the method public void addToCart(Order order)

                new DrinkDatabase(getBaseContext()).DrinkaddToCart(new DrinkOrder(
//                new Database(getBaseContext()).addToCart(new Order(
                        drinkId,
                        currentDrink.getDrinkName(),
                        drink_numberButton.getNumber(), //null,//use null to check or to run the program
                        currentDrink.getDrinkPrice(),
                        currentDrink.getDrinkDiscount()


                ));

                Toast.makeText(DrinkDetail.this, "Added to chart", Toast.LENGTH_SHORT).show();
            }
        });

        drink_description= (TextView) findViewById(R.id.drink_description);
        drink_name = (TextView) findViewById(R.id.drink_name);
        drink_price = (TextView) findViewById(R.id.drink_price);
        drink_image = (ImageView) findViewById(R.id.img_drink);

        drinkcollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.drink_collapsing);
        drinkcollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        drinkcollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        //Get food ID from Intent
        if(getIntent() != null)
            drinkId = getIntent().getStringExtra("DrinkId");
        if(!drinkId.isEmpty()){
            getDetailDrink(drinkId);
        }

    }

    private void getDetailDrink(String drinkId) {
        drinks.child(drinkId).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                currentDrink = dataSnapshot.getValue(Drink.class);

                //Set Image
                //picasso is used to load image
                Picasso.with(getBaseContext()).load(currentDrink.getDrinkImage()).into(drink_image);

                drinkcollapsingToolbarLayout.setTitle(currentDrink.getDrinkName());

                drink_price.setText(currentDrink.getDrinkPrice());

                drink_name.setText(currentDrink.getDrinkName());

                drink_description.setText(currentDrink.getDrinkDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }

   /* private void getDetailDrink(String foodId) {
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

    }*/
}

