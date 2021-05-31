package com.example.mywallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mywallet.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Balance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String incomeIntent,expenseIntent;
    TextView balance;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        balance=findViewById(R.id.balance_money_total);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Intent intent= getIntent();
        incomeIntent=intent.getStringExtra("income");
        expenseIntent=intent.getStringExtra("expense");
        int total=Integer.parseInt(incomeIntent)-Integer.parseInt(expenseIntent);
        String stTotal=String.valueOf(total);
        balance.setText(stTotal);



    }
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                startActivity(new Intent(this,Home.class));
                break;
            case R.id.nav_income:
                startActivity(new Intent(this,IncomeCategory.class));
                break;
            case R.id.nav_expense:
                startActivity(new Intent(this,ExpensesCategory.class));
                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(this,ContactUs.class));
                break;
            case R.id.nav_rate_us:
                startActivity(new Intent(this,RateUs.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
