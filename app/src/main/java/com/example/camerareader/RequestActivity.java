package com.example.camerareader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestActivity extends AppCompatActivity {
    public final static String urlBase = "";

    private TextView responseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        responseView = findViewById(R.id.request_activity_body);
        makeRequest("https://api.coindesk.com/v1/bpi/currentprice.json", Request.Method.GET);
    }

    private void makeRequest(String url, int method) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(method, url,
                response -> responseView.setText(response),
                error -> responseView.setText(R.string.request_error_message));

        queue.add(stringRequest);
    }
}