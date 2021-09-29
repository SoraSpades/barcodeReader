package com.example.camerareader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private final static int REQUEST_CAMERA_PERMISSION = 201;
    private CameraSource cameraSrc;
    private SurfaceView surfaceView;
    private TextView barcodeText;
    private String barcodeData;
    private boolean scanned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        surfaceView = findViewById(R.id.surface_view);
        barcodeText = findViewById(R.id.barcode_text);

        initialize();
    }

    private void initialize() {
        BarcodeDetector barDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSrc = new CameraSource.Builder(this, barDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1080)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSrc.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(CameraActivity.this,
                                new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSrc.stop();
            }
        });

        barDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    barcodeText.post(new Runnable() {
                        @Override
                        public void run() {
                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                            } else {
                                barcodeData = barcodes.valueAt(0).displayValue;
                            }
                            if (!scanned) {
                                processCode(barcodeData);
                                scanned = true;
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Funtion to test if the code is valid and launch an activity to request data
     * @param codeData Final data of the barcode
     */
    private void processCode(String codeData) {
        boolean codeValid = true; // TODO

        if (codeValid) {
            startActivity(new Intent(this, RequestActivity.class));
        }
    }

}