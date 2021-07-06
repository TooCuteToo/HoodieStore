    package com.example.shopping.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.Fragment.CartFragment;
import com.example.shopping.Fragment.CategoryFragment;
import com.example.shopping.Fragment.CategoryProductsFragment;
import com.example.shopping.Fragment.FavoriteFragment;
import com.example.shopping.Fragment.InfoFragment;
import com.example.shopping.Fragment.ProductsFragment;
import com.example.shopping.Fragment.SearchFragment;
import com.example.shopping.Interface.API;
import com.example.shopping.Model.Cart;
import com.example.shopping.Model.CartItem;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.example.shopping.Utils.Counter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

    public class HomeActivity extends AppCompatActivity {
    BottomNavigationView navView;
    Bundle bundle;
    Fragment fragment;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Hooks
        navView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        menu = navigationView.getMenu();

        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("info");

        initHeader(customer);

        bundle = new Bundle();
        bundle.putSerializable("info", customer);

        fragment = new ProductsFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.home);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(mOnDrawerNavigationItemSelectedListener);
        navigationView.setCheckedItem(R.id.nav_profile);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else { super.onBackPressed(); }
    }

    private void initHeader(Customer customer) {
        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.header_name);
        TextView headerEmail = headerView.findViewById(R.id.header_email);
        TextView headerUsername = headerView.findViewById(R.id.header_username);

        headerName.setText(customer.getName());
        headerEmail.setText(customer.getEmail());
        headerUsername.setText(customer.getUsername());
    }

    private NavigationView.OnNavigationItemSelectedListener mOnDrawerNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_profile:
                    bundle = new Bundle();
                    bundle.putSerializable("info", customer);

                    fragment = new InfoFragment();
                    fragment.setArguments(bundle);

                    FragmentTransaction transaction =
                            getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case R.id.nav_logout:
                    SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", MODE_PRIVATE).edit();
                    editor.clear().apply();

                    SharedPreferences.Editor mPrefsEditor = getSharedPreferences("CartPrefs", MODE_PRIVATE).edit();
                    mPrefsEditor.clear().apply();
                    Cart.getInstance().getItems().clear();

                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                case R.id.nav_share: Toast.makeText(HomeActivity.this, "Share", Toast.LENGTH_SHORT).show(); break;
            }

            drawerLayout.closeDrawer(GravityCompat.START); return true;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new ProductsFragment();
                    ((ProductsFragment) fragment).setNavView(navView);
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;

                case R.id.search:
                    fragment = new SearchFragment();
                    ((SearchFragment) fragment).setNavView(navView);
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;

                case R.id.category:
                    fragment = new CategoryFragment();
                    ((CategoryFragment) fragment).setNavView(navView);
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;

                case R.id.heart:
                    fragment = new FavoriteFragment();
                    ((FavoriteFragment) fragment).setNavView(navView);
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
//
                case R.id.cart:
                    CartFragment fragment = new CartFragment();
                    fragment.setArguments(bundle);
                    fragment.setNavView(navView);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences mPrefs = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        ArrayList<CartItem> items = Cart.getInstance().getItems();
        SharedPreferences.Editor prefsEditors = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        prefsEditors.putString("Cart", json);
        prefsEditors.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences mPrefs = getSharedPreferences("CartPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("Cart", null);
        Type type = new TypeToken<List<CartItem>>() {}.getType();
        List<CartItem> items = gson.fromJson(json, type);
        if (items == null) {
            items = new ArrayList<>();
        }

        Cart.getInstance().setItems((ArrayList<CartItem>) items);

        if (Cart.getInstance().calculateQuantity() != 0) {
            navView.getOrCreateBadge(R.id.cart).setNumber(Cart.getInstance().calculateQuantity());
        }

        Retrofit retrofit = APIHelper.buildRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Product>> call = api.getFavorite(customer.getCustomerId());

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    Counter.favoriteCounter = products.size();

                    if (Counter.favoriteCounter > 0) navView.getOrCreateBadge(R.id.heart).setNumber(products.size());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}