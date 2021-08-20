package com.example.hms.ui.patient.prescription;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.databinding.FragmentPatientPrescriptionBinding;
import com.example.hms.service.API;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionFragment extends Fragment {


    private FragmentPatientPrescriptionBinding binding;
    private PrescriptionAdapter prescriptionAdapter;
    private Intent intent;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPatientPrescriptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        createApdater();
        setControl();
        binding.backId.setOnClickListener(v -> {

            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            }

        });
        return root;

    }
    private void createApdater(){
        intent = new Intent(getContext(), DetailPrescriptionActivity.class);

        prescriptionAdapter = new PrescriptionAdapter(prescription -> {
            intent.putExtra("prescription", prescription);
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.prescriptionRecycleView.setLayoutManager(llm);
        binding.prescriptionRecycleView.setAdapter(prescriptionAdapter);
    }
    private void setControl() {
        API.apiService.getAllPrescription().enqueue(new Callback<List<PrescriptionModel>>() {
            @Override
            public void onResponse(Call<List<PrescriptionModel>> call, Response<List<PrescriptionModel>> response) {
                if(response.code()==200&&response!=null){
                    List<PrescriptionModel> prescriptionModels=response.body();
                    setPrescriptionAdapter(prescriptionModels);
                }
            }

            @Override
            public void onFailure(Call<List<PrescriptionModel>> call, Throwable t) {

            }
        });
    }
    private void setPrescriptionAdapter(List<PrescriptionModel> prescriptionModels){
        if (prescriptionModels.size() > 0) {
            Collections.sort(prescriptionModels, Comparator.comparing(PrescriptionModel::getPrescriptionNumber));
        }
        prescriptionAdapter.setPrescriptions(prescriptionModels);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
    private PublishSubject<Integer> selectPublisher;
    public PublishSubject<Integer> getSelectPublisher() {
        if (selectPublisher == null) {
            selectPublisher = PublishSubject.create();
        }
        return selectPublisher;
    }
}