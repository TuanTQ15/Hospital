package com.example.hms.ui.patient.dashboard.payment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.ModelClass.Medicine;
import com.example.hms.ModelClass.PaymentModel;
import com.example.hms.ModelClass.Services;
import com.example.hms.R;

import java.text.DecimalFormat;
import java.util.List;

public class SuccessActivity extends AppCompatActivity {
    private TextView tvTotal,tvRoom, tvMedicine,tvService,tvAdvances;
    private PaymentModel payment;
    private DecimalFormat df = new DecimalFormat("###,### VNĐ");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        setControl();
        setEvent();
    }

    private void setEvent() {
        long total=0;
        long medicineFree=0;
        long serviceFee=0;
        long roomFee=payment.getRooms().getPrice()*payment.getRooms().getTotalDay();
        List<Services> services=payment.getServices();
        List<Medicine> medicines =payment.getMedicines();
        for (Medicine medicine:medicines) {
            medicineFree+=(medicine.getPrice()*medicine.getQuantity());
        }
        for (Services service:services) {
            serviceFee+=(service.getPrice()*service.getQuantity());
        }
        total=  payment.getAdvances()-(medicineFree+serviceFee+roomFee);
        tvAdvances.setText(df.format(payment.getAdvances()));
        tvTotal.setText(df.format(total));
        tvRoom.setText(df.format(roomFee));
        tvMedicine.setText(df.format(medicineFree));
        tvService.setText(df.format(serviceFee));
    }

    private void setControl() {
        if (getIntent().getExtras() != null) {
            payment = (PaymentModel) getIntent().getSerializableExtra("payment");
        }

        tvTotal = findViewById(R.id.s_total);
        tvRoom = findViewById(R.id.s_room);
        tvMedicine =findViewById(R.id.s_medine);
        tvService =findViewById(R.id.s_services);
        tvAdvances =findViewById(R.id.s_advances);
    }

}
