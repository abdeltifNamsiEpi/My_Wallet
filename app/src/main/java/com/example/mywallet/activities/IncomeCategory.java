package com.example.mywallet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywallet.Category;
import com.example.mywallet.CategoryAdapter;
import com.example.mywallet.Income;
import com.example.mywallet.R;
import com.example.mywallet.User;
import com.example.mywallet.interfaces.RecyclerViewClickInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IncomeCategory extends AppCompatActivity implements RecyclerViewClickInterface , NavigationView.OnNavigationItemSelectedListener{
    Category[] categories=new Category[7];
    private FirebaseUser firebaseUser;
    private String userID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_category);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        RecyclerView recyclerView=findViewById(R.id.income_recyclerview_id);
        categories[0]=new Category("Salary", R.drawable.salary);
        categories[1]=new Category("Refunds", R.drawable.refund);
        categories[2]=new Category("Coupons", R.drawable.coupon);
        categories[3]=new Category("Rental", R.drawable.house);
        categories[4]=new Category("Grants", R.drawable.grant);
        categories[5]=new Category("Awards", R.drawable.trophy);
        categories[6]=new Category("Gambling", R.drawable.slot_machine);



        CategoryAdapter adapter=new CategoryAdapter(categories,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        AlertDialog.Builder builder=new AlertDialog.Builder(IncomeCategory.this);
        View mView=getLayoutInflater().inflate(R.layout.fragment_income,null);
        final TextView fragmentCategory=(TextView)mView.findViewById(R.id.fragment_income_category);
        final TextView fragmentAmout=(TextView)mView.findViewById(R.id.fragment_income);
        final TextView fragmentValidate=(TextView)mView.findViewById(R.id.fragment_income_validate);
        fragmentCategory.setText(categories[position].toString());

        fragmentValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                userReference= FirebaseDatabase.getInstance().getReference("Users");
                userID=firebaseUser.getUid();
                userReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user=snapshot.getValue(User.class);
                        if(user!=null){

                            String incomeMoney=fragmentAmout.getText().toString();
                            String uid=firebaseUser.getUid();
                            String uemail=user.getEmail();
                            String incomeCategory=fragmentCategory.getText().toString();
                            Income income = new Income(uid,uemail,incomeCategory,incomeMoney);
                            firebaseDatabase.getInstance().getReference("Incomes").child(userID).push().setValue(income).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(IncomeCategory.this,"Income Amount added successfuly!!!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(IncomeCategory.this,Home.class));
                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        builder.setView(mView);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();




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
                startActivity(new Intent(IncomeCategory.this,Home.class));
                break;
            case R.id.nav_income:
                break;
            case R.id.nav_expense:
                startActivity(new Intent(IncomeCategory.this,ExpensesCategory.class));
                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(IncomeCategory.this,ContactUs.class));

                break;
            case R.id.nav_rate_us:
                startActivity(new Intent(IncomeCategory.this,RateUs.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(IncomeCategory.this, MainActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}