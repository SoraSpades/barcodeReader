package com.example.camerareader;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

public class insertCode extends AppCompatActivity {

    EditText codeEditText;
    Button sendCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_code);

        codeEditText = findViewById(R.id.insertCode_EditText);
        sendCodeButton=findViewById(R.id.insertCode_Button);

        sendCodeButton.setOnClickListener((view) -> {
            String code = codeEditText.getText().toString();
            if (!code.equals("") && Product.validateCode(code)) {
                Intent intent = new Intent(this, RequestActivity.class);
                intent.putExtra(RequestActivity.BARCODE_EXTRA, code);
                activityLauncher.launch(intent);            }
        });
    }

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK) {
                        Intent data = result.getData();
                        setResult(Activity.RESULT_OK, data);
                        finish();
                    }
                }
            });

}