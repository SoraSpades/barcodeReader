package com.example.camerareader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestActivity extends AppCompatActivity {
    public final static String URLBASE = "http://192.168.1.128:3000/query?id=";
    public final static String BARCODE_EXTRA = "BARCODE_EXTRA";

    private TextView responseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        responseView = findViewById(R.id.request_activity_body);
        String barcodeData = getIntent().getStringExtra(BARCODE_EXTRA);
        makeRequest(URLBASE + barcodeData, Request.Method.GET);
    }

    private void makeRequest(String url, int method) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(method, url,
                response -> responseView.setText(response),
                error -> responseView.setText(error.toString() ));

        queue.add(stringRequest);
    }
}