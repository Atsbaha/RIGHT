package com.example.saintmary.right;

//import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
//import androidx.appcompat.app.AppCompatCallback;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saintmary.right.Common.Common;
import com.example.saintmary.right.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

//import com.google.android.gms.signin.SignIn;//Be SURE//Thank oyu my God
//MenuId=10 means this is food in category with Id=10
public class MainActivity extends AppCompatActivity
        {
            Button btnSignIn, btnSignUp,btnService,btnAboutUs;
            //implements NavigationView.OnNavigationItemSelectedListener {
//        Button btnSignIn, btnSignUp;
            TextView txtSlogan;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                btnSignIn = findViewById(R.id.btnSignIn);
                btnSignUp = findViewById(R.id.btnSignUp);

                btnService=findViewById(R.id.btnService);
                btnAboutUs=findViewById(R.id.btnAbout);
                Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");
                btnSignIn.setTypeface(typeface);
                btnService.setTypeface(typeface);
                btnAboutUs.setTypeface(typeface);
                btnSignUp.setTypeface(typeface);





//                txtSlogan = findViewById(R.id.txtSlogan);
                //you can create assets in app folder and copy and paste Nabila.ttf from PROJECT foder
               // Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Nabila.ttf");
                //txtSlogan.setTypeface(face);
                //init paper
                Paper.init(this);

                btnSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent signin = new Intent(MainActivity.this, SignIn.class);
                        startActivity(signin);
//                        btnSignIn.setText("create an account first");
                    }
                });

                btnSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent signup = new Intent(MainActivity.this, SignUp.class);
                        startActivity(signup);
                    }
                });

                btnAboutUs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(intent);
//                        btnSignIn.setText("create an account first");
                    }
                });

                btnService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent service = new Intent(MainActivity.this, Intent_to_User.class);
                        startActivity(service);
//                        btnSignIn.setText("create an account first");
                    }
                });

                //check remember
                String user= Paper.book().read(Common.USER_KEY);
                String pwd=Paper.book().read(Common.PWD_KEY);

                if(user!=null && pwd!=null)
                {
                    if(!user.isEmpty() && !pwd.isEmpty())
                    {
                     login(user,pwd);
                    }
                }
            }

            private void login(final String phone, final String pwd) {
                //just copy login from signin.class
                //save user and password

                //init firebase
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table_user = database.getReference("User");

                final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);//Progress bars are used to show progress of a task. For example, when you are uploading or downloading something from the internet, it is better to show the progress of download/upload to the user
                mDialog.setMessage("Please Waiting...");
                // mDialog.show();//You're trying to show a Dialog after you've exited an Activity.

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {//A DataSnapshot contains data from a Database location.Any time you read data from the Database, you receive the data as a DataSnapshot
                        //check if the user is not existed in database
                        if (dataSnapshot.child(phone).exists()) {
                            //Get user information
//                            mDialog.dismiss();//usually to respond to a click event on a button in your Dialog .
                            User user = dataSnapshot.child(phone).getValue(User.class);

                            user.setPhone(phone);

                            if (user.getPassword().equals(pwd)) {
                                //Toast.makeText(SignIn.this, "Sign in successfully!", Toast.LENGTH_SHORT).show();

                                Intent homeIntent=new Intent(MainActivity.this,intent_to_all.class);
                                Common.currentUser=user;//this is a variable to store the current user
                                startActivity(homeIntent);
                                finish();
                            } else

                            {

                                Toast.makeText(MainActivity.this, "Sign in Successfull!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
//                            mDialog.dismiss();
//                            Toast.makeText(MainActivity.this, "user not existed in database", Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


        }


