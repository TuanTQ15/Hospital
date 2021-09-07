package com.example.hms.ui.patient.dashboard.payment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.Medicine;
import com.example.hms.ModelClass.PaymentModel;
import com.example.hms.ModelClass.ReceiptModel;
import com.example.hms.ModelClass.Room;
import com.example.hms.ModelClass.Services;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;
import com.example.hms.ui.patient.prescription.DetailPrescriptionAdapter;
import com.example.hms.ui.patient.prescription.MedicineActivity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.cancel.OnCancel;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.error.ErrorInfo;
import com.paypal.checkout.error.OnError;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    PaymentButton payPalButton;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    private TextView tvFullName,tvTotalFee,tvAdvances,tvTotal_P,tvRoomFee,tvServiceFee,tvMedicineFee,tvStatus;
    private ServiceAdapter serviceAdapter;
    private MedicineAdapter medicineAdapter;
    private RoomAdapter roomAdapter;
    private RecyclerView serviceRecycle,medicineRecycle,roomRecycle;
    private long roomFee=0,serviceFee=0,medicineFree=0,total=0,total_p=0;
    private DecimalFormat df = new DecimalFormat("###,### VNĐ");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao=db.userDao();
        getHospitalFee(userDao.getLogin().getUsername());
    }

    private void setEvent(PaymentModel payment) {
        if(total<0){
            total=-1*total;
        }
        tvTotalFee.setText(df.format(total));
        tvTotal_P.setText(df.format(total_p));
        tvAdvances.setText(df.format(payment.getAdvances()));
        tvServiceFee.setText(df.format(serviceFee));
        tvRoomFee.setText(df.format(roomFee));
        tvMedicineFee.setText(df.format(medicineFree));
        serviceAdapter.setServices(payment.getServices());
        medicineAdapter.setMedicines(payment.getMedicines());
        roomAdapter.setRooms(payment.getRooms());
        payPalButton = findViewById(R.id.payPalButton);
        if(payment.getStatus()==0){
            tvTotalFee.setText(df.format(total));
            tvStatus.setText("Còn nợ");
        }else if(payment.getStatus()==1){
            tvStatus.setText("Đã hoàn trả");
            tvTotalFee.setText(df.format(total));
            payPalButton.setVisibility(View.GONE);
        }
        setButtonPaypal(payment, total);
    }
    private long getToTal(PaymentModel payment){
        List<Services> services=payment.getServices();
        List<Medicine> medicines =payment.getMedicines();
        List<Room> rooms=payment.getRooms();
        for (Medicine medicine:medicines) {
            medicineFree+=(medicine.getPrice()*medicine.getQuantity());
        }
        for (Services service:services) {
            serviceFee+=(service.getPrice()*service.getQuantity());
        }
        for(Room room : rooms){
            roomFee+=(room.getPrice()*room.getTotalDay());
        }
        total_p=medicineFree+serviceFee+roomFee;
        return payment.getAdvances()-total_p;
    }
    private void setButtonPaypal(PaymentModel payment,long total) {
        DecimalFormat df = new DecimalFormat("###.##");
        String value= df.format(total/ 22435);
        payPalButton.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        ArrayList purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(new PurchaseUnit.Builder()
                                .amount(
                                        new Amount.Builder()
                                                .currencyCode(CurrencyCode.USD)
                                                .value(value)
                                                .build()
                                )
                                .build()
                        );
                        Order order = new Order(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                createReceiptment(payment);
                                lauchComplete(payment);
                            }
                        });
                    }
                },
                new OnCancel() {
                    @Override
                    public void onCancel() {
                        Log.d("OnCancel", "Buyer cancelled the PayPal experience.");
                    }
                },
                new OnError() {
                    @Override
                    public void onError(@NotNull ErrorInfo errorInfo) {
                        showNotify("Payment failed, please try again");
                    }
                }
        );
    }
    private void createReceiptment(PaymentModel payment){
        userDao=db.userDao();
        ReceiptModel receiptModel = new ReceiptModel(total_p,"",medicineFree,payment.getMedicalRecord(),roomFee,payment.getAdvances(),serviceFee,total_p);
        API.apiService.createReceiptment(receiptModel).enqueue(new Callback<ReceiptModel>() {
            @Override
            public void onResponse(Call<ReceiptModel> call, Response<ReceiptModel> response) {
                if(response.code()==200){

                }
            }

            @Override
            public void onFailure(Call<ReceiptModel> call, Throwable t) {
                System.out.println("loi");
            }
        });
    }
    private void lauchComplete(PaymentModel payment) {
        Intent intent = new Intent(this, SuccessActivity.class);
        intent.putExtra("payment", payment);
        startActivity(intent);
    }

    private void createServiceApdater(){
        Intent intent = new Intent(this, MedicineActivity.class);
        serviceAdapter = new ServiceAdapter(service -> {
            intent.putExtra("services", service);
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        serviceRecycle.setLayoutManager(llm);
        serviceRecycle.setAdapter(serviceAdapter);
    }
    private void createRoomApdater(){
        roomAdapter =new RoomAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        roomRecycle.setLayoutManager(llm);
        roomRecycle.setAdapter(roomAdapter);
    }
    private void createMedicineApdater(){
        Intent intent = new Intent(this, MedicineActivity.class);
        medicineAdapter = new MedicineAdapter(medicine -> {
            intent.putExtra("services", medicine);
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        medicineRecycle.setLayoutManager(llm);
        medicineRecycle.setAdapter(medicineAdapter);
    }
    private void setControl(PaymentModel payment) {
        total=getToTal(payment);
        if(total > 0&&payment.getStatus()==0){
            setContentView(R.layout.activity_payback);
            TextView tvTotalPayback =findViewById(R.id.total_payback);
            tvTotalPayback.setText(df.format(total));
        }else {
            setContentView(R.layout.activity_payment);
            userDao=db.userDao();
            tvFullName = findViewById(R.id.full_name);
            tvAdvances = findViewById(R.id.advances);
            tvTotalFee = findViewById(R.id.total_pay);
            tvStatus =findViewById(R.id.status);
            serviceRecycle =findViewById(R.id.service_recycle);
            medicineRecycle =findViewById(R.id.medicine_recycle);
            roomRecycle =findViewById(R.id.room_recycle);
            tvMedicineFee =findViewById(R.id.p_medicine);
            tvRoomFee =findViewById(R.id.p_room);
            tvServiceFee =findViewById(R.id.p_service);
            tvTotal_P =findViewById(R.id.total_fee);
            tvFullName.setText(userDao.getLogin().getFullname());
            createServiceApdater();
            createMedicineApdater();
            createRoomApdater();
            setEvent(payment);
        }
    }

    private void getHospitalFee(String CMND) {
        API.apiService.getHospitalFee(CMND).enqueue(new Callback<PaymentModel>() {
            @Override
            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                if(response.code()==200 && response.body()!=null){
                    PaymentModel payment= response.body();
                    setControl(payment);
                }
            }
            @Override
            public void onFailure(Call<PaymentModel> call, Throwable t) {
                System.out.println("loi");
            }
        });
    }
    private void showNotify(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setMessage(message);
        builder.setPositiveButton("OK",(dialogInterface,i)->{
           dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }
}
