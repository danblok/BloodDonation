package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blooddonation.databinding.ActivityRegistrationBinding;
import com.example.blooddonation.databinding.ActivityRegistrationProfileBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupOnClickListeners();
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
            startActivity(RegistrationProfileActivity.newIntent(this, user));
        });
        binding.textViewSwitchToLogin.setOnClickListener(view -> {
            startActivity(LoginActivity.newIntent(this));
        });
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}