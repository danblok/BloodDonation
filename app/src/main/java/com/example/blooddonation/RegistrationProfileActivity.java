package com.example.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.blooddonation.databinding.ActivityRegistrationProfileBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistrationProfileActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationProfileActivity";
    private static final String USER_EXTRA = "user";
    private static final String CLIENT_ROLE = "CLIENT";

    private ActivityRegistrationProfileBinding binding;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> bloodTypesAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_list_item,
                getResources().getStringArray(R.array.blood_types));
        binding.spinnerBloodTypes.setAdapter(bloodTypesAdapter);
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModel();

        User user = (User) getIntent().getSerializableExtra(USER_EXTRA);
        binding.buttonSingUp.setOnClickListener(view -> {
            Date dateOfBirth;
            try {
                dateOfBirth = parseDate(getTrimmedValue(binding.editTextDateOfBirth));
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(
                        this,
                        getString(R.string.write_date_in_a_correct_way),
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            String sex = getTrimmedValue(binding.radioButtonMale);
            if (binding.radioButtonFemale.isSelected()) {
                sex = getTrimmedValue(binding.radioButtonFemale);
            } else if (binding.radioButtonOther.isSelected()) {
                sex = getTrimmedValue(binding.radioButtonOther);
            }

            String city = getTrimmedValue(binding.editTextCity);
            String seriesAndNumber = getTrimmedValue(binding.editTextSeriesAndNumber);
            String bloodType = binding.spinnerBloodTypes.getSelectedItem().toString();

            if (city.isEmpty() || !Validator.isSeriesAndNumberValid(seriesAndNumber)
                || bloodType.isEmpty()) {
                Toast.makeText(
                                this,
                                getString(R.string.check_validity_of_your_data),
                                Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            List<String> bloodComponents = new ArrayList<>();
            if (binding.checkBoxWholeBlood.isChecked()) {
                bloodComponents.add(binding.checkBoxWholeBlood.getText().toString());
            }
            if (binding.checkBoxPlasma.isChecked()) {
                bloodComponents.add(binding.checkBoxPlasma.getText().toString());
            }
            if (binding.checkBoxPlatelets.isChecked()) {
                bloodComponents.add(binding.checkBoxPlatelets.getText().toString());
            }
            if (binding.checkBoxRedBloodCells.isChecked()) {
                bloodComponents.add(binding.checkBoxRedBloodCells.getText().toString());
            }
            if (binding.checkBoxGranulocytes.isChecked()) {
                bloodComponents.add(binding.checkBoxGranulocytes.getText().toString());
            }

            User newUser = new User(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getMiddleName(),
                    user.getEmail(),
                    user.getPassword(),
                    dateOfBirth,
                    sex,
                    city,
                    bloodType,
                    seriesAndNumber,
                    bloodComponents,
                    CLIENT_ROLE
                    );

            viewModel.createUser(newUser);
        });
    }

    private String getTrimmedValue(TextView textView) {
        return textView.getText().toString().trim();
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUser().observe(this, user -> {
            startActivity(LoginActivity.newIntent(this));
            finish();
        });

    }

    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return formatter.parse(date);
    }

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, RegistrationProfileActivity.class);
        intent.putExtra(USER_EXTRA, user);
        return intent;
    }
}