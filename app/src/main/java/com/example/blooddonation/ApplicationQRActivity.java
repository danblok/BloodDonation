package com.example.blooddonation;

import static com.example.blooddonation.Constants.APPLICATION_EXTRA;
import static com.example.blooddonation.Constants.QR_CODE_API_QUERY_TEMPLATE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.blooddonation.databinding.ActivityApplicationQractivityBinding;

public class ApplicationQRActivity extends AppCompatActivity {

    private ActivityApplicationQractivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicationQractivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Application application = (Application) getIntent().getSerializableExtra(APPLICATION_EXTRA);
        Glide
                .with(this)
                .load(String.format(QR_CODE_API_QUERY_TEMPLATE, application.getId()))
                .into(binding.imageViewQR);

        binding.fabGoBack.setOnClickListener(v -> {
            startActivity(ApplicationsActivity.newIntent(this));
        });
    }

    public static Intent newIntent(Context context, Application application) {
        Intent intent = new Intent(context, ApplicationQRActivity.class);
        intent.putExtra(APPLICATION_EXTRA, application);
        return intent;
    }
}