package com.example.blooddonation;

import static com.example.blooddonation.Constants.APPLICATION_ACCEPTED_STATUS;
import static com.example.blooddonation.Constants.APPLICATION_CLOSED_STATUS;
import static com.example.blooddonation.Constants.APPLICATION_EXTRA;
import static com.example.blooddonation.Utils.dateToString;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ExperimentalGetImage;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.blooddonation.databinding.ActivityApplicationManagementBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ApplicationManagementActivity extends AppCompatActivity {

    private static final String TAG = "ApplicationManagementActivity";

    private ActivityApplicationManagementBinding binding;
    private ApplicationManagementViewModel viewModel;
    private String applicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicationManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        applicationId = getIntent().getStringExtra(APPLICATION_EXTRA);
        viewModel = new ViewModelProvider(this).get(ApplicationManagementViewModel.class);
        observeViewModel();
        viewModel.loadApplication(applicationId);

        setupClickListeners();
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                fillUiWithUser(user);
            }
        });
        viewModel.getApplication().observe(this, application -> {
            if (application != null) {
                fillUIWithApplication(application);
            }
        });
    }

    @SuppressLint({"SimpleDateFormat"})
    private void fillUiWithUser(User user) {
        binding.textViewLastName.setText(getString(R.string.last_name_template, user.getLastName()));
        binding.textViewFirstName.setText(getString(R.string.first_name_template, user.getFirstName()));
        binding.textViewMiddleName.setText(getString(R.string.middle_name_template, user.getMiddleName()));
        binding.textViewEmail.setText(getString(R.string.email_template, user.getEmail()));
        binding.textViewSex.setText(getString(R.string.sex_template, user.getSex()));
        binding.textViewAge.setText(new SimpleDateFormat("dd.MM.yyyy").format(user.getDateOfBirth()));
        binding.textViewSeriesAndNumber.setText(getString(R.string.series_and_number_template, user.getSeriesAndNumber()));
    }

    private void fillUIWithApplication(Application application) {
        switch (application.getStatus()) {
            case "ACCEPTED":
                binding.textViewStatus.setText(
                        getString(R.string.accepted)
                );
                break;
            case "CLOSED":
                binding.textViewStatus.setText(
                        getString(R.string.closed)
                );
                break;
        }
        binding.textViewDate.setText(
                new SimpleDateFormat("dd.MM.yyyy").format(application.getDonationDate())
        );
        binding.textViewBloodComponent.setText(application.getBloodComponent());
        binding.textViewDonationType.setText(application.getDonationType());
        binding.textViewDonationPlace.setText(application.getDonationPlace());
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void  setupClickListeners() {
        binding.fabLogGoBack.setOnClickListener(v -> {
            startActivity(QRActivity.newIntent(this));
        });
        binding.radioButtonAccepted.setOnClickListener(v -> {
            if (!binding.radioButtonAccepted.isSelected()) {
                viewModel.updateStatus(applicationId, APPLICATION_ACCEPTED_STATUS);
            }
        });
        binding.radioButtonClosed.setOnClickListener(v -> {
            if (!binding.radioButtonClosed.isSelected()) {
                viewModel.updateStatus(applicationId, APPLICATION_CLOSED_STATUS);
            }
        });
    }

    public static Intent newIntent(Context context, String applicationId) {
        Intent intent = new Intent(context, ApplicationManagementActivity.class);
        intent.putExtra(APPLICATION_EXTRA, applicationId);
        return intent;
    }
}