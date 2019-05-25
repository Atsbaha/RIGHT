
package com.example.saintmary.right;

        import android.graphics.Typeface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;

public class MusicUser extends AppCompatActivity {
    TextView MusicDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_user);

        MusicDetail=findViewById(R.id.MusicDetail);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        MusicDetail.setTypeface(typeface);
        MusicDetail.setText(R.string.musiclist);//temesasali ms drink music and so on
    }
}
