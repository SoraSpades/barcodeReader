package com.example.camerareader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ProductList pList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pList = new ProductList();

        setContentView(R.layout.activity_main);

        findViewById(R.id.launch_camera_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, CameraActivity.class));
                    }
                }
        );

        findViewById(R.id.insertCode_Activity_button).setOnClickListener(view->{
            startActivity(new Intent(MainActivity.this, insertCode.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (pList.getLength()>0) {
            findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
            String products="", prices="";
            for (Product p: pList.getList()) {
                products += p.getName() + '\n';
                prices += Double.toString(p.getPrice()) + '€' + '\n';
            }
            ((TextView)findViewById(R.id.productListNames)).setText(products);
            ((TextView)findViewById(R.id.productListPrices)).setText(prices);
        } else {
            findViewById(R.id.listLayout).setVisibility(View.GONE);
        }
    }

    public void changeTheme(View view) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}