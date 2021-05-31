package com.example.mywallet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mywallet.Expense;
import com.example.mywallet.ExpenseHomeAdapter;
import com.example.mywallet.Income;
import com.example.mywallet.IncomeHomeAdapter;
import com.example.mywallet.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView balanceMoney,incomeMoney,expenseMoney,addIncome,addExpense;
    RecyclerView RvIncome,RvExpense;
    DatabaseReference incomeRef,expenseRef;
    IncomeHomeAdapter incomeHomeAdapter;
    ExpenseHomeAdapter expenseHomeAdapter;
    List<Expense> listExpense;
    List<Income> listIncome;
    private FirebaseUser firebaseUser;
    private String userID;
    int incomeTotal=0;
    int expenseTotal=0;
    int a=1;
    int b=2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RvIncome=findViewById(R.id.income_recyclerview);
        RvExpense=findViewById(R.id.expense_recyclerview);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        balanceMoney=findViewById(R.id.balance_money);
        incomeMoney=findViewById(R.id.income_money);
        expenseMoney=findViewById(R.id.expense_money);
        addIncome=findViewById(R.id.add_income);
        addExpense=findViewById(R.id.add_expense);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home.this,IncomeCategory.class));
            }
        });

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,ExpensesCategory.class));
            }
        });

        initRvIncome();
        initRvExpense();

        balanceMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this, Balance.class);
                intent.putExtra("income",incomeMoney.getText().toString());
                intent.putExtra("expense",expenseMoney.getText().toString());
                startActivity(intent);
            }
        });


    }



    private void initRvExpense() {
        RvExpense.setLayoutManager(new LinearLayoutManager(this));
        expenseRef=FirebaseDatabase.getInstance().getReference("Expenses").child(userID);
        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listExpense=new ArrayList<>();
                for (DataSnapshot snap:snapshot.getChildren()){
                    Expense expense=snap.getValue(Expense.class);
                    expenseTotal+=Integer.parseInt(expense.getExpenseMoney());
                    listExpense.add(expense);
                }

                String stExpenseTotal=String.valueOf(expenseTotal);
                expenseMoney.setText(stExpenseTotal);
                expenseHomeAdapter=new ExpenseHomeAdapter(getApplicationContext(),listExpense);
                RvExpense.setAdapter(expenseHomeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initRvIncome() {
        RvIncome.setLayoutManager(new LinearLayoutManager(this));
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userID=firebaseUser.getUid();

        incomeRef=FirebaseDatabase.getInstance().getReference("Incomes").child(userID);
        incomeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listIncome=new ArrayList<>();
                for(DataSnapshot snap:snapshot.getChildren()){
                    Income income= snap.getValue(Income.class);
                    incomeTotal+=Integer.parseInt(income.getIncomeMoney());
                    listIncome.add(income);

                }
                String stIncomeTotal=String.valueOf(incomeTotal);
                incomeMoney.setText(stIncomeTotal);

                incomeHomeAdapter = new IncomeHomeAdapter(getApplicationContext(),listIncome);
                RvIncome.setAdapter(incomeHomeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                break;
            case R.id.nav_income:
                startActivity(new Intent(Home.this,IncomeCategory.class));
                break;
            case R.id.nav_expense:
                startActivity(new Intent(Home.this,ExpensesCategory.class));
                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(Home.this,ContactUs.class));
                break;
            case R.id.nav_rate_us:
                startActivity(new Intent(Home.this,RateUs.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this, MainActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START); 
        return true;
    }
}