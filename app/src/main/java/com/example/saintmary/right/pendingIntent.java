package com.example.saintmary.right;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class pendingIntent extends AppCompatActivity {
    TextView txtPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_intent);
        txtPendingIntent=findViewById(R.id.pendingIntent);

        txtPendingIntent.setText(R.string.pendingState);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        txtPendingIntent.setTypeface(typeface);



    }
}
