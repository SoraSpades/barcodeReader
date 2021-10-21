package com.example.camerareader;

import androidx.appcompat.app.AppCompatActivity;

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
            if (!code.equals("")) {
                Intent intent = new Intent(this, RequestActivity.class);
                intent.putExtra(RequestActivity.BARCODE_EXTRA, code);
                startActivity(intent);
            }
        });
    }


}