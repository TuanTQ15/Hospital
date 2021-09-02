package com.example.hms.ui.patient.prescription;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.R;
import com.example.hms.databinding.FragmentPatientPrescriptionBinding;
import com.example.hms.service.API;
import com.example.hms.ui.patient.PatientActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionFragment extends Fragment {


    private FragmentPatientPrescriptionBinding binding;
    private PrescriptionAdapter prescriptionAdapter;
    private ActionBar actionBar;
    private Intent intent;
    List<PrescriptionModel> prescriptions;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPatientPrescriptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        actionBar =((AppCompatActivity) getActivity()).getSupportActionBar();
        createApdater();
        setControl();
        setHasOptionsMenu(true);
        return root;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView (actionBar.getThemedContext());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        setupSearch(menu);
    }

    SearchView searchView = null;
    private MenuItem searchMenuItem;
    private void setupSearch(Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);

        if (searchMenuItem != null) {
            searchView = (SearchView) searchMenuItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("Type here to search");
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //result khen click search btn
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterContact(newText);
                return true;
            }
        });

    }   private void filterContact(String strSearch){
        List<PrescriptionModel> filter = this.prescriptions.stream().filter(prescription -> {
            if(prescription == null || prescription.getPrescriptionNumber().isEmpty())
            {return true;}else {

                return (prescription.getPrescriptionNumber().contains(strSearch));
            }
        }).collect(Collectors.toList());
        setPrescriptionAdapter(filter);
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
                    prescriptions=response.body();
                    setPrescriptionAdapter(prescriptions);
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