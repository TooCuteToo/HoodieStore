package com.example.shopping.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
import com.example.shopping.Model.OrderDetail;
import com.example.shopping.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailAdapterVH> {
    private Context context;
    private List<OrderDetail> orderDetails;
    private Customer customer;
    private float totalMoney;
    private int quantity;

    public OrderDetailAdapter(Context context, List<OrderDetail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getTotalMoney() {
        float sum = 0;
        for (OrderDetail orderDetail : orderDetails) {
            sum += orderDetail.getMoney() * orderDetail.getQuantity();
        }

        return sum;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getQuantity() {
        int count = 0;
        for (OrderDetail orderDetail : orderDetails) {
            count += orderDetail.getQuantity();
        }

        return count;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NonNull
    @NotNull
    @Override
    public OrderDetailAdapter.OrderDetailAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_order_item_layout, parent, false);
        return new OrderDetailAdapter.OrderDetailAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderDetailAdapterVH holder, int position) {
        OrderDetail orderDetail = orderDetails.get(position);

        holder.productOrderItemName.setText(orderDetail.getName());

        DecimalFormat moneyFormat = new DecimalFormat("$0.00");
        String formattedCurrency = moneyFormat.format(orderDetail.calculateTotalMoney());
        holder.productOrderItemPrice.setText(formattedCurrency);

        String padFormat = String.format("%02d", orderDetail.getQuantity());
        holder.productOrderItemQuantity.setText(padFormat);

        Glide.with(holder.itemView).load("file:///android_asset/Image/" + orderDetail.getImage()).into(holder.productOrderItemImg);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public interface OnDataChangeListener{
        void onDataChanged(int size);
    }

    CartAdapter.OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(CartAdapter.OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }


    public class OrderDetailAdapterVH extends RecyclerView.ViewHolder {
        public TextView productOrderItemName;
        public TextView productOrderItemPrice;
        public TextView productOrderItemQuantity;

        public ImageView productOrderItemImg;
        public View itemView;

        public OrderDetailAdapterVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            this.productOrderItemName = itemView.findViewById(R.id.product_order_item_name);
            this.productOrderItemPrice = itemView.findViewById(R.id.product_order_item_price);
            this.productOrderItemQuantity = itemView.findViewById(R.id.product_order_item_quantity);
            this.productOrderItemImg = itemView.findViewById(R.id.product_order_item_image);

            mOnDataChangeListener.onDataChanged(orderDetails.size());
        }
    }
}
