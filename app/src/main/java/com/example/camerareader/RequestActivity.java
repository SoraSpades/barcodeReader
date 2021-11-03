package com.example.camerareader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class RequestActivity extends AppCompatActivity {
    public final static String URLBASE = "https://codebarreaderserver.herokuapp.com/";
    public final static String QUERYSUFFIX = "query?id=";
    public final static String BARCODE_EXTRA = "BARCODE_EXTRA";

    private Requester requester;
    private Product product;
    private String response;

    // Display containers
    private ImageView imageView;
    private TextView nameTextView, priceTextView, QuantityTextView;
    private Button backButton, addToListButton;
    private ConstraintLayout mainLayout;

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
        mainLayout       = findViewById(R.id.activityRequestMainLayout);

        String barcodeData = getIntent().getStringExtra(BARCODE_EXTRA);
        requester = new Requester(this);

        // Button Listeners
        backButton.setOnClickListener((view)->{
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
        addToListButton.setOnClickListener(view->{
            Intent intent = new Intent();
            intent.putExtra(MainActivity.PRODUCT_EXTRA, response);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });


        // Make Request
        CompletableFuture<String> future = requester.requestString(URLBASE + QUERYSUFFIX + barcodeData);
        future.thenRun(()->{
            try {
                response = future.get(); // SERVER RESPONSE TODO: Parse data
                product = new Product(new JSONObject(response));
                display(product);
                CompletableFuture<Bitmap> imgFuture = requester.requestBitmap(URLBASE + product.getImgPath());
                imgFuture.thenRun(()->{
                    try {
                        Bitmap image = imgFuture.get();
                        product.setImgBitmap(image);
                        imageView.setImageBitmap(image);
                        mainLayout.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Log.d("IMG_RESPONSE", "Exception: " + e.toString());
                    }
                });
            } catch (Exception e) {
                Log.d("RESPONSE", "Exception: " + e.toString());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void display(@NonNull Product p) {
        nameTextView.setText(p.getName());
        priceTextView.setText(p.getPrice() + "€");
        // QuantityTextView.setText(p.getQuantity());
    }
}