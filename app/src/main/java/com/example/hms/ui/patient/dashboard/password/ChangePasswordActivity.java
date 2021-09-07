package com.example.hms.ui.patient.dashboard.password;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.ModelClass.ChangePassword;
import com.example.hms.R;
import com.example.hms.service.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextView tvUserName,tvPassword,tvConfirmPassword;
    private ProgressBar loadingProgressBar;
    private Button btnChangePass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_doctor_change_password);
        setControl();
        setEvent();
    }

    private void setControl() {
        tvUserName = findViewById(R.id.user_name);
        tvPassword = findViewById(R.id.pass);
        tvConfirmPassword = findViewById(R.id.pass);
        loadingProgressBar = findViewById(R.id.loading);
        btnChangePass = findViewById(R.id.button_change);
    }
    private void setEvent() {
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_ = tvUserName.getText().toString().trim();
                String password_ = tvPassword.getText().toString().trim();
                String confirmPassword_ = tvConfirmPassword.getText().toString().trim();
                loadingProgressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(username_)){
                    tvUserName.setError("Enter Username");
                }else if (TextUtils.isEmpty(password_)){
                    tvPassword.setError("Enter Password");
                }else if (TextUtils.isEmpty(confirmPassword_)){
                    tvConfirmPassword.setError("Enter Password");
                }
                if (!password_.equals(confirmPassword_)){
                    tvConfirmPassword.setError("Password Not Match");
                }else {
                    ChangePassword changePassword = new ChangePassword(username_,password_);
                    API.apiService.changePasssword(changePassword).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            int code=response.code();
                            loadingProgressBar.setVisibility(View.GONE);
                            if(code ==200){
                                showNotifyLogin("Thay đổi mật khẩu thành công");
                            } else if (code==401) {
                                showNotifyLogin("Sai tài khoản");
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            System.out.println();
                        }
                    });
                }
                loadingProgressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showNotifyLogin(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder.setMessage(message);
        builder.setPositiveButton("OK",(dialogInterface,i)->{
            onBackPressed();
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }
}
