package com.example.shopping.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.shopping.Adapter.ProductAdapter;
import com.example.shopping.Interface.API;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private BottomNavigationView navView;
    private ProductAdapter productAdapter;
    private List<Product> products;
    private Customer customer;
    private RecyclerView search_recycler;

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        search_recycler = view.findViewById(R.id.search_recycler);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
        search_recycler.setLayoutManager(grid);

        products = new ArrayList<>();

        Bundle args = getArguments();
        Customer customer = (Customer) args.getSerializable("info");

        productAdapter = new ProductAdapter(getContext(), products);
        productAdapter.setCustomer(customer);
        productAdapter.setNavView(navView);
        search_recycler.setAdapter(productAdapter);

//        EditText searchTxt = view.findViewById(R.id.searchTxt);

        SearchView searchTxt = view.findViewById(R.id.searchTxt);
        searchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTxt.setIconified(false);
            }
        });

        searchTxt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchAPI(newText);
                return false;
            }
        });

        searchTxt.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getActivity().onBackPressed();
                return false;
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.commit();
    }

    private void fetchAPI(String name) {
        if (name.isEmpty()){
            search_recycler.setVisibility(View.GONE);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API productsAPI = retrofit.create(API.class);

        Call<List<Product>> call = productsAPI.getProduct(name);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    products = response.body();

                    if (products.size() > 0){
                        search_recycler.setVisibility(View.VISIBLE);
                    }

                    productAdapter.setProducts(products);
                    productAdapter.notifyDataSetChanged();
                }
                else return;
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    private void searchFurniture(String newText) {
//        Toast.makeText(this, tmp.size()+"", Toast.LENGTH_SHORT).show();


    }


}