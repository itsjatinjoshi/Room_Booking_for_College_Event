package com.example.roombooking;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.roombooking.Utils.PreferenceUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public int OpenDraw, closeDraw;
    FloatingActionButton fab;
    private static final String TAG = "HomeActivity";
    String firstName, lastName, username, password, email, department;
    int phoneNumber, facilityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        SharedPreferences sharedpref1 = getApplication().getSharedPreferences("userName", MODE_PRIVATE);
        facilityId = sharedpref1.getInt("id",0);
        firstName = sharedpref1.getString("firstName", ""); //
        lastName = sharedpref1.getString("lastName", "");
        username = sharedpref1.getString("username", "");
        password = sharedpref1.getString("password", "");
        email = sharedpref1.getString("email", "");
        phoneNumber = sharedpref1.getInt("phoneNumber", 0);
        department = sharedpref1.getString("department", "");

        Log.d(TAG, "Firstname : " + firstName);
        Log.d(TAG, "Email : " + email);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("emailKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("firstName", firstName);
                editor.putString("email", email);
                editor.apply();
                Intent composeEmailActivity = new Intent(HomeActivity.this, ComposeEmail.class);
                startActivity(composeEmailActivity);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        try {
            OpenDraw = Integer.parseInt("Open Navigation drawer");
            closeDraw = Integer.parseInt("Close navigation drawer");
        } catch (Exception e) {
            e.getMessage();
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, OpenDraw, closeDraw);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.isDrawerOpen(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("HOME_LOG", "NAVIGATION MENU");
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent viewCart = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(viewCart);
        } else if (id == R.id.nav_inbox) {
            SharedPreferences sharedPref = getSharedPreferences("inbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("email", email);
            editor.apply();
            System.out.println("ID for Cart : " + id);
            Intent inboxEmails = new Intent(HomeActivity.this, InboxEmails.class);
            startActivity(inboxEmails);
            Log.d("HOME_ACTIVITY", "INBOX ACTIVITY");

        } else if (id == R.id.nav_approved) {
            System.out.println("ID for Order : " + id);
//            Intent orderStatus = new Intent(HomeActivity.this, GalleryFragment.class);
//            startActivity(orderStatus);
            Log.d("HOME_ACTIVITY", "INBOX APPROVED");

        } else if (id == R.id.nav_pending) {
            SharedPreferences sharedPref = getSharedPreferences("pendingRequests", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("id", facilityId);
            editor.putString("firstName", firstName);
            editor.putString("lastName", lastName);
            editor.apply();
            Intent newRoomBooking = new Intent(HomeActivity.this, PendingBookingRequests.class);
            startActivity(newRoomBooking);
            Log.d("HOME_ACTIVITY", "PENDING");

        } else if (id == R.id.nav_available) {
            SharedPreferences sharedPref = getSharedPreferences("bookRoomForActivity", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("id", facilityId);
            editor.putString("firstName", firstName);
            editor.putString("lastName", lastName);
            editor.apply();
            Intent newRoomBooking = new Intent(HomeActivity.this, NewRoomBooking.class);
            startActivity(newRoomBooking);
            Log.d("HOME_ACTIVITY", "New Room Booking");

        } else if (id == R.id.nav_setting) {
            System.out.println("ID for Order : " + id);
//            Intent orderStatus = new Intent(HomeActivity.this, OrderStatus.class);
//            startActivity(orderStatus);
            Log.d("HOME_ACTIVITY", "SETTING");

        } else if (id == R.id.nav_logout) {
            logout();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {
        System.out.println(" Logout METHOD CALLED: ");
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("LOGOUT !!!");
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Are You Sure want to Logout");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PreferenceUtils.savePassword(null, getApplicationContext());
                PreferenceUtils.saveUsername(null, getApplicationContext());
                Intent intent = new Intent(HomeActivity.this, LoginUser.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
        Log.d("HOME_LOG", "HOME LOGOUT");
    }


}
