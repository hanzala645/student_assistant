package com.example.student_assistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.student_assistant.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Load Animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        // Get Views
        ImageView logo = findViewById(R.id.splash_logo);
        TextView title = findViewById(R.id.splash_title);

        // Start Animations
        logo.startAnimation(fadeIn);
        title.startAnimation(slideUp);

        // Handler to start the next activity
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(i);
            // Apply a fade transition to the next activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, SPLASH_TIME_OUT);
    }
}