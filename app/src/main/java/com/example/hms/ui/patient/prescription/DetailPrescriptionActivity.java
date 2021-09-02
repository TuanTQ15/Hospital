package com.example.hms.ui.patient.prescription;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.DetailPrescription;
import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.R;

import java.util.List;
import java.util.stream.Collectors;

public class DetailPrescriptionActivity  extends AppCompatActivity {
    private TextView backId;
    private PrescriptionModel prescription=null;
    private DetailPrescriptionAdapter detailPrescriptionAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prescription);
        setControl();
        createApdater();
        setEvent();
    }

    private void setEvent() {
        if (getIntent().getExtras() != null) {
            prescription = (PrescriptionModel) getIntent().getSerializableExtra("prescription");
        }
        if(prescription!=null){
            setDetailPrescriptionAdapter(prescription.getDetailPrescriptionList());
        }
    }
    private void createApdater(){
        Intent intent = new Intent(this, MedicineActivity.class);
           detailPrescriptionAdapter = new DetailPrescriptionAdapter(detailPrescription -> {
            intent.putExtra("detailPrescription", detailPrescription);
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(detailPrescriptionAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        setupSearch(menu);
        return super.onCreateOptionsMenu(menu);
    }

    SearchView searchView = null;
    private MenuItem searchMenuItem;
    private void setupSearch(Menu menu) {
        SearchManager searchManager = (SearchManager) DetailPrescriptionActivity.this.getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);

        if (searchMenuItem != null) {
            searchView = (SearchView) searchMenuItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(DetailPrescriptionActivity.this.getComponentName()));
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

    }

    private void filterContact(String strSearch){
        List<DetailPrescription> filter = this.prescription.getDetailPrescriptionList();
        filter.stream().filter(detailPrescription -> {
            if(detailPrescription == null || detailPrescription.getPrescriptionNumber().isEmpty())
            {return true;}else {

                return (detailPrescription.getPrescriptionNumber().contains(strSearch));
            }
        }).collect(Collectors.toList());
        setDetailPrescriptionAdapter(filter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setControl() {
        recyclerView=findViewById(R.id.detail_prescription_recycle_view);
        backId =findViewById(R.id.backId);
        backId.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    private void setDetailPrescriptionAdapter(List<DetailPrescription> detailPrescriptionList){
        detailPrescriptionAdapter.setPrescriptions(detailPrescriptionList);
    }
}
