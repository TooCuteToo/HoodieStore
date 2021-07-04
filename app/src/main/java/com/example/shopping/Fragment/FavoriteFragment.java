package com.example.shopping.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopping.Adapter.ProductAdapter;
import com.example.shopping.Interface.API;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoriteFragment extends Fragment {
    RecyclerView favoriteRecycler;
    ProductAdapter productAdapter;
    Customer customer;
    BottomNavigationView navView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public BottomNavigationView getNavView() {
        return navView;
    }

    public void setNavView(BottomNavigationView navView) {
        this.navView = navView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("info");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteRecycler = view.findViewById(R.id.favorite_recycler);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
        favoriteRecycler.setLayoutManager(grid);

        List<Product> products = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), products);

        Bundle args = getArguments();
        Customer customer = (Customer) args.getSerializable("info");

        productAdapter.setCustomer(customer);
        productAdapter.setFlag(1);
        productAdapter.setNavView(navView);
        favoriteRecycler.setAdapter(productAdapter);

        Retrofit retrofit = APIHelper.buildRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Product>> call = api.getFavorite(customer.getCustomerId());

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productAdapter.setProducts(products);
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}