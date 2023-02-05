package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ExperimentalGetImage;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @ExperimentalGetImage
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}