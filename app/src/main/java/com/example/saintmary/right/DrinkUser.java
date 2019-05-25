package com.example.saintmary.right;

        import android.graphics.Typeface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

public class DrinkUser extends AppCompatActivity {
    TextView DrinkDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_user);


        DrinkDetail=findViewById(R.id.DrinkDetail);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        DrinkDetail.setTypeface(typeface);
        DrinkDetail.setText(R.string.drinklist);//temesasali ms drink music and so on
    }
}
