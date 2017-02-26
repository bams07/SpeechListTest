package com.bams.android.examenspeechtotext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchListProductsActivity extends AppCompatActivity {

    ArrayList<Product> listProducts;
    ListView listViewProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listViewProducts = (ListView) findViewById(R.id.listProducts);
        listProducts = new ArrayList<>();
        listProducts = ReportsActivity.searchListProducts;

        /**
         * Set adapter to listView
         */
        ProductListAdapter adapter = new ProductListAdapter(this, listProducts);
        listViewProducts.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}