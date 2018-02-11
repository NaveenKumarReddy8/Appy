package com.example.naveen.appy;

import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class EmailVerification extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button mEmailResendBtn;
    private Button mEmailRegBtn;
    private Button mEmailLogoutBtn;
    private final int time = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getInstance().getCurrentUser();

        mEmailResendBtn = (Button)findViewById(R.id.email_Resend_btn);
        mEmailRegBtn = (Button)findViewById(R.id.email_Reg_btn);
        mEmailLogoutBtn = (Button)findViewById(R.id.email_logout_btn);

        mEmailRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent emailReg = new Intent(EmailVerification.this, RegisterActivity.class);
                startActivity(emailReg);
            }
        });

        mEmailLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent emailLogout = new Intent(EmailVerification.this, StartActivity.class);
                startActivity(emailLogout);
            }
        });
        mEmailResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user =mAuth.getInstance().getCurrentUser();
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EmailVerification.this, "Verification Email resent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
