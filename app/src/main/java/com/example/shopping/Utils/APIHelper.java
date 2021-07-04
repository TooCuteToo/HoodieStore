package com.example.shopping.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.example.shopping.Adapter.CategoryAdapter;
import com.example.shopping.Adapter.OrderAdapter;
import com.example.shopping.Adapter.OrderDetailAdapter;
import com.example.shopping.Interface.API;
import com.example.shopping.Model.Category;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
import com.example.shopping.Model.OrderDetail;
import com.example.shopping.Model.Product;
import com.example.shopping.Adapter.ProductAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {
    public static String baseURL = "http://10.0.2.2:8000/api/";
    public static int code = 0;

    public static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void fetchProducts(ProductAdapter productAdapter) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<List<Product>> call = api.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productAdapter.setProducts(products);
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Dialog.showAlert(productAdapter.getContext(), t.toString());
            }
        });
    }

    public static void fetchCategories(CategoryAdapter categoryAdapter) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<List<Category>> call = api.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    List<Category> categories = response.body();
                    categoryAdapter.setCategories(categories);
                    categoryAdapter.notifyDataSetChanged();
                } else return;
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void fetchOrders(int customerId, OrderAdapter orderAdapter) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<List<Order>> call = api.getOrders(customerId);

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    List<Order> orders = response.body();
                    orderAdapter.setOrders(orders);
                    orderAdapter.notifyDataSetChanged();
                } else return;
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void fetchOrdersDetail(int orderId, OrderDetailAdapter orderDetailAdapter) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<List<OrderDetail>> call = api.getOrdersDetail(orderId);

        call.enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                if (response.isSuccessful()) {
                    List<OrderDetail> orders = response.body();
                    orderDetailAdapter.setOrderDetails(orders);
                    orderDetailAdapter.notifyDataSetChanged();
                } else return;
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void fetchProductsByCategory(String typeId, ProductAdapter productAdapter) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<List<Product>> call = api.getProductsByCategory(typeId);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productAdapter.setProducts(products);
                    productAdapter.notifyDataSetChanged();
                } else return;
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void fetchFavorite(int customerId, ProductAdapter productAdapter) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<List<Product>> call = api.getFavorite(customerId);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> products = response.body();
                    productAdapter.setProducts(products);
                    productAdapter.notifyDataSetChanged();
                } else return;
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void postFavorite(int customerId, int Id) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<Void> call = api.postFavorite(customerId, Id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void postOrder(Order order) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<Void> call = api.createOrder(order);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void getCustomer(List<Customer> customers, String username, String password) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<List<Customer>> call = api.getCustomer(username, password);

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    List<Customer> tmp = response.body();
                    customers.addAll(tmp);
                } else return;

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void createCustomer(Customer customer, Activity activity, Intent intent) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);
        Call<Void> call = api.createCustomer(customer);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                code = response.code();

                if (code == 400) {
                    Dialog.showAlert(activity, "Email or username is already existed!!!");
                    return;
                }

                intent.putExtra("info", customer);
                activity.setResult(101, intent);

                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public static void editCustomer(int id, Customer customer, Activity activity) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<Void> call = api.editCustomer(id, customer);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
}
