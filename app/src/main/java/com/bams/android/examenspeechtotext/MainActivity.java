package com.bams.android.examenspeechtotext;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADDNAME = 1;
    public final static String EXTRA_MESSAGE = "products";

    public static ArrayList<Product> listProducts;
    Button btnAddProduct, btnAddName, btnCleanName, btnGoToProducts, btnReports;
    EditText txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listProducts = new ArrayList<Product>();
        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        btnAddName = (Button) findViewById(R.id.btnAddName);
        btnCleanName = (Button) findViewById(R.id.btnCleanName);
        btnGoToProducts = (Button) findViewById(R.id.btnGoToProducts);
        btnReports = (Button) findViewById(R.id.btnReports);
        txtName = (EditText) findViewById(R.id.txtName);

        btnGoToProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListProductsActivity.class);
                intent.putExtra(EXTRA_MESSAGE, listProducts);
                startActivity(intent);

            }
        });
        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportsActivity.class);
                startActivity(intent);
            }
        });

        btnCleanName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.getText().clear();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                String name = txtName.getText().toString();

                txtName.getText().clear();

                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "REQUIRED FIELDS", Toast.LENGTH_SHORT).show();
                    return;
                }

                listProducts.add(new Product(name, "PENDING", date.getTime()));
                Toast.makeText(getApplicationContext(), "NEW PRODUCT ADDED", Toast.LENGTH_SHORT).show();

            }
        });

        btnAddName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMessage(REQUEST_CODE_ADDNAME);
            }
        });

    }

    public void setMessage(int REQUEST_CODE) {
        if (isConnected()) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    /**
     * @see https://developer.android.com/training/basics/intents/result.html?hl=es
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String txtData = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0).toString();
            switch (requestCode) {
                case REQUEST_CODE_ADDNAME:
                    txtName.setText(txtData);
                    break;
            }

        } else {
            Toast.makeText(getApplicationContext(), "ERROR TO GET DATA", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Check connection
     *
     * @return
     */
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }


}
