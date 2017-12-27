package com.bonnetrouge.quasar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bonnetrouge.quasar.Commons.Utility;
import com.bonnetrouge.quasar.services.OverlayAccessibilityService;
import com.bonnetrouge.quasar.services.OverlayService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    @BindView(R.id.img_selection_view) CardView selectionView;
    @BindView(R.id.preview_image) ImageView previewImage;
    @BindView(R.id.show_button) Button showButton;
    @BindView(R.id.hide_button) Button hideButton;

    private AlertDialog accessibilityDialog;
    private AlertDialog overlayDialog;

    private Bitmap imgBitmap;

    private OverlayService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupImgSelector();
        setupButtons();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.service.onDestroy();
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
                updateOverlayImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        } else if ((requestCode == OverlayService.OVERLAY_REQUEST_CODE
                || requestCode == OverlayAccessibilityService.ACCESSIBILITY_REQUEST_CODE)
                && Settings.canDrawOverlays(MainActivity.this)) {
            showOverlay();
        } else if (requestCode == OverlayAccessibilityService.ACCESSIBILITY_REQUEST_CODE) {
            requestOverlayPermission();
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
            if (!Utility.isAccessibilityEnabled(getApplicationContext(),
                    OverlayAccessibilityService.ACCESSIBILITY_ID)) {
                requestAccessibilityPermission();
            } else if (!Settings.canDrawOverlays(MainActivity.this)){
                requestOverlayPermission();
            } else {
                showOverlay();
            }
        });
        this.hideButton.setOnClickListener(view -> {
            this.service.hideOverlay();
        });
    }

    private void requestAccessibilityPermission() {
        if (accessibilityDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("We use accessibility to automatically hide and show the overlay appropriately for the best user experience.")
                    .setTitle("Auto-Disable");
            builder.setPositiveButton("enable", (dialog, id) -> {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivityForResult(intent, OverlayAccessibilityService.ACCESSIBILITY_REQUEST_CODE);
            });
            builder.setNegativeButton("no thanks", (dialog, id) -> {
                dialog.dismiss();
                if (Settings.canDrawOverlays(MainActivity.this)) {
                    showOverlay();
                } else {
                    requestOverlayPermission();
                }
            });
            accessibilityDialog = builder.create();
        }
        accessibilityDialog.show();
    }

    private void requestOverlayPermission() {
        if (overlayDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Please enable us to draw over apps so the overlay can be displayed.")
                    .setTitle("Display Overlay");
            builder.setPositiveButton("enable", (dialog, id) -> {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" +getApplicationContext().getPackageName()));
                startActivityForResult(intent, OverlayService.OVERLAY_REQUEST_CODE);
            });
            builder.setNegativeButton("no thanks", (dialog, id) -> {
                dialog.dismiss();
                Toast.makeText(this, "We require the overlay permission to show the overlay :(", Toast.LENGTH_SHORT).show();
            });
            overlayDialog = builder.create();
        }
        overlayDialog.show();
    }

    private void showOverlay() {
        if (this.service == null) {
            Intent intent = new Intent(MainActivity.this, OverlayService.class);
            bindService(intent, this.connection, Context.BIND_AUTO_CREATE);
        } else {
            this.service.showOverlay();
        }
        updateOverlayImage();
    }

    private void updateOverlayImage() {
        if (this.service != null) {
            this.service.passImageToOverlay(this.imgBitmap);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OverlayService.OverlayBinder binder = (OverlayService.OverlayBinder) service;
            MainActivity.this.service = binder.getService();
            if (MainActivity.this.imgBitmap != null) {
                updateOverlayImage();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { }
    };
}
