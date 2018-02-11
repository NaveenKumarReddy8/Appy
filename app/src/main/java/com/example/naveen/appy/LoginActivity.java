package com.example.naveen.appy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;
    private EditText mLoginEmail;
    private EditText mLoginPassword;
    private Button mLogBtn;
    private ProgressDialog mLoginProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        mLoginProgress = new ProgressDialog(this);

        mLoginEmail = (EditText)findViewById(R.id.log_email);
        mLoginPassword = (EditText)findViewById(R.id.log_password);
        mLogBtn = (Button)findViewById(R.id.log_btn);

        mLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mLoginEmail.getText().toString().trim();
                String password = mLoginPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    mLoginProgress.setTitle("Logging in");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();
                    loginUser(email,password);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Enter the Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    mLoginProgress.dismiss();
                    Intent logIntent = new Intent(LoginActivity.this, MainActivity.class);
                    logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logIntent);
                    finish();
                }
                else{
                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this,"Cannot Sign in. Please check the form and try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
