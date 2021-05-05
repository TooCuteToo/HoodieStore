package com.example.shopping.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shopping.Interface.API;
import com.example.shopping.Model.Cart;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Order;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.shopping.Utils.APIHelper.baseURL;

public class InfoFragment extends Fragment {
    private Customer customer;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            customer = (Customer) getArguments().getSerializable("info");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println(Cart.getInstance().getItems().size());
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hooks
        TextView infoName = view.findViewById(R.id.info_name);
        TextView infoUsername = view.findViewById(R.id.info_username);
        TextView paymentLabel = view.findViewById(R.id.payment_label);
        TextView orderLabel = view.findViewById(R.id.order_label);

        TextInputLayout infoNameTxt = view.findViewById(R.id.info_name_txt);
        TextInputLayout infoUserNameTxt = view.findViewById(R.id.info_username_txt);
        TextInputLayout infoEmailTxt = view.findViewById(R.id.info_email_txt);
        TextInputLayout infoPasswordTxt = view.findViewById(R.id.info_password_txt);

        Button saveBtn = view.findViewById(R.id.save_btn);

        infoName.setText(customer.getName());
        infoUsername.setText(customer.getUsername());

        infoNameTxt.getEditText().setText(customer.getName());
        infoUserNameTxt.getEditText().setText(customer.getUsername());
        infoEmailTxt.getEditText().setText(customer.getEmail());
        infoPasswordTxt.getEditText().setText(customer.getPass());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Call<List<Order>> call = api.getOrders(customer.getCustomerId());

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    List<Order> orders = response.body();
                    float sum = 0;
                    int count = 0;

                    for (Order order : orders) {
                        sum += order.getTotalMoney();
                        count++;
                    }

                    DecimalFormat moneyFormat = new DecimalFormat("$0.00");
                    String formattedCurrency = moneyFormat.format(sum);
                    paymentLabel.setText(formattedCurrency);
                    orderLabel.setText(String.valueOf(count));
                } else return;
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                System.out.println(t);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullName(infoNameTxt) ||
                        !validateEmail(infoEmailTxt) ||
                        !validateUserName(infoUserNameTxt) ||
                        !validatePassword(infoPasswordTxt)
                ) {
                    return;
                }

                Customer temp = new Customer(
                        customer.getCustomerId(),
                        infoNameTxt.getEditText().getText().toString(),
                        infoEmailTxt.getEditText().getText().toString(),
                        infoUserNameTxt.getEditText().getText().toString(),
                        infoPasswordTxt.getEditText().getText().toString()
                );

                customer.setName(temp.getName());
                customer.setEmail(temp.getEmail());
                customer.setUsername(temp.getUsername());
                customer.setPass(temp.getPass());

                APIHelper.editCustomer(customer.getCustomerId(), temp);

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE).edit();
                editor.clear().apply();

                infoName.setText(infoNameTxt.getEditText().getText());
                infoUsername.setText(infoUserNameTxt.getEditText().getText());

                infoNameTxt.getEditText().clearFocus();
                infoUserNameTxt.getEditText().clearFocus();
                infoEmailTxt.getEditText().clearFocus();
                infoPasswordTxt.getEditText().clearFocus();
            }
        });
    }

    private boolean validateFullName(TextInputLayout fullNameTxtInput) {
        if (fullNameTxtInput.getEditText().getText().toString().isEmpty()) {
            fullNameTxtInput.setError("Name can't be empty");
            return false;
        }

        fullNameTxtInput.setError(null);
        return true;
    }

    private boolean validateEmail(TextInputLayout emailTxtInput) {
        if (!isValid(emailTxtInput.getEditText().getText().toString())) {
            emailTxtInput.setError("Invalid email address!");
            return false;
        }

        if (emailTxtInput.getEditText().getText().toString().isEmpty()) {
            emailTxtInput.setError("Email can't be empty");
            return false;
        }

        emailTxtInput.setError(null);
        return true;
    }

    private boolean validateUserName(TextInputLayout userNameTxtInput) {
        if (userNameTxtInput.getEditText().getText().toString().isEmpty()) {
            userNameTxtInput.setError("User can't be empty");
            return false;
        }

        userNameTxtInput.setError(null);
        return true;
    }

    private boolean validatePassword(TextInputLayout passwordTxtInput) {
        if (passwordTxtInput.getEditText().getText().toString().isEmpty()) {
            passwordTxtInput.setError("Password can't be empty");
            return false;
        }

        if (passwordTxtInput.getEditText().getText().toString().length() < 6) {
            passwordTxtInput.setError("Minimun 6 numbers");
            return false;
        }

        passwordTxtInput.setError(null);
        return true;
    }

    private boolean isValid(String email) {
        String emailRegex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();
    }
}