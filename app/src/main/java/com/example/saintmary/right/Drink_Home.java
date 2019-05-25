
package com.example.saintmary.right;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.saintmary.right.Common.Common;
        import com.example.saintmary.right.Interface.ItemClickListener;
        import com.example.saintmary.right.Model.Category;
        import com.example.saintmary.right.Model.DrinkCategory;
//        import com.example.saintmary.right.Service.ListenOrder;
        import com.example.saintmary.right.ViewHolder.DrinkMenuViewHolder;
        import com.example.saintmary.right.ViewHolder.MenuViewHolder;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.squareup.picasso.Picasso;

        import java.util.HashMap;
        import java.util.Map;

        import dmax.dialog.SpotsDialog;
        import io.paperdb.Paper;

public class Drink_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference category;


    TextView txtFullName;

    RecyclerView drink_recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink__home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu Management");
        setSupportActionBar(toolbar);


        //initialize firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("DrinkCategory");

        Paper.init(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent cartIntent=new Intent(Drink_Home.this,DrinkCart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drink_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.drink_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //set Name for user
//        View headerView=navigationView.getHeaderView(0);//this is error please set the index to 1 or others
//        txtFullName=headerView.findViewById(R.id.txtFullName);
//        txtFullName.setText(Common.currentUser.getName()); //please correct it i commented latter


        drink_recycler_menu=(RecyclerView)findViewById(R.id.drink_recycler_menu);//this is foud in the content_home layout
        drink_recycler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);//ezi malet eti recycler view linear layout kkon alewo nmbal eyu
        drink_recycler_menu.setLayoutManager(layoutManager);

        loadMenu();//this is a function to load a menu

        //Register Service
//        Intent service=new Intent(Drink_Home.this, ListenOrder.class);
//        startService(service);

    }

    private void loadMenu() {

        adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
                Category.class,
                R.layout.menu_item,
                MenuViewHolder.class,
                category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {//in this case the by default created in the model parameter wa category  and the viewHolder was menuViewHolder


                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())//i am happy here because of the error that i have corrected
                        .into(viewHolder.imageView);
                final Category clickItem=model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view,int position, boolean isLongClick) {//this number of parameter must be equal with the ItemClickListener interface
//                        Toast.makeText(Home.this,""+clickItem.getName(),Toast.LENGTH_SHORT).show();
//                        Get CategoryId and send to new Activity
                        Intent foodList=new Intent(Drink_Home.this,Drink_list.class);

                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);


                    }
                });



            }
        };
        drink_recycler_menu.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drink_drawer_layout);//this is very important please note that
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*  // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
//    Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/TIMES.TTF");


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drink_nav_menu) {
//            drink.setTypeface(typeface);

        } else if (id == R.id.dink_nav_cart) {
            Intent cartIntent=new Intent(Drink_Home.this,DrinkCart.class);
            startActivity(cartIntent);
        } else if (id == R.id.drink_nav_orders) {
            Intent orderIntent=new Intent(Drink_Home.this,DrinkOrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_log_out) {

            //Delete Remember user and password
            Paper.book().destroy();
            //logout
            Intent signIn=new Intent(Drink_Home.this,SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);

        }
        else if(id == R.id.nav_change_pwd)
        {
            showChangePasswordDialog();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drink_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showChangePasswordDialog() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Drink_Home.this);
        alertDialog.setTitle("Change Password");
        alertDialog.setMessage("please Fill all Information");

        LayoutInflater inflater=LayoutInflater.from(this);
        View layout_pwd=inflater.inflate(R.layout.change_password_layout,null);

        final EditText edtPassword=layout_pwd.findViewById(R.id.edtPassword);
        final EditText edtNewPassword=layout_pwd.findViewById(R.id.edtNewPassword);
        final EditText edtRepeatPassword=layout_pwd.findViewById(R.id.edtRepeatPassword);

        alertDialog.setView(layout_pwd);

        //Button
        alertDialog.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //change password here

                //for use SpotDialog please use AlertDialog from android app not from v7 like above AlertDialog
                final android.app.AlertDialog waitingDialog=new SpotsDialog(Drink_Home.this);
                waitingDialog.show();

                //check old password
                if(edtPassword.getText().toString().equals(Common.currentUser.getPassword()))
                {
                    //check new password and the Repeat password or confirm password
                    if (edtNewPassword.getText().toString().equals(edtRepeatPassword.getText().toString()))
                    {

                        Map<String,Object> passwordUpdate=new HashMap<>();
                        //password must be equal with the database field in firebase database
                        passwordUpdate.put("password",edtNewPassword.getText().toString());

                        //make update
                        DatabaseReference user=FirebaseDatabase.getInstance().getReference("User");
                        user.child(Common.currentUser.getPhone())
                                .updateChildren(passwordUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        waitingDialog.dismiss();
                                        Toast.makeText(Drink_Home.this,"Password is updated",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Drink_Home.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });


                    }
                    else
                    {
                        Toast.makeText(Drink_Home.this, "New password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Drink_Home.this,"Wrong Old password",Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}



