package com.example.shopping.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.Fragment.DetailFragment;
import com.example.shopping.Interface.API;
import com.example.shopping.Model.Cart;
import com.example.shopping.Model.CartItem;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.example.shopping.Utils.Counter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterVH> {
    private Context context;
    private List<Product> products;
    private List<Integer> checkProducts;
    private Customer customer;
    private BottomNavigationView navView;
    private int flag; // 0 - ProductFragement || 1 - FavoriteFragment

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
        this.checkProducts = new ArrayList<>();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BottomNavigationView getNavView() {
        return navView;
    }

    public void setNavView(BottomNavigationView navView) {
        this.navView = navView;
    }

    @NonNull
    @Override
    public ProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_grid_cell, parent, false);

        return new ProductAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterVH holder, int position) {
        Product product = products.get(position);

        holder.product_title.setText(product.getName());
        Glide.with(holder.itemView).load("file:///android_asset/Image/" + product.getImg()).into(holder.product_img);

        CartItem item = new CartItem();
        item.setId(product.getId());

        if (Cart.getInstance().isInCart(item) >= 0) {
            holder.shopNow.setText("IN CART");
        }

        Retrofit retrofit = APIHelper.buildRetrofit();
        API api = retrofit.create(API.class);
        Call<List<Product>> call = api.getFavorite(customer.getCustomerId());

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> favProducts = response.body();

                    for (Product elem : favProducts) {
                        Product temp = products.get(position);

                        if (elem.getId() == temp.getId()) {
                            ((ToggleButton) holder.itemView.findViewById(R.id.product_heart)).setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t);
            }
        });


//        holder.product_img.setImageURI(Uri.parse("file:///android_asset/Image/" + product.getImg()));

//        holder.product_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentActivity fragmentActivity = (FragmentActivity) getContext();
//                FragmentTransaction transaction =
//                        fragmentActivity.getSupportFragmentManager().beginTransaction();
//
//                Bundle args = new Bundle();
//                args.putSerializable("product", product);
//                DetailFragment detailFragment = new DetailFragment();
//                detailFragment.setArguments(args);
//
//                transaction.replace(R.id.nav_host_fragment, detailFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
//
//        holder.product_like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                Customer customer = (Customer) intent.getSerializableExtra("info");
//                APIHelper.postFavorite(customer.getCustomerId(), product.getId());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductAdapterVH extends RecyclerView.ViewHolder {
        View itemView;
        TextView product_title;
        TextView shopNow;
        ImageView product_img;
        ToggleButton product_heart;

        public ProductAdapterVH(@NonNull View itemView) {
            super(itemView);
            this.product_title = itemView.findViewById(R.id.product_title);
            this.product_img = itemView.findViewById(R.id.product_img);
            this.product_heart = itemView.findViewById(R.id.product_heart);
            this.shopNow = itemView.findViewById(R.id.shop_now);
            this.itemView = itemView;

            Animation topAnim = AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
            Animation bottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation);

            product_title.setAnimation(bottomAnim);
            product_img.setAnimation(topAnim);

            shopNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = products.get(getLayoutPosition());
                    CartItem item = new CartItem(
                            product.getId(),
                            product.getTypeId(),
                            product.getImg(),
                            product.getName(),
                            1,
                            Float.parseFloat(String.valueOf(product.getPrice()))
                    );

                    Cart.getInstance().addItem(item);
                    SharedPreferences mPrefs = context.getSharedPreferences("CartPrefs", MODE_PRIVATE);
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

                    shopNow.setText("IN CART");
                }
            });

            product_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                    FragmentTransaction transaction =
                            fragmentActivity.getSupportFragmentManager().beginTransaction();

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(product_title, "detail_name_tran");
                    pairs[1] = new Pair<View, String>(product_img, "detail_img_tran");

                    Bundle args = new Bundle();
                    Product product = products.get(getLayoutPosition());

                    args.putSerializable("product", product);
                    DetailFragment detailFragment = new DetailFragment(navView);
                    detailFragment.setArguments(args);

                    transaction.replace(R.id.nav_host_fragment, detailFragment);
                    transaction.addSharedElement(product_title, "detail_name_tran");
                    transaction.addSharedElement(product_img, "detail_img_tran");

                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            product_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product_heart.isChecked()) {
                        Product product = products.get(getLayoutPosition());

                        Retrofit retrofit = APIHelper.buildRetrofit();
                        API api = retrofit.create(API.class);
                        Call<Void> call = api.postFavorite(customer.getCustomerId(), product.getId());

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response.code());
                                navView.getOrCreateBadge(R.id.heart).setNumber(++Counter.favoriteCounter);
                                navView.getOrCreateBadge(R.id.heart).setVisible(true);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(t);
                            }
                        });
                    } else {
                        Product product = products.get(getLayoutPosition());
                        Retrofit retrofit = APIHelper.buildRetrofit();
                        API api = retrofit.create(API.class);
                        Call<Void> call = api.deleteFavorite(customer.getCustomerId(), product.getId());

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println(response.code());

                                if (Counter.favoriteCounter > 1) {
                                    navView.getOrCreateBadge(R.id.heart).setNumber(--Counter.favoriteCounter);
                                    navView.getOrCreateBadge(R.id.heart).setVisible(true);
                                } else {
                                    navView.getOrCreateBadge(R.id.heart).setVisible(false);
                                }

                                if (flag == 1) {
                                    products.remove(product);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println(t);
                            }
                        });
                    }
                }
            });
        }
    }
}
