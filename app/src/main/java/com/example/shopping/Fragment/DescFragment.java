package com.example.shopping.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopping.Model.Product;
import com.example.shopping.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DescFragment extends Fragment {
    public DescFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_desc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Product product = (Product) getArguments().getSerializable("product");

        TextView materialTxt = view.findViewById(R.id.materialTxt);
        TextView descTxt = view.findViewById(R.id.descTxt);

        materialTxt.setText(product.getMaterial());
        descTxt.setText(product.getDescription());
    }
}