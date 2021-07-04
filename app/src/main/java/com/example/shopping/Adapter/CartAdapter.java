
package com.example.shopping.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.Fragment.DetailFragment;
import com.example.shopping.Model.Cart;
import com.example.shopping.Model.CartItem;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLSyntaxErrorException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartAdapterVH> {
    private Context context;
    private ArrayList<CartItem> items;

    public CartAdapter(Context context) {
        this.context = context;
        this.items = Cart.getInstance().getItems();
    }

    @NonNull
    @NotNull
    @Override
    public CartAdapterVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);
        return new CartAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartAdapterVH holder, int position) {
        CartItem item = items.get(position);

        holder.productItemName.setText(item.getName());

        DecimalFormat moneyFormat = new DecimalFormat("$0.00");
        String formattedCurrency = moneyFormat.format(item.calculateTotalMoney());
        holder.productItemPrice.setText(formattedCurrency);

        String padFormat = String.format("%02d", item.getQuantity());
        holder.productItemQuantity.setText(String.valueOf(padFormat));

        Glide.with(holder.itemView).load("file:///android_asset/Image/" + item.getImage()).into(holder.productItemImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnDataChangeListener{
        void onDataChanged(int size);
    }

    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public class CartAdapterVH extends RecyclerView.ViewHolder {
        View itemView;

        TextView productItemName;
        TextView productItemPrice;
        TextView productItemQuantity;
        TextView upIcon;
        TextView downIcon;

        ImageView productItemImage;
        ImageView deleteIcon;

        public CartAdapterVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            this.productItemName = itemView.findViewById(R.id.product_item_name);
            this.productItemPrice = itemView.findViewById(R.id.product_item_price);

            this.productItemQuantity = itemView.findViewById(R.id.product_item_quantity);
            this.productItemImage = itemView.findViewById(R.id.product_item_image);

            this.deleteIcon = itemView.findViewById(R.id.delete_icon);
            this.upIcon = itemView.findViewById(R.id.up_icon);
            this.downIcon = itemView.findViewById(R.id.down_icon);

            this.deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart.getInstance().deleteItem(items.get(getLayoutPosition()));
                    notifyDataSetChanged();

                    if (mOnDataChangeListener != null) {
                        mOnDataChangeListener.onDataChanged(Cart.getInstance().getItems().size());
                    }
                }
            });

            this.upIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(productItemQuantity.getText().toString());
                    String padFormat = String.format("%02d", ++count);
                    productItemQuantity.setText(padFormat);

                    Cart.getInstance().getItems().get(getLayoutPosition()).setQuantity(Integer.parseInt(padFormat));
                    DecimalFormat moneyFormat = new DecimalFormat("$0.00");
                    CartItem cartItem = Cart.getInstance().getItems().get(getLayoutPosition());
                    String formattedCurrency = moneyFormat.format(cartItem.calculateTotalMoney());
                    productItemPrice.setText(formattedCurrency);

                    mOnDataChangeListener.onDataChanged(Cart.getInstance().getItems().size());
                }
            });

            this.downIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = Integer.parseInt(productItemQuantity.getText().toString());

                    if (count - 1 == 0) {
                        Cart.getInstance().deleteItem(items.get(getLayoutPosition()));
                        notifyDataSetChanged();

                        if (mOnDataChangeListener != null) {
                            mOnDataChangeListener.onDataChanged(Cart.getInstance().getItems().size());
                        }
                    } else {
                        String padFormat = String.format("%02d", --count);
                        productItemQuantity.setText(padFormat);
                        Cart.getInstance().getItems().get(getLayoutPosition()).setQuantity(Integer.parseInt(padFormat));

                        DecimalFormat moneyFormat = new DecimalFormat("$0.00");
                        CartItem cartItem = Cart.getInstance().getItems().get(getLayoutPosition());
                        String formattedCurrency = moneyFormat.format(cartItem.calculateTotalMoney());
                        productItemPrice.setText(formattedCurrency);

                        mOnDataChangeListener.onDataChanged(Cart.getInstance().getItems().size());
                    }
                }
            });
        }
    }
}
