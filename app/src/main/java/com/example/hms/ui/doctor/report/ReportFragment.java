package com.example.hms.ui.doctor.report;

import android.app.ProgressDialog;
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

import com.example.hms.databinding.FragmentDoctorReportBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class ReportFragment extends Fragment {

    private FragmentDoctorReportBinding binding;
    TextView tvR, tvPython, tvCPP, tvJava;
    private ProgressDialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDoctorReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.tvR.setText(String.valueOf(59));
        binding.tvPython.setText(String.valueOf(93));
        binding.tvCPP.setText(String.valueOf(23));
        binding.tvJava.setText(String.valueOf(61));
        binding.piechart.addPieSlice(new PieModel("Tim",59,
                Color.parseColor("#FFA726")));
        binding.piechart.addPieSlice(new PieModel("Lâm Sàng",93,
                Color.parseColor("#66BB6A")));
        binding.piechart.addPieSlice(new PieModel("Mắt",23,
                Color.parseColor("#EF5350")));
        binding.piechart.addPieSlice(new PieModel("Truyền Nhiễm",61,
                Color.parseColor("#29B6F6")));
        binding.piechart.startAnimation();
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