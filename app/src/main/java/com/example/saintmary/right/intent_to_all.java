package com.example.saintmary.right;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class intent_to_all extends AppCompatActivity {
    Button btnFoodOrder,btnMusicOrder,btnFootballProgram,btnDrinkOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_to_all);

//        LayoutInflater inflater=LayoutInflater.from(this);
//        View layout_pwd=inflater.inflate(R.layout.new_layout,null);
         /*btnFoodOrder=layout_pwd.findViewById(R.id.btnFoodOrder);
        btnMusicOrder=layout_pwd.findViewById(R.id.btnMusicOrder);
        btnFootballProgram=layout_pwd.findViewById(R.id.btnFootballProgram);
        btnDrinkOrder=layout_pwd.findViewById(R.id.btnDrinkOrder);*/


        btnFoodOrder=findViewById(R.id.btnFoodOrder);
        btnMusicOrder=findViewById(R.id.btnMusicOrder);
        btnFootballProgram=findViewById(R.id.btnFootballProgram);
        btnDrinkOrder=findViewById(R.id.btnDrinkOrder);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        btnDrinkOrder.setTypeface(typeface);
        btnFoodOrder.setTypeface(typeface);
        btnFootballProgram.setTypeface(typeface);
        btnMusicOrder.setTypeface(typeface);




//      //  btnTableReservation=findViewById(R.id.btnTableReservation);a



        btnFoodOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(intent_to_all.this,Home.class);
                startActivity(intent);
            }
        });
        btnMusicOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(intent_to_all.this,Music_Home.class);
                Intent intent=new Intent(intent_to_all.this,Music_Home.class);
                startActivity(intent);
            }
        });
        btnDrinkOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(intent_to_all.this,Drink_Home.class);
                startActivity(intent);
            }
        });
        btnFootballProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(intent_to_all.this,FootballProgram_Home.class);
                startActivity(intent);
            }
        });

    }
}
