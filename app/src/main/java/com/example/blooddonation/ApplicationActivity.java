package com.example.blooddonation;

import static com.example.blooddonation.Utils.getTrimmedValue;
import static com.example.blooddonation.Utils.parseDate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.blooddonation.databinding.ActivityApplicationBinding;

import java.text.ParseException;
import java.util.Date;

public class ApplicationActivity extends AppCompatActivity {

    private ActivityApplicationBinding binding;
    private ApplicationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupSpinners();


        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        observeViewModel();
        viewModel.getFirebaseUser().observe(this, firebaseUser -> {
            String userId = firebaseUser.getUid();
            binding.buttonMakeAnAppointment.setOnClickListener(v -> {
                String dateInText = getTrimmedValue(binding.editTextAppointmentDate);
                if (!Validator.isDateValid(dateInText)) {
                    Toast.makeText(
                                    this,
                                    getString(R.string.write_date_in_a_correct_way),
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                Date appointmentDateTime;
                try {
                    appointmentDateTime = parseDate(
                            getTrimmedValue(binding.editTextAppointmentDate),
                            "dd.MM.yyyy"
                    );
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(
                                    this,
                                    getString(R.string.write_date_in_a_correct_way),
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                String bloodComponent = binding.spinnerBloodComponents.getSelectedItem().toString();
                String donationType = getTrimmedValue(binding.radioButtonFreeOfCharge);
                if (binding.radioButtonPaid.isSelected()) {
                    donationType = getTrimmedValue(binding.radioButtonPaid);
                }

                String donationPlace = binding.spinnerDonationPlaces.getSelectedItem().toString();

                if (bloodComponent.isEmpty() || donationPlace.isEmpty()) {
                    Toast.makeText(
                                    this,
                                    getString(R.string.check_validity_of_your_data),
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                Application application = new Application(
                        appointmentDateTime,
                        userId,
                        bloodComponent,
                        donationType,
                        donationPlace,
                        Constants.APPLICATION_ACCEPTED_STATUS
                );

                viewModel.createApplication(application);
            });
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getIsApplicationAdded().observe(this, isApplicationAdded -> {
            if (isApplicationAdded) {
                startActivity(ApplicationsActivity.newIntent(this));
            }
        });
    }

    private void setupSpinners() {
        ArrayAdapter<String> bloodComponentsAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_list_item,
                getResources().getStringArray(R.array.blood_components));
        binding.spinnerBloodComponents.setAdapter(bloodComponentsAdapter);

        ArrayAdapter<String> donationPlacesAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_list_item,
                getResources().getStringArray(R.array.donation_places));
        binding.spinnerDonationPlaces.setAdapter(donationPlacesAdapter);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ApplicationActivity.class);
    }
}