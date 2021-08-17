package com.example.hms.ui.doctor.report;

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

import com.example.hms.databinding.FragmentDoctorReportBinding;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class ReportFragment extends Fragment {

    private FragmentDoctorReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDoctorReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        textView.setText("this report");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private PublishSubject<Integer> selectPublisher;
    public PublishSubject<Integer> getSelectPublisher() {
        if (selectPublisher == null) {
            selectPublisher = PublishSubject.create();
        }
        return selectPublisher;
    }
}