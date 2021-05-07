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

import com.example.shopping.Adapter.CategoryAdapter;
import com.example.shopping.Adapter.ProductAdapter;
import com.example.shopping.Interface.API;
import com.example.shopping.Model.Category;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.example.shopping.Utils.ImageSlider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private CategoryAdapter categoryAdapter;
    private List<Category> categories;
    private RecyclerView category_recycler;
    private BottomNavigationView navView;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryAdapter getCategoryAdapter() {
        return categoryAdapter;
    }

    public void setCategoryAdapter(CategoryAdapter categoryAdapter) {
        this.categoryAdapter = categoryAdapter;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        category_recycler = view.findViewById(R.id.category_recycler);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
        category_recycler.setLayoutManager(grid);

        Bundle args = getArguments();
        Customer customer = (Customer) args.getSerializable("info");

        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categories);
        categoryAdapter.setCustomer(customer);

        category_recycler.setAdapter(categoryAdapter);
        APIHelper.fetchCategories(categoryAdapter);
    }
}