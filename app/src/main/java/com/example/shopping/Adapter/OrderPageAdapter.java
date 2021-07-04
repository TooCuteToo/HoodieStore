package com.example.shopping.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopping.Fragment.DescFragment;
import com.example.shopping.Fragment.PaymentFragment;
import com.example.shopping.Fragment.ShippingFragment;
import com.example.shopping.Model.Product;

public class OrderPageAdapter extends FragmentStateAdapter {
    private Product product;

    public OrderPageAdapter(@NonNull FragmentActivity fragmentActivity, Product product) {
        super(fragmentActivity);
        this.product = product;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);

                DescFragment descFragment = new DescFragment();
                descFragment.setArguments(bundle);

                return descFragment;
            case 1:
                return new ShippingFragment();

            default:
                return new PaymentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
