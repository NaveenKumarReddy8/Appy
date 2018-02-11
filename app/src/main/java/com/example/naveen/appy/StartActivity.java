package com.example.naveen.appy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button mStartRegBtn;
    private Button mStartLogBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mStartRegBtn = (Button)findViewById(R.id.start_reg_btn);
        mStartLogBtn = (Button)findViewById(R.id.start_log_btn);

        mStartRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(reg_intent);
            }
        });

        mStartLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log_intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(log_intent);
            }
        });
    }
}
