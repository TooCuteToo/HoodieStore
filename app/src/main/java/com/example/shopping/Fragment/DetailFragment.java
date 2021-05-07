package com.example.shopping.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopping.Adapter.OrderPageAdapter;
import com.example.shopping.Model.Cart;
import com.example.shopping.Model.CartItem;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private ViewPager2 viewPager2;
    private BottomNavigationView navView;

    public DetailFragment(BottomNavigationView navView) {
        // Required empty public constructor
        this.navView = navView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Product product = (Product) getArguments().getSerializable("product");

        Animation topAnim = AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation);

        viewPager2 = view.findViewById(R.id.pager);
        viewPager2.setAdapter(new OrderPageAdapter(getActivity(), product));

        TextView productName = view.findViewById(R.id.product_title2);
        TextView productID = view.findViewById(R.id.productID);
        TextView productPrice = view.findViewById(R.id.detailPrice);
        ImageView productImg = view.findViewById(R.id.productImg);
        Button buyBtn = view.findViewById(R.id.buyBtn);

        productName.setAnimation(topAnim);
        productImg.setAnimation(bottomAnim);

        productName.setText(product.getName());
        productID.setText("SP" + product.getId() + " | " + product.getTypeId());
        productPrice.setText("$" + String.valueOf((product.getPrice())));
        Glide.with(view).load("file:///android_asset/Image/" + product.getImg()).into((ImageView)view.findViewById(R.id.productImg));

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Description");
                        break;
                    case 1:
                        tab.setText("Shipping");
                        break;
                    case 2:
                        tab.setText("Payment");
                        break;
                }
            }
        });

        tabLayoutMediator.attach();

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem item = new CartItem(
                        product.getId(),
                        product.getTypeId(),
                        product.getImg(),
                        product.getName(),
                        1,
                        Float.parseFloat(String.valueOf(product.getPrice()))
                );

                Cart.getInstance().addItem(item);
                SharedPreferences mPrefs = getContext().getSharedPreferences("CartPrefs", MODE_PRIVATE);
                SharedPreferences.Editor prefsEditors = mPrefs.edit();

                Gson gson = new Gson();
                String json = gson.toJson(Cart.getInstance().getItems());

                prefsEditors.putString("Cart", json);
                prefsEditors.apply();

                int count = Cart.getInstance().calculateQuantity();

                if (count != 0) {
                    navView.getOrCreateBadge(R.id.cart).setNumber(count);
                    navView.getOrCreateBadge(R.id.cart).setVisible(true);
                }
            }
        });
    }
}