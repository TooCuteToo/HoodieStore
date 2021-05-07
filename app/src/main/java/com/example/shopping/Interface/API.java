package com.example.shopping.Interface;
import com.example.shopping.Model.Category;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
import com.example.shopping.Model.Product;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<List<Product>> getProduct(@Path("id") String name);

    @GET("categories")
    Call<List<Category>> getCategories();

    @GET("categories/{id}")
    Call<List<Product>> getProductsByCategory(@Path("id") String typeId);

    @POST("orders/{customerId}")
    Call<List<Order>> getOrders(@Path("customerId") int customerId);

    @POST("favorites")
    @FormUrlEncoded
    Call<Void> postFavorite(@Field("customerId") int customerId, @Field("Id") int id);

    @POST("customer")
    @FormUrlEncoded
    Call<List<Customer>> getCustomer(@Field("userName") String userName, @Field("pass") String password);

    @POST("customers")
    Call<Void> createCustomer(@Body Customer customer);

    @POST("orders")
    Call<Void> createOrder(@Body Order order);

    @POST("favorite")
    @FormUrlEncoded
    Call<List<Product>> getFavorite(@Field("customerId") int customerId);

    @PUT("customer/{id}")
    Call<Void> editCustomer(@Path("id") int id, @Body Customer customer);
}
