package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blooddonation.databinding.ActivityRegistrationBinding;
import com.example.blooddonation.databinding.ActivityRegistrationProfileBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        setupOnClickListeners();
        observeViewModel();
    }

    private void setupOnClickListeners() {
        binding.buttonSingUp.setOnClickListener(view -> {
            String firstName = getTrimmedValue(binding.editTextFirstName);
            String middleName = getTrimmedValue(binding.editTextMiddleName);
            String lastName = getTrimmedValue(binding.editTextLastName);
            String email = getTrimmedValue(binding.editTextEmail);
            String password = getTrimmedValue(binding.editTextPassword);
            String repeatedPassword = getTrimmedValue(binding.editTextRepeatedPassword);

            if (firstName.isEmpty()
                    || middleName.isEmpty()
                    || lastName.isEmpty()
                    || !Validator.isEmailValid(email)
                    || !Validator.isPasswordValid(password)
                    || !password.equals(repeatedPassword)) {
                Toast.makeText(this,
                                getString(R.string.check_validity_of_your_data),
                                Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            User user = new User(firstName, middleName, lastName, email, password);
            viewModel.signUp(user);
            viewModel.getFirebaseUser().observe(this, firebaseUser -> {
                user.setId(firebaseUser.getUid());
                startActivity(RegistrationProfileActivity.newIntent(this, user));
                finish();
            });
        });
        binding.textViewSwitchToLogin.setOnClickListener(view -> {
            startActivity(LoginActivity.newIntent(this));
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}