package com.example.saintmary.right;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saintmary.right.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.example.saintmary.project.Common.Common; //common zbl class ms feterna androideatit b project nkyro
import com.example.saintmary.right.Model.User;//user zbl class ms feterna androideatit b project nkyro

import io.paperdb.Paper;
//import com.example.saintmary.project.Common.Common;
//import com.example.saintmary.project.Model.Home;*/


public class SignIn extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnSignIn;
    CheckBox ckbRemember;
    TextView txtForgetPwd;

    FirebaseDatabase database;
    DatabaseReference table_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

       /* edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);*/
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        btnSignIn = findViewById(R.id.btnSignIn);
        ckbRemember=(CheckBox)findViewById(R.id.ckbRemember);
        txtForgetPwd=findViewById(R.id.txtForgetPwd);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        btnSignIn.setTypeface(typeface);
        edtPassword.setTypeface(typeface);
        edtPhone.setTypeface(typeface);
        ckbRemember.setTypeface(typeface);
        txtForgetPwd.setTypeface(typeface);



        //init paper
        Paper.init(this);


        //init firebase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        txtForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetPwdDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save user and password
                if(ckbRemember.isChecked())
                {
                    Paper.book().write(Common.USER_KEY,edtPhone.getText().toString());
                    Paper.book().write(Common.PWD_KEY,edtPassword.getText().toString());

                }

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);//Progress bars are used to show progress of a task. For example, when you are uploading or downloading something from the internet, it is better to show the progress of download/upload to the user
//                mDialog.setMessage("Please Waiting...");
                // mDialog.show();//You're trying to show a Dialog after you've exited an Activity.

                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {//A DataSnapshot contains data from a Database location.Any time you read data from the Database, you receive the data as a DataSnapshot
                        //check if the user is not existed in database
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            //Get user information
//                            mDialog.dismiss();//usually to respond to a click event on a button in your Dialog .
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                            user.setPhone(edtPhone.getText().toString());


                            if(Boolean.parseBoolean(user.getIsEnabled())) //if isStaff is true
                            {

                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    //Toast.makeText(SignIn.this, "Sign in successfully!", Toast.LENGTH_SHORT).show();

                                    Intent homeIntent = new Intent(SignIn.this, intent_to_all.class);
                                    Common.currentUser = user;//this is a variable to store the current user
                                    startActivity(homeIntent);
                                    finish();

                                    table_user.removeEventListener(this);
                                }
//                                    Toast.makeText(SignIn.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                            }else

                            {
                                Intent homeIntent=new Intent(SignIn.this,pendingIntent.class);
                                Common.currentUser=user;//this is a variable to store the current user
                                startActivity(homeIntent);
                                finish();
//                                Toast.makeText(SignIn.this, "Sign in Successfull!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
//                            mDialog.dismiss();
//                            Toast.makeText(SignIn.this, "user not existed in database", Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                         /*  user.setPhone(edtPhone.getText().toString());
                             (user.getPassword().equals(edtPassword.getText().toString())) {

                                Intent homeIntent = new Intent(Signin.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();

                            } */
            }
        });

    }

    private void showForgetPwdDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Forget password");
        builder.setMessage("Enter your secure code");

        LayoutInflater inflater=this.getLayoutInflater();
        View forget_view=inflater.inflate(R.layout.forget_password_layout,null);

        builder.setView(forget_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        final EditText edtPhone=forget_view.findViewById(R.id.edtPhone);
        final EditText edtSecureCode=forget_view.findViewById(R.id.edtSecureCode);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
        edtPhone.setTypeface(typeface);
        edtSecureCode.setTypeface(typeface);
        edtPhone.setHint("phone number");
        edtSecureCode.setHint("Password hint");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //check if user available
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user =dataSnapshot.child(edtPhone.getText().toString())
                                .getValue(User.class);

                        if(user.getSecureCode().equals(edtSecureCode.getText().toString()))
                            Toast.makeText(SignIn.this, "Your Password is: "+user.getPassword(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(SignIn.this,"Wrong secure code",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

}

