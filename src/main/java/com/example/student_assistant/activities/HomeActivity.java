package com.example.student_assistant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.student_assistant.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Animate the banner and grid
        LinearLayout banner = findViewById(R.id.banner_container);
        GridLayout grid = findViewById(R.id.home_grid);

        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeIn.setStartOffset(400); // Stagger the fade-in

        banner.startAnimation(slideDown);
        grid.startAnimation(fadeIn);

        // Set up card navigation
        CardView grammarCard = findViewById(R.id.grammar_card);
        CardView summarizerCard = findViewById(R.id.summarizer_card);
        CardView historyCard = findViewById(R.id.history_card);
        CardView profileCard = findViewById(R.id.profile_card);

        grammarCard.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, GrammarCheckerActivity.class)));
        summarizerCard.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, TextSummarizerActivity.class)));
        historyCard.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, SavedHistoryActivity.class)));
        profileCard.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
    }
}