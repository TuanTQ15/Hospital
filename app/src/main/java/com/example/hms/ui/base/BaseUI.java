package com.example.hms.ui.base;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

public interface BaseUI<VB extends ViewDataBinding, VM extends ViewModel> {
    default void showToast(String message, int duration) {
        Toast.makeText(
                (this instanceof AppCompatActivity) ?
                        (((AppCompatActivity) this).getApplicationContext()) :
                        ((Fragment) this).getContext(),
                message,
                duration
        ).show();
    }

    default void showToast(String message){
        showToast(message, Toast.LENGTH_SHORT);
    }

    void createView();

    VM setUpViewModel();
}
