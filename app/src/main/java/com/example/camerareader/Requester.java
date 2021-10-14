package com.example.camerareader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.CompletableFuture;

public class Requester {

    private final RequestQueue queue;

    public Requester(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public CompletableFuture<String> requestString(String url) {
        CompletableFuture<String> future = new CompletableFuture<>();
        // Response callback
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                future::complete, // response -> future.complete(response)
                error -> future.cancel(true) // Error callback
        );
        queue.add(stringRequest);
        return future;
    }

    public CompletableFuture<Bitmap> requestBitmap(String url) {
        CompletableFuture<Bitmap> future = new CompletableFuture<>();
        Log.d("REQUEST", "Requesting to " + url);
        ImageRequest imageRequest = new ImageRequest(
                url,
                future::complete, // Complete callback
                1000, 1000, // Image dimensions
                ImageView.ScaleType.CENTER_CROP, // Align type
                Bitmap.Config.RGB_565, // Color space
                error -> future.cancel(true) // Cancel callback
        );
        queue.add(imageRequest);
        return future;
    }
}
