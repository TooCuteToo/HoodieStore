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
import android.widget.Button;
import android.widget.TextView;

import com.example.shopping.Adapter.CartAdapter;
import com.example.shopping.Adapter.OrderDetailAdapter;
import com.example.shopping.Model.Cart;
import com.example.shopping.Model.Order;
import com.example.shopping.Model.OrderDetail;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailFragment extends Fragment {
    private OrderDetailAdapter productOrderAdapter;
    private RecyclerView productOrderRecycler;
    private List<OrderDetail> orderDetails;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView productOrderItemQuantity = view.findViewById(R.id.product_order_quantity_txt);
        TextView productOrderItemTotal = view.findViewById(R.id.product_order_total_txt);

        productOrderRecycler = view.findViewById(R.id.product_order_recycler);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
        productOrderRecycler.setLayoutManager(grid);

        orderDetails = new ArrayList<>();
        productOrderAdapter = new OrderDetailAdapter(getContext(), orderDetails);
        productOrderRecycler.setAdapter(productOrderAdapter);

        productOrderAdapter.setOnDataChangeListener(new CartAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged(int size) {
                String padFormat = String.format("%02d", productOrderAdapter.getQuantity());
                productOrderItemQuantity.setText(padFormat);

                DecimalFormat moneyFormat = new DecimalFormat("$0.00");
                String formattedCurrency = moneyFormat.format(productOrderAdapter.getTotalMoney());
                productOrderItemTotal.setText(formattedCurrency);
            }
        });


        Bundle args = getArguments();
        Order order = (Order) args.getSerializable("order");

//        productOrderItemQuantity.setText(String.valueOf(productOrderAdapter.getItemCount()));
//        productOrderItemTotal.setText(String.valueOf(productOrderAdapter.getTotalMoney()));

        APIHelper.fetchOrdersDetail(order.getOrderId(), productOrderAdapter);
    }
}