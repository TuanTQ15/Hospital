package com.example.hms.util;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.R;

public class NoDataAcitivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_data);
        TextView backId =findViewById(R.id.backId);
        backId.setOnClickListener(v -> onBackPressed());
    }
}
