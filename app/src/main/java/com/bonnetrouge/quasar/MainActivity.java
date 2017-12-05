package com.bonnetrouge.quasar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img_selection_view) CardView selectionView;
    @BindView(R.id.preview_image) ImageView previewImage;
    @BindView(R.id.show_button) Button showButton;
    @BindView(R.id.hide_button) Button hideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void setupImgSelector() {
        this.selectionView.setOnClickListener(view -> {
            // Let user upload image
            // Put img into previewImage
            // Save img to DB
        });
    }

    private void setupButtons() {
        this.showButton.setOnClickListener(view -> {
            // Show the overlay
        });
        this.hideButton.setOnClickListener(view -> {
            // Hide the overlay
        });
    }
}
