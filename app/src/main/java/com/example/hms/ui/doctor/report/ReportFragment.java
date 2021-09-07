package com.example.hms.ui.doctor.report;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hms.ModelClass.StatisticTreament;
import com.example.hms.R;
import com.example.hms.databinding.FragmentDoctorReportBinding;
import com.example.hms.service.API;
import com.example.hms.ui.patient.dashboard.medicalhistory.MedicalHistoryAdapter;
import com.example.hms.ui.patient.prescription.DetailPrescriptionActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportFragment extends Fragment {

    private FragmentDoctorReportBinding binding;
    private ReportAdapter reportAdapter;
    private static final String PATTERN_DATE="dd/MM/yyyy";
    private long dateFrom=1,dateTo=1;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        createApdater();
        setControl();
        return root;
    }

    private void setControl() {
        binding.btnOk.setEnabled(false);
        binding.chooseDateFrom.setOnClickListener(v -> getDatePicker1().show());
        binding.chooseDateTo.setOnClickListener(v -> {
            getDatePicker2().show();
            if(dateFrom!=0&&dateTo!=0){
                binding.btnOk.setEnabled(true);
            }else {
                binding.btnOk.setEnabled(false);
            }
        });
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateSet();
            }
        });
    }

    private void getDateSet() {
        API.apiService.getDataSet(dateFrom,dateTo).enqueue(new Callback<List<StatisticTreament>>() {
            @Override
            public void onResponse(Call<List<StatisticTreament>> call, Response<List<StatisticTreament>> response) {
                if(response.code()==200&response.body()!=null){
                    List<StatisticTreament> dataSet =response.body();
                    setUpChart(dataSet);
                }else if(response.code()==406){
                    showNotifyLogin("Vui lòng chọn ngày hợp lệ");
                }
            }
            @Override
            public void onFailure(Call<List<StatisticTreament>> call, Throwable t) {
                System.out.println("loi");
            }
        });
    }
    @NonNull
    protected DatePickerDialog getDatePicker1() {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
                getContext(),
                getDatePickerCallBack1(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    private DatePickerDialog.OnDateSetListener datePickerCallBack1;
    protected DatePickerDialog.OnDateSetListener getDatePickerCallBack1() {
        if (datePickerCallBack1 == null) {
            datePickerCallBack1 = (view, year, monthOfYear, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateFrom = calendar.getTimeInMillis();
                binding.dateFrom.setText(convertTime(calendar.getTimeInMillis()));
            };
        }
        return datePickerCallBack1;
    }
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat(PATTERN_DATE);
        return format.format(date);
    }
    private long convertTimeToLong(String date){
        long milliseconds=0;
        SimpleDateFormat f = new SimpleDateFormat(PATTERN_DATE);
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
    @NonNull
    protected DatePickerDialog getDatePicker2() {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
                getContext(),
                getDatePickerCallBack2(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    private DatePickerDialog.OnDateSetListener datePickerCallBack2;
    protected DatePickerDialog.OnDateSetListener getDatePickerCallBack2() {
        if (datePickerCallBack2 == null) {
            datePickerCallBack2 = (view, year, monthOfYear, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateTo = calendar.getTimeInMillis();
                binding.dateTo.setText(convertTime(calendar.getTimeInMillis()));

            };
        }
        return datePickerCallBack2;
    }
    private void setUpChart(List<StatisticTreament> statisticTreamentList){

        binding.piechart.setUsePercentValues(true);
        binding.piechart.getDescription().setEnabled(false);
        binding.piechart.setExtraOffsets(5,10,5,5);
        binding.piechart.setDragDecelerationFrictionCoef(0.95f);
        binding.piechart.setDrawHoleEnabled(true);
        binding.piechart.setHoleColor(Color.WHITE);
        binding.piechart.setTransparentCircleRadius(40f);
        binding.piechart.setDrawEntryLabels(false);
        Legend legend =binding.piechart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        ArrayList <PieEntry> values= new ArrayList<>();
        for(StatisticTreament sta : statisticTreamentList){
            if(sta.getQuantity()>0){
                values.add( new PieEntry(sta.getQuantity(),sta.getNameDepartment()));
            }

        }
        PieDataSet dataSet=new PieDataSet(values,"");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(14f);
        dataSet.setDrawValues(true);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(binding.piechart));
        binding.piechart.setData(data);
        binding.piechart.animateY(1000,Easing.EaseInOutCubic);
        reportAdapter.setReport(statisticTreamentList);
        binding.listData.setAdapter(reportAdapter);

    }
    private void showNotifyLogin(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
        builder.setMessage(message);
        builder.setPositiveButton("OK",(dialogInterface,i)->{
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }
    private void createApdater(){
        reportAdapter = new ReportAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        binding.listData.setLayoutManager(llm);
        binding.listData.setAdapter(reportAdapter);
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