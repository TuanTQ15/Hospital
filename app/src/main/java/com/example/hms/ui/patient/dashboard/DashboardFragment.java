package com.example.hms.ui.patient.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.hms.databinding.FragmentPatientDashboardBinding;

import io.reactivex.rxjava3.subjects.PublishSubject;
import com.example.hms.R;

public class DashboardFragment extends Fragment {

    private FragmentPatientDashboardBinding binding;
    private CardView contactUs;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPatientDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setControl();
        setEvent();
        return root;
    }

    private void setEvent() {
        contactUs.setOnClickListener(v -> {
            ContactUsFragment nextFrag= new ContactUsFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(((ViewGroup)getView().getParent()).getId(), nextFrag, getString(R.string.contact_us))
                    .addToBackStack(getString(R.string.dashboard))
                    .commit();
        });
    }

    private void setControl() {
        contactUs=binding.contactUs;
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