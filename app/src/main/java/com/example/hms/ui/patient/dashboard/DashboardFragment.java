package com.example.hms.ui.patient.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hms.databinding.FragmentPatientDashboardBinding;


public class DashboardFragment extends Fragment {

    private FragmentPatientDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPatientDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //final TextView textView = binding.textDashboard;
      //  textView.setText("");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}