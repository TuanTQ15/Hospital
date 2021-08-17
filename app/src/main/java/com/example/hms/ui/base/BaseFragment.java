package com.example.hms.ui.base;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

public abstract class BaseFragment<VB extends ViewDataBinding, VM extends ViewModel> extends Fragment implements BaseUI<VB, VM> {
    @LayoutRes
    private int layoutRes;
    protected VB binding;
    protected VM viewModel;

    private BaseFragment() {
    }

    protected BaseFragment(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), layoutRes, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = setUpViewModel();
        createView();
    }

    protected DatePickerDialog.OnDateSetListener getDatePickerCallBack(){
        return null;
    }

    @NonNull
    protected DatePickerDialog getDatePicker() {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
                getContext(),
                getDatePickerCallBack(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
