package com.example.lookbook1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonPrevious, buttonNext;
    private int[] imageResources = {R.drawable.cat, R.drawable.dog1, R.drawable.dog2};
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);

        updateImageView();
    }

    public void showPreviousImage(View view) {
        currentImageIndex = (currentImageIndex - 1 + imageResources.length) % imageResources.length;
        updateImageView();
    }

    public void showNextImage(View view) {
        currentImageIndex = (currentImageIndex + 1) % imageResources.length;
        updateImageView();
    }

    private void updateImageView() {
        imageView.setImageResource(imageResources[currentImageIndex]);
    }
}
