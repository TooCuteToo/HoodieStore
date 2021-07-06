package com.example.shopping.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.Fragment.DetailFragment;
import com.example.shopping.Fragment.OrderDetailFragment;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
import com.example.shopping.Model.OrderDetail;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterVH> {
    private Context context;
    private List<Order> orders;
    private Customer customer;
    private int position;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }


    public int getItemPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @NonNull
    @NotNull
    @Override
    public OrderAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        return new OrderAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapterVH holder, int position) {
        Order order = orders.get(position);
        DecimalFormat moneyFormat = new DecimalFormat("$0.00");

        String formattedCurrency = moneyFormat.format(order.getTotalMoney());
        holder.orderItemTotal.setText(formattedCurrency);

        String padFormat = String.format("%02d", order.getOrderId());
        holder.orderItemNumber.setText(padFormat);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderAdapterVH extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView orderItemNumber;
        public TextView orderItemTotal;
        public MaterialCardView orderItemCard;
        public View itemView;

        public OrderAdapterVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.orderItemNumber = itemView.findViewById(R.id.order_item_number);
            this.orderItemTotal = itemView.findViewById(R.id.order_item_total);
            this.orderItemCard = itemView.findViewById(R.id.order_item_card);
            orderItemCard.setOnCreateContextMenuListener(this);

            this.orderItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                    FragmentTransaction transaction =
                            fragmentActivity.getSupportFragmentManager().beginTransaction();

                    OrderDetailFragment orderDetailFragment = new OrderDetailFragment();

                    Bundle args = new Bundle();
                    Order order = orders.get(getLayoutPosition());

                    args.putSerializable("order", order);
                    orderDetailFragment.setArguments(args);

                    transaction.replace(R.id.nav_host_fragment, orderDetailFragment);

                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), 773, 0, "Delete");
        }
    }
}
