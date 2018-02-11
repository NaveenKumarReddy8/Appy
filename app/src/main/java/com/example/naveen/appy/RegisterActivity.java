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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCreateBtn;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    private android.support.v7.widget.Toolbar mToolbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mDisplayName = (EditText) findViewById(R.id.reg_display_name);
        mEmail = (EditText) findViewById(R.id.log_email);
        mPassword = (EditText) findViewById(R.id.log_password);
        mCreateBtn = (Button) findViewById(R.id.reg_create_btn);
        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name = mDisplayName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(display_name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please Wait while we create your account");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(display_name, email, password);
                }

            }
        });
    }

    private void register_user(String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mRegProgress.dismiss();
                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Verification Email sent", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Sending verification email failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    mRegProgress.hide();
                    Toast.makeText(RegisterActivity.this,"Cannot Register. Please check the form and try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
