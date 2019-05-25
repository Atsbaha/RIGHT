package com.example.saintmary.right;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FoodUser extends AppCompatActivity {
    TextView FoodDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_user);

        FoodDetail=findViewById(R.id.FoodDetail);
        FoodDetail.setText(R.string.foodlist);//temesasali ms drink music and so on
    }
}
