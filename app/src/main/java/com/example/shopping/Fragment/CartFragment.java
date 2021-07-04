package com.example.shopping.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.example.shopping.Adapter.ProductAdapter;
import com.example.shopping.Model.Cart;
import com.example.shopping.Model.CartItem;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
import com.example.shopping.Model.OrderDetail;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private CartAdapter cartAdapter;
    private RecyclerView cartRecycler;
    private BottomNavigationView navView;

    public CartFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartRecycler = view.findViewById(R.id.cart_recycler);
        TextView cartQuantity = view.findViewById(R.id.cart_quantity_txt);
        TextView cartTotal = view.findViewById(R.id.cart_total_txt);
        Button checkoutBtn = view.findViewById(R.id.checkout_btn);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 1);
        cartRecycler.setLayoutManager(grid);
        cartAdapter = new CartAdapter(getContext());

        String padFormat = String.format("%02d", Cart.getInstance().calculateQuantity());
        cartQuantity.setText(padFormat);

        DecimalFormat moneyFormat = new DecimalFormat("$0.00");
        String formattedCurrency = moneyFormat.format(Cart.getInstance().calculateTotal());
        cartTotal.setText(formattedCurrency);

        cartAdapter.setOnDataChangeListener(new CartAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged(int size) {
                String padFormat = String.format("%02d", Cart.getInstance().calculateQuantity());
                cartQuantity.setText(padFormat);

                DecimalFormat moneyFormat = new DecimalFormat("$0.00");
                String formattedCurrency = moneyFormat.format(Cart.getInstance().calculateTotal());
                cartTotal.setText(formattedCurrency);

                if (Cart.getInstance().calculateQuantity() == 0) {
                    navView.getOrCreateBadge(R.id.cart).clearNumber();
                    navView.getOrCreateBadge(R.id.cart).setVisible(false);
                }

                navView.getOrCreateBadge(R.id.cart).setNumber(Cart.getInstance().calculateQuantity());
            }
        });

        Bundle args = getArguments();
        Customer customer = (Customer) args.getSerializable("info");

        cartRecycler.setAdapter(cartAdapter);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure ?")
                        .setTitle("Information")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = getContext().getSharedPreferences("CartPrefs", Context.MODE_PRIVATE).edit();
                                editor.clear().apply();

                                ArrayList<OrderDetail> orderDetails = new ArrayList<>();
                                for (CartItem item : Cart.getInstance().getItems()) {
                                    OrderDetail detail = new OrderDetail(item.getId(), item.getTypeId(), item.getQuantity(), item.getMoney());
                                    orderDetails.add(detail);
                                }

                                Order order = new Order(customer.getCustomerId(), Cart.getInstance().calculateTotal(), orderDetails);
                                APIHelper.postOrder(order);

                                Cart.getInstance().getItems().clear();
                                cartAdapter.notifyDataSetChanged();

                                String padFormat = String.format("%02d", Cart.getInstance().calculateQuantity());
                                cartQuantity.setText(padFormat);

                                DecimalFormat moneyFormat = new DecimalFormat("$0.00");
                                String formattedCurrency = moneyFormat.format(Cart.getInstance().calculateTotal());
                                cartTotal.setText(formattedCurrency);

                                navView.getOrCreateBadge(R.id.cart).clearNumber();
                                navView.getOrCreateBadge(R.id.cart).setVisible(false);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}