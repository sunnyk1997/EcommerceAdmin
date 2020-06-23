package com.desirestodesigns.ecommerceadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.desirestodesigns.ecommerceadmin.R;
import com.desirestodesigns.ecommerceadmin.fragment.HomeFragment;
import com.desirestodesigns.ecommerceadmin.fragment.InventoryFragment;
import com.desirestodesigns.ecommerceadmin.fragment.OrdersTrackingFragment;
import com.desirestodesigns.ecommerceadmin.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BottomNavigationView mBottomNavigationView;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUi();
    }
    private void initializeUi() {
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mBottomNavigationView =findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        drawerLayout = findViewById(R.id.dl);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        navigationView = findViewById(R.id.nav_view);
        navigationDrawerLayout();

    }

    private void navigationDrawerLayout() {
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //Enable Hamburger button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        //METHOD TO PERFORM ACTIONS ON ELEMENTS IN THE NAVIGATION DRAWER
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.offers) {
//                    Intent intent = new Intent(MainActivity.this, InactiveEmployees.class);
//                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Offers", Toast.LENGTH_LONG).show();

                    Log.i(TAG, "Offers");
                } else if (id == R.id.referral) {
//                    Intent intent = new Intent(MainActivity.this,AttendanceReport.class);
//                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Referral", Toast.LENGTH_LONG).show();
                } else if (id == R.id.customer_support) {
//                    Intent i = new Intent(MainActivity.this, Categories.class);
//                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Customer Support", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Category activity is called using Intent");
                } else if (id == R.id.loyalty_points) {
//                    Intent i = new Intent(MainActivity.this, Transactions.class);
//                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Loyalty Points", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Category activity is called using Intent");
                } else if (id == R.id.share) {
//                    Intent i = new Intent(MainActivity.this, CustomerDetails.class);
//                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Category activity is called using Intent");
                }
                else if (id == R.id.rate_app) {
//                    Intent i = new Intent(MainActivity.this, Vehicles.class);
//                    startActivity(i);
                    Toast.makeText(MainActivity.this, "Rate Our App", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Category activity is called using Intent");
                }
                else if (id == R.id.about_us) {
//                    Intent i = new Intent(MainActivity.this, AddTrips.class);
//                    startActivity(i);
                    Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "Category activity is called using Intent");
                }


                //CLOSES THE NAVIGATION DRAWER

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case  R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case  R.id.nav_inventory:
                            selectedFragment = new InventoryFragment();
                            break;
                        case  R.id.nav_orders:
                            selectedFragment = new OrdersTrackingFragment();
                            break;
                        case  R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }

    //If the user isn't logged in then they will be redirected to login activity
    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser == null) {
            Intent loginIntent = new Intent(MainActivity.this, SplashScreenActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
    }
}
