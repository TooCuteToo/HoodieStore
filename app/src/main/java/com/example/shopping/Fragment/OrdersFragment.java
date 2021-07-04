package com.example.shopping.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.shopping.Adapter.OrderAdapter;
import com.example.shopping.Interface.API;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment {
    private OrderAdapter orderAdapter;
    private List<Order> orders;
    private RecyclerView orderItemRecycler;

    public OrdersFragment() {
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
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderItemRecycler = view.findViewById(R.id.order_recycler);

        GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
        orderItemRecycler.setLayoutManager(grid);

        orders = new ArrayList<>();
        orderAdapter = new OrderAdapter(getContext(), orders);
        orderItemRecycler.setAdapter(orderAdapter);

        Bundle args = getArguments();
        Customer customer = (Customer) args.getSerializable("info");

        Retrofit retrofit = APIHelper.buildRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Order>> call = api.getOrders(customer.getCustomerId());

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    List<Order> orders = response.body();
                    orderAdapter.setOrders(orders);
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}