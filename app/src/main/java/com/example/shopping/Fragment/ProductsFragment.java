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

import com.example.shopping.Interface.API;
import com.example.shopping.Model.Customer;
import com.example.shopping.Utils.APIHelper;
import com.example.shopping.Adapter.ProductAdapter;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.example.shopping.Utils.Dialog;
import com.example.shopping.Utils.ImageSlider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {
  private ProductAdapter productAdapter;
  private List<Product> products;
  private RecyclerView product_recycler;
  private BottomNavigationView navView;

  public ProductsFragment() {
    // Required empty public constructor
  }

  public BottomNavigationView getNavView() {
    return navView;
  }

  public void setNavView(BottomNavigationView navView) {
    this.navView = navView;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_products, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
  {
    super.onViewCreated(view, savedInstanceState);
    product_recycler = view.findViewById(R.id.product_recycler);

    GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
    product_recycler.setLayoutManager(grid);

    products = new ArrayList<>();
    productAdapter = new ProductAdapter(getContext(), products);

    Bundle args = getArguments();
    Customer customer = (Customer) args.getSerializable("info");

    productAdapter.setNavView(navView);
    productAdapter.setCustomer(customer);
    product_recycler.setAdapter(productAdapter);

    Retrofit retrofit = APIHelper.buildRetrofit();
    API api = retrofit.create(API.class);
    Call<List<Product>> call = api.getProducts();

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
        Dialog.showAlert(productAdapter.getContext(), t.toString());
      }
    });

    ImageSlider.loadImageSlider(view.findViewById(R.id.imageSlider), getContext());
  }

}