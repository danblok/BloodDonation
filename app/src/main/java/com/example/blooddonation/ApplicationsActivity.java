package com.example.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonation.databinding.ActivityDonationsBinding;

public class ApplicationsActivity extends AppCompatActivity {

    private static final String TAG = "ApplicationsActivity";

    private ActivityDonationsBinding binding;
    private ApplicationsViewModel viewModel;
    private ApplicationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ApplicationsViewModel.class);
        observeViewModel();

        adapter = new ApplicationsAdapter();
        adapter.setOnApplicationClickListener(application -> {
            startActivity(ApplicationQRActivity.newIntent(this, application));
        });
        binding.recyclerViewApplications.setAdapter(adapter);
        setItemTouchHelper(binding.recyclerViewApplications);
        setupOnClickListeners();
    }

    private void setupOnClickListeners() {
        binding.fabAddApplication.setOnClickListener(view -> {
            startActivity(ApplicationActivity.newIntent(this));
        });
        binding.fabLogSignOut.setOnClickListener(view -> {
            viewModel.signOut();
            startActivity(LoginActivity.newIntent(this));
        });
    }

    private void observeViewModel() {
        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getApplications().observe(this, applications -> {
            adapter.setApplications(applications);
        });
    }

    private void setItemTouchHelper(RecyclerView recyclerView) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            int direction
                    ) {
                        int position = viewHolder.getAdapterPosition();

                        viewModel.removeApplication(
                                adapter.getApplications().get(position).getId()
                        );
                        viewModel.loadApplications();
                    }
                }
        );

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ApplicationsActivity.class);
    }
}