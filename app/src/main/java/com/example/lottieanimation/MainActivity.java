package com.example.lottieanimation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private Button btnPlay, btnPause, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        animationView = findViewById(R.id.animationView);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        // Load animation from assets folder
        // Make sure to place your animation.json file in the assets folder
        animationView.setAnimation("animation.json");

        // Set click listeners for buttons
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAnimation();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAnimation();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAnimation();
            }
        });
    }

    private void playAnimation() {
        animationView.playAnimation();
    }

    private void pauseAnimation() {
        animationView.pauseAnimation();
    }

    private void resetAnimation() {
        animationView.cancelAnimation();
        animationView.setProgress(0f);
    }
}