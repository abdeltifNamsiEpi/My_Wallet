package com.example.mywallet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywallet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView login,forgot_password,signUp;
    private FirebaseAuth mAuth;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=findViewById(R.id.login);
        forgot_password=findViewById(R.id.forgot_password);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signUp=findViewById(R.id.main_sign_up);
        mAuth = FirebaseAuth.getInstance();


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgetPassword.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEmail() || !validatePassword()){
                    return;
                }

                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();

                login(userEmail,userPassword);
            }
        });


    }



    public Boolean validateEmail(){
        String val=email.getText().toString();
        if (val.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()){
            email.setError("Please provide valid Email");
            email.requestFocus();
            return false;
        }
        else{
            email.setError(null);
            return true;
        }

    }

    public Boolean validatePassword(){
        String val=password.getText().toString();
        if(val.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return false;
        }
        else if(val.length()<6){
            password.setError("Password should at least have 6 characters");
            password.requestFocus();
            return false;
        }
        else{
            password.setError(null);
            return true;
        }

    }

    private void login(String email, String password) {


        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"Authentication Success "+user.getEmail(),Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(MainActivity.this,Home.class);
                            startActivity(i);

                        }
                        else{

                            Toast.makeText(MainActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();


                        }



                    }
                });
    }
}