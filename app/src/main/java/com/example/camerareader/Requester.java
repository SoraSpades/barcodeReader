package com.example.camerareader;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Requester {

    private RequestQueue queue;
    private Context context;

    public Requester(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(this.context);
    }

    public Future<String> RequestString(String url) {
        CompletableFuture<String> future = new CompletableFuture<String>();
        queue = Volley.newRequestQueue(context);
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

}
