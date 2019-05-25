
package com.example.saintmary.right;

        import android.graphics.Typeface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
    TextView AboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        AboutUs=findViewById(R.id.AboutUs);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        AboutUs.setTypeface(typeface);
        AboutUs.setText(R.string.Aboutus);//temesasali ms drink music and so on
    }
}

