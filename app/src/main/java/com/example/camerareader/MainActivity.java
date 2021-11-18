package com.example.camerareader;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ProductList pList;
    public static final String PRODUCT_EXTRA = "ProdExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pList = new ProductList();

        setContentView(R.layout.activity_main);

        findViewById(R.id.launch_camera_button).setOnClickListener(
                view -> activityLauncher.launch(new Intent(MainActivity.this, CameraActivity.class))
        );

        findViewById(R.id.insertCode_Activity_button).setOnClickListener(view-> activityLauncher.launch(new Intent(MainActivity.this, insertCode.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshListView();
    }

    private void refreshListView() {
        if (pList.getLength()>0) {
            findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
            String products="", prices="";
            for (Product p: pList.getList()) {
                int quantity = pList.getQuantity(p);
                products += ((quantity == 1)? "" : quantity + "x ") + p.getName() + '\n';
                prices += Double.toString(p.getPrice() * quantity) + '€' + '\n';
            }
            ((TextView)findViewById(R.id.productListNames)).setText(products);
            ((TextView)findViewById(R.id.productListPrices)).setText(prices);
        } else {
            findViewById(R.id.listLayout).setVisibility(View.GONE);
        }

        String total = "Comprar: " + pList.calculateTotal() + " €";

        ((TextView)findViewById(R.id.totalListButton)).setText(total);
    }

    public void changeTheme(View view) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()== Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        try {
                            pList.addElement(new Product(new JSONObject(data.getStringExtra(PRODUCT_EXTRA))));
                            refreshListView();
                        } catch (Exception e) {
                            Log.e("JSONException", e.toString());
                        }
                    }
                }
//                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    // Cancelled Request
//                    }
            });
}