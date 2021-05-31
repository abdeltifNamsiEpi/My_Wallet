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
import com.example.mywallet.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextView signUp;
    EditText email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email=findViewById(R.id.signup_email);
        password=findViewById(R.id.signup_password);
        signUp=findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
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

    private void signUp() {

        if (!validateEmail() || !validatePassword() ){
            return;
        }

        String signUpEmail=email.getText().toString();
        String signUpPassword=password.getText().toString();



        mAuth.createUserWithEmailAndPassword(signUpEmail,signUpPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user=new User(signUpEmail,signUpPassword);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this,"User has been signed up Successfully!!! "+user.getEmail(),Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(SignUp.this,Home.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(SignUp.this,"Failed to Sign Up!!! Try again!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUp.this,"Failed to Sign Up!!! ",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}