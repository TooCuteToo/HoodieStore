package com.example.shopping.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


import com.example.shopping.Adapter.ProductAdapter;
import com.example.shopping.Model.Product;
import com.example.shopping.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ArrayList<Product>  products;

    private RecyclerView product_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadProducts();
        Intent intent = getIntent();

    }

    private void loadProducts() {
//        this.products = Product.initDummyData();
        this.product_recycler = findViewById(R.id.product_recycler);
        this.productAdapter = new ProductAdapter(this, products);

        GridLayoutManager grid = new GridLayoutManager(this, 2);
        product_recycler.setLayoutManager(grid);
        product_recycler.setAdapter(productAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}