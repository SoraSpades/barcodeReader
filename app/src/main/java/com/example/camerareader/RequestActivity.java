package com.example.camerareader;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RequestActivity extends AppCompatActivity {
    public final static String URLBASE = "https://codebarreaderserver.herokuapp.com/";
    public final static String QUERYSUFFIX = "query?id=";
    public final static String BARCODE_EXTRA = "BARCODE_EXTRA";

    private TextView responseView;
    private ImageView imageView;
    private Requester requester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        // Variable Initialization
        responseView = findViewById(R.id.request_activity_body);
        imageView = findViewById(R.id.product_image_view);
        String barcodeData = getIntent().getStringExtra(BARCODE_EXTRA);
        requester = new Requester(this);

        // Make Request
        CompletableFuture<String> future = requester.requestString(URLBASE + QUERYSUFFIX + barcodeData);
        future.thenRun(()->{
            try {
                String response = future.get();
                responseView.setText(response);
            } catch (Exception e) {
                Log.d("RESPONSE", "Exception: " + e.toString());
            }
        });
    }

//    private void makeImageRequest(String image) {
//        ImageRequest imageRequest = new ImageRequest(
//                URLBASE + image,
//                response -> { // Callback
//                    imageView.setImageBitmap(response);
//                },
//                0, 0, // Image dimensions
//                ImageView.ScaleType.CENTER_CROP, // Align type
//                Bitmap.Config.RGB_565, // Color space
//                error -> Log.d("Error in Image Request", error.toString()) // Error callback
//        );
//        queue.add(imageRequest);
//    }
}