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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.shopping.Adapter.ProductAdapter;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.example.shopping.Utils.ImageSlider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductsFragment extends Fragment {
    private ProductAdapter productAdapter;
    private RecyclerView categoryProductsReycler;
    private List<Product> products;
    private BottomNavigationView navView;

    public CategoryProductsFragment() {
        // Required empty public constructor
    }

    public ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    public void setProductAdapter(ProductAdapter productAdapter) {
        this.productAdapter = productAdapter;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        Animation leftAnim = AnimationUtils.loadAnimation(getContext(), R.anim.left_animation);
        TextView cateProducstTitle = view.findViewById(R.id.cate_products_title);

        cateProducstTitle.setText(args.getString("categoryTitle"));
        cateProducstTitle.setAnimation(leftAnim);

        categoryProductsReycler = view.findViewById(R.id.category_products_recycler);
        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
        categoryProductsReycler.setLayoutManager(grid);

        products = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), products);

        Customer customer = (Customer) args.getSerializable("info");

        productAdapter.setNavView(navView);
        productAdapter.setCustomer(customer);
        categoryProductsReycler.setAdapter(productAdapter);

        APIHelper.fetchProductsByCategory(args.getString("typeId"), productAdapter);
    }
}