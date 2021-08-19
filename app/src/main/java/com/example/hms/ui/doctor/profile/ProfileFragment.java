package com.example.hms.ui.doctor.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


import com.example.hms.databinding.FragmentDoctorEditProfileBinding;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class ProfileFragment extends Fragment {


    private FragmentDoctorEditProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDoctorEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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