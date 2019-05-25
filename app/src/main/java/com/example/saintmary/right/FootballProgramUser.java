package com.example.saintmary.right;

        import android.graphics.Typeface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

public class FootballProgramUser extends AppCompatActivity {
    TextView footballDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_program_user);

        footballDetail=findViewById(R.id.FootballProgram);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        footballDetail.setTypeface(typeface);
        footballDetail.setText(R.string.footballProgram);//temesasali ms drink music and so on
    }
}
