package com.example.camerareader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

    private Requester requester;
    private Product product;

    // Display containers
    private ImageView imageView;
    private TextView nameTextView, priceTextView, QuantityTextView;
    private Button backButton, addToListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        // Variable Initialization
        nameTextView     = findViewById(R.id.titleTextView);
        priceTextView    = findViewById(R.id.priceTextView);
        QuantityTextView = findViewById(R.id.quantityTextView);
        backButton       = findViewById(R.id.backButton);
        addToListButton  = findViewById(R.id.addtolistButton);
        imageView        = findViewById(R.id.product_image_view);

        String barcodeData = getIntent().getStringExtra(BARCODE_EXTRA);
        requester = new Requester(this);

        // Button Listeners
        backButton.setOnClickListener((view)->{
            finish();
        });


        // Make Request
        CompletableFuture<String> future = requester.requestString(URLBASE + QUERYSUFFIX + barcodeData);
        future.thenRun(()->{
            try {
                String response = future.get(); // SERVER RESPONSE TODO: Parse data
                product = new Product(new JSONObject(response));
                responseView.setText(response);
                CompletableFuture<Bitmap> imgFuture = requester.requestBitmap(URLBASE + product.getImgPath());
                imgFuture.thenRun(()->{
                    try {
                        Bitmap image = imgFuture.get();
                        product.setImgBitmap(image);
                        imageView.setImageBitmap(image);
                    } catch (Exception e) {
                        Log.d("IMG_RESPONSE", "Exception: " + e.toString());
                    }
                });
            } catch (Exception e) {
                Log.d("RESPONSE", "Exception: " + e.toString());
            }
        });
    }
}