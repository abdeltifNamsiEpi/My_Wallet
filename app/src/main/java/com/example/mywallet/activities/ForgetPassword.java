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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    TextView ResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.forget_email);
        ResetPassword=findViewById(R.id.reset);

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String forgetEmail=email.getText().toString();

        if (forgetEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(forgetEmail).matches()){
            email.setError("Please provide valid Email");
            email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(forgetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(ForgetPassword.this,"Check your Email!!! ",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetPassword.this,MainActivity.class));
                }else {
                    Toast.makeText(ForgetPassword.this,"Try again! Something wrong happened!!! ",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}