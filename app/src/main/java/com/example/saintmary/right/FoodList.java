package com.example.saintmary.right;

import android.os.Bundle;
//we need to add ".indexOn" in the database of Rules tab


//ab 12 dekika part 7 and pubish in the firebase add Name to the MenuId in the rule tab which show all its details also
        import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
        import android.text.Editable;
        import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.saintmary.right.Database.Database;
import com.example.saintmary.right.Interface.ItemClickListener;
import com.example.saintmary.right.Model.Food;
        import com.example.saintmary.right.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.mancj.materialsearchbar.MaterialSearchBar;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;


public class FoodList extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference foodList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    String categoryId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    //search functionality
     FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList=new ArrayList<>();
    MaterialSearchBar materialSearchBar;//we will import from firebase online

    //Favourites
    Database localDB;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
//        this.getDelegate().setContentView(R.layout.activity_food_list);
        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        //loacal DB
        localDB=new Database(this);


    swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.food_swipe_layout);
    swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark);


    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //Get Intent here
            if (getIntent() != null)
                categoryId = getIntent().getStringExtra("CategoryId");
            if (!categoryId.isEmpty() && categoryId != null) {
                loadListFood(categoryId);
            }

        }
    });

    swipeRefreshLayout.post(new Runnable() {
        @Override
        public void run() {
            //Get Intent here
            if (getIntent() != null)
                categoryId = getIntent().getStringExtra("CategoryId");
            if (!categoryId.isEmpty() && categoryId != null) {
                loadListFood(categoryId);
            }
        }
    });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here
       /* if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);
        }*/


//search
       /* materialSearchBar=(MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your food");
        materialSearchBar.setSpeechMode(false); //no need to define because we already defined in the xml///////
        loadSuggest();//write function to load suggest from firebase
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);*/
       /* materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //when user type thier text,we will change suggest list
                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList) {//correct this it is not sugget but it suggestList
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);

                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

     /*  materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
           @Override
           public void onSearchStateChanged(boolean enabled)
           {
               //when search Bar is close
//            Restore original suggest adapter
               if(!enabled)
                   recyclerView.setAdapter(adapter);

           }
           @Override
           public void onSearchConfirmed(CharSequence text)
           {
//               when search finish
//            show results of search adapter
               startSearch(text);
           }
           @Override
           public void onButtonClicked(int buttonCode)
           {

           }

       });*/

        }

    private void startSearch(CharSequence text) {
        searchAdapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("name").equalTo(text.toString()) ////this was foodList.orderByChild("name").equalTo(text.toString())//this was foodList.orderByChild("MenuId").equalTo(categoryId) but it lists all no filter so changed to what is now
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                //the load is used to load a url
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                final Food local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        Intent foodDetail=new Intent(FoodList.this,FoodDetail.class);
                        //searchAdapter was Adapter and we need to get index on it
                        foodDetail.putExtra("FoodId",searchAdapter.getRef(position).getKey());//send Food Id to new Activity
                        startActivity(foodDetail);
                    }//this was });

                });
            }
        };

        recyclerView.setAdapter(searchAdapter);//set adapter for Recycler view is search result
    }
   private void loadSuggest() {
        foodList.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Food item=postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());//add the name of the food to suggest list
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void loadListFood(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId)//this is like select * from foods where MenuId=catagoryId
        ) {
            @Override
            protected void populateViewHolder(final FoodViewHolder viewHolder, final Food model, final int position) {

                viewHolder.food_name.setText(model.getName());
                viewHolder.food_price.setText(String.format("",model.getPrice().toString()));
                //the load is used to load a url
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);



                //ADD favorites
                if(localDB.isFavourite(adapter.getRef(position).getKey()))
                    viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);

                //click to change state of favorites
                viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!localDB.isFavourite(adapter.getRef(position).getKey()))
                        {
                            localDB.addToFavourites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(FoodList.this,""+model.getName()+"is added to favorites",Toast.LENGTH_SHORT).show();
                  }
                        else
                        {
                            localDB.removeFromFavourites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(FoodList.this,""+model.getName()+"is removed from favorites",Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                final Food local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        //start new activity
                        Intent foodDetail=new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());//send Food Id to new Activity
                        startActivity(foodDetail);
                 }//this was });

            });
        }
        };

            //        Log.d("TAG", ""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);

    }
}





















//categoryID will be corrected after importing firebase or syncing the gradle



/*

        };


    materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
        @Override
        public void onSearchStateChanged(boolean enabled) {
//       when search Bar is close
//            Restore original suggest adapter
            if(!enabled)
            recyclerView.setAdapter(adapter);


        }

        @Override
        public void onSearchConfirmed(CharSequence text) {
//       when search finish
//            show results of search adapter
            startSearch(text);
        }

        @Override
        public void onButtonClicked(int buttonCode) {

        }
    });

    }

    private void startSearch(CharSequence text) {
        searchAdapter=new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                //the load is used to load a url
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);
                final Food local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());//send Food Id to new Activity
                        startActivity(foodDetail);

                    });


                );

                recyclerView.setAdapter(searchAdapter);//set adapter for recyclerView
            }
*/
