package com.example.shopping.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopping.Interface.API;
import com.example.shopping.Model.Customer;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout userName;
    private TextInputLayout password;
    private Button goButton;
    private Button signUpButton;
    private ImageView logoImg;
    private TextView logoName;
    private TextView sloganName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Animation
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // Login Hooks
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);

        goButton = findViewById(R.id.go_button);
        signUpButton = findViewById(R.id.signup_button);

        logoImg = findViewById(R.id.logo_image);
        logoName = findViewById(R.id.logo_name);
        sloganName = findViewById(R.id.slogan_name);

        userName.setAnimation(topAnim);
        password.setAnimation(topAnim);

        goButton.setAnimation(bottomAnim);
        signUpButton.setAnimation(bottomAnim);

        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        int customerId = prefs.getInt("customerId", -1);
        String username = prefs.getString("username", null);
        String pass = prefs.getString("password", null);

        String name = prefs.getString("name", null);
        String email = prefs.getString("email", null);

        if (username != null && pass != null) {
            Customer customer = new Customer(customerId, name, email, username, pass);

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("info", customer);

            startActivity(intent);
            finish();
        }

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserName(userName) || !validatePassword(password)) return;
                getCustomer(userName.getEditText(), password.getEditText());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                Pair[] pairs = new Pair[5];
                pairs[0] = new Pair<View, String>(logoImg, "logo_image");
                pairs[1] = new Pair<View, String>(logoName, "logo_text");
                pairs[2] = new Pair<View, String>(sloganName, "logo_text");
                pairs[3] = new Pair<View, String>(signUpButton, "button_tran");
                pairs[4] = new Pair<View, String>(goButton, "button_tran");
//
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }

    private boolean validatePassword(TextInputLayout password) {
        if (password.getEditText().getText().toString().isEmpty()) {
            password.setError("Password can't be empty");
            return false;
        }

        if (password.getEditText().getText().toString().length() < 6) {
            password.setError("Minimum 6 numbers");
            return false;
        }

        password.setError(null);
        return true;
    }

    private boolean validateUserName(TextInputLayout userName) {
        if (userName.getEditText().getText().toString().isEmpty()) {
            userName.setError("User can't be empty");
            return false;
        }

        userName.setError(null);
        return true;
    }

    private void getCustomer(EditText userName, EditText password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIHelper.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Call<List<Customer>> call = api.getCustomer(userName.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    List<Customer> customers = response.body();
                    if (customers.size() <= 0) {
                        return;
                    }

                    SharedPreferences prefs = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putInt("customerId", customers.get(0).getCustomerId());
                    editor.putString("username", customers.get(0).getUsername());

                    editor.putString("password", customers.get(0).getPass());
                    editor.putString("name", customers.get(0).getName());

                    editor.putString("email", customers.get(0).getEmail());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("info", customers.get(0));

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(logoImg, "logo_image");
                    pairs[1] = new Pair<View, String>(logoName, "logo_text");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }


}