package com.bonnetrouge.quasar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img_selection_view) CardView selectionView;
    @BindView(R.id.preview_image) ImageView previewImage;
    @BindView(R.id.show_button) Button showButton;
    @BindView(R.id.hide_button) Button hideButton;

    private Bitmap imgBitmap;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupImgSelector();
        setupButtons();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            Uri uri = data.getData();

            try {
                this.imgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                previewImage.setImageBitmap(this.imgBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupImgSelector() {
        this.selectionView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
