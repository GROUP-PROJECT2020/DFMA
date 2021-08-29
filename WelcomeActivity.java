package com.example.dfma_app_656995;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        Button welcomeButton = findViewById(R.id.welcome_bt);
        welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeOnButtonClick();
            }
        });
    }

    public void welcomeOnButtonClick() {
        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(i);
    }
}
