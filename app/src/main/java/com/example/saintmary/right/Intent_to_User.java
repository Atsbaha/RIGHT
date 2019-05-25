package com.example.saintmary.right;

        import android.content.Intent;
        import android.graphics.Typeface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

public class Intent_to_User extends AppCompatActivity {
    Button btnFoodOrder,btnMusicOrder,btnFootballProgram,btnDrinkOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_to__user);

//        LayoutInflater inflater=LayoutInflater.from(this);
//        View layout_pwd=inflater.inflate(R.layout.new_user_layout,null);


       /* btnFoodOrder=layout_pwd.findViewById(R.id.btnFooodOrder);
        btnMusicOrder=layout_pwd.findViewById(R.id.btnMusiccOrder);
        btnFootballProgram=layout_pwd.findViewById(R.id.btnFoottballProgram);
        btnDrinkOrder=layout_pwd.findViewById(R.id.btnDrinkkOrder);*/


        btnFoodOrder=findViewById(R.id.btnFooodOrder);
        btnMusicOrder=findViewById(R.id.btnMusiccOrder);
        btnFootballProgram=findViewById(R.id.btnFoottballProgram);
        btnDrinkOrder=findViewById(R.id.btnDrinkkOrder);


        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        btnFoodOrder.setTypeface(typeface);
        btnMusicOrder.setTypeface(typeface);
        btnFootballProgram.setTypeface(typeface);
        btnDrinkOrder.setTypeface(typeface);






        btnFoodOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent_to_User.this,FoodUser.class);
                startActivity(intent);
            }
        });
        btnMusicOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(intent_to_all.this,Music_Home.class);
                Intent intent=new Intent(Intent_to_User.this,MusicUser.class);
                startActivity(intent);
            }
        });
        btnDrinkOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent_to_User.this,DrinkUser.class);
                startActivity(intent);
            }
        });
        btnFootballProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent_to_User.this,FootballProgramUser.class);
                startActivity(intent);
            }
        });

    }
}
