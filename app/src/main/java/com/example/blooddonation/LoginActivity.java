package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ExperimentalGetImage;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.blooddonation.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @ExperimentalGetImage
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setupClickListeners();
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Log.d("LoginActivity", errorMessage);
                Toast.makeText(
                        LoginActivity.this,
                        "Неверный логин или пароль! Повторите попытку",
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getFirebaseUser().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                viewModel.pullUserDocument(firebaseUser.getUid());
            }
        });
        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                //TODO start qr reader activity (admin)
                //TODO start applications list activity (client)
                Log.d("LoginActivity", "User has been found");
                //finish();
            }
        });
    }

    private void setupClickListeners() {
        binding.buttonLogin.setOnClickListener(view -> {
            String email = binding.editTextEmail.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                        LoginActivity.this,
                        "Неверный логин или пароль! Повторите попытку",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.login(email, password);
        });
        binding.textViewSwitchToSingUp.setOnClickListener(view -> {
            startActivity(RegistrationActivity.newIntent(this));
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}