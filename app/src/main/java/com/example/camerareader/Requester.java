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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Requester {

    private final RequestQueue queue;
    private final Context context;

    public Requester(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(this.context);
    }

    public CompletableFuture<String> requestString(String url) {
        CompletableFuture<String> future = new CompletableFuture<>();
        // Response callback
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Log.d("RESPONSE", response);
                    future.complete(response);
                },
//                future::complete, // response -> future.complete(response)
                error -> future.cancel(true) // Error callback
        );
        queue.add(stringRequest);
        return future;
    }
}
