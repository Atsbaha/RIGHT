package com.example.saintmary.right;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;


        import android.app.ProgressDialog;
//        import androidx.appcompat.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.saintmary.right.Model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    //MaterialEditText edtphone,edtName,edtpassword;
    EditText edtphone,edtFirstName,edtLastName,edtpassword,edtSecureCode,edtStaff,edtConfirmPassword;
    Button btnSingUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtFirstName=findViewById(R.id.edtFirstName);
        edtLastName=findViewById(R.id.edtLastName);
        edtpassword=findViewById(R.id.edtPassword);
        edtphone=findViewById(R.id.edtPhone);
        edtSecureCode=findViewById(R.id.edtSecureCode);
        edtConfirmPassword=findViewById(R.id.edtConfirmPassword);


        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        edtFirstName.setTypeface(typeface);
        edtLastName.setTypeface(typeface);
        edtpassword.setTypeface(typeface);
        edtphone.setTypeface(typeface);
        edtSecureCode.setTypeface(typeface);
        edtConfirmPassword.setTypeface(typeface);




        btnSingUp=findViewById(R.id.btnSignUp);
        btnSingUp.setTypeface(typeface);
        //init firebase
        FirebaseApp.initializeApp(this);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
         final DatabaseReference table_user = database.getReference("User");
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Wating...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // check if already user phone
                        if(dataSnapshot.child(edtphone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
//                            Toast.makeText(SignUp.this,"Phone number already registered",Toast.LENGTH_SHORT).show();
                        }
                        else if(edtConfirmPassword.getText().toString().equals(edtpassword.getText().toString()))
                        {

                            mDialog.dismiss();
                            User user=new User(
                                    edtFirstName.getText().toString(),
                                    edtLastName.getText().toString(),
                                    edtpassword.getText().toString(),
                                    edtSecureCode.getText().toString());
                            table_user.child(edtphone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this,"Sign up successfully",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SignUp.this,pendingIntent.class);
                            startActivity(intent);


                            finish();
                        }
                        else
                            Toast.makeText(SignUp.this,"Password not Matched",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });




    }

}

