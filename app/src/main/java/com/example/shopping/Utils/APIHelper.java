package com.example.shopping.Utils;

import com.example.shopping.Interface.API;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
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

    private static Retrofit buildRetrofit() {
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

    public static void createCustomer(Customer customer) {
        Retrofit retrofit = buildRetrofit();
        API api = retrofit.create(API.class);

        Call<Void> call = api.createCustomer(customer);

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

    public static void editCustomer(int id, Customer customer) {
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
