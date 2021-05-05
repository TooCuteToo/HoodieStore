package com.example.shopping.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.shopping.Model.Customer;
import com.example.shopping.R;
import com.example.shopping.Utils.APIHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Animation
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // Register Hooks
        TextInputLayout fullNameTxtInput = findViewById(R.id.reg_fullname);
        TextInputLayout emailTxtInput = findViewById(R.id.reg_email);
        TextInputLayout userNameTxtInput = findViewById(R.id.reg_username);
        TextInputLayout passwordTxtInput = findViewById(R.id.reg_password);
        TextInputLayout confirmTxtInput = findViewById(R.id.reg_confirm_pass);

        Button regGo = findViewById(R.id.reg_go_button);
        Button regLogin = findViewById(R.id.reg_login);

        fullNameTxtInput.setAnimation(topAnim);
        emailTxtInput.setAnimation(topAnim);

        passwordTxtInput.setAnimation(bottomAnim);
        confirmTxtInput.setAnimation(bottomAnim);

        regGo.setAnimation(bottomAnim);
        regLogin.setAnimation(bottomAnim);

        regGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateFullName(fullNameTxtInput) ||
                    !validateEmail(emailTxtInput) ||
                    !validateUserName(userNameTxtInput) ||
                    !validatePassword(passwordTxtInput) ||
                    !validateConfirmPassword(confirmTxtInput, passwordTxtInput)
                ) {
                    return;
                }

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                Customer customer = new Customer(
                        0,
                        fullNameTxtInput.getEditText().getText().toString(),
                        emailTxtInput.getEditText().getText().toString(),
                        userNameTxtInput.getEditText().getText().toString(),
                        passwordTxtInput.getEditText().getText().toString()
                );

                APIHelper.createCustomer(customer);

                SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE).edit();
                editor.clear().apply();

//                    Utils.context = RegisterActivity.this;
//                    Utils.writeCustomerToInternal(customer);


                intent.putExtra("info", customer);

                setResult(101, intent);

                startActivity(intent);
                finish();
            }
        });

        regLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

    private boolean validateConfirmPassword(TextInputLayout confirmTxtInput, TextInputLayout passwordTxtInput) {
        if (confirmTxtInput.getEditText().getText().toString().isEmpty()) {
            confirmTxtInput.setError("Confirm password can't be empty");
            return false;
        }

        if (!confirmTxtInput.getEditText().getText().toString().equals(passwordTxtInput.getEditText().getText().toString())) {
            confirmTxtInput.setError("Comfirm password doesn't equal password");
            return false;
        }

        confirmTxtInput.setError(null);
        return true;
    }

    private boolean isValid(String email) {
        String emailRegex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();
    }
}