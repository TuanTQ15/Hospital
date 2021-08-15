package com.example.hms.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hms.service.API;
import com.example.hms.ui.doctor.DoctorActivity;
import com.example.hms.ModelClass.LoginInfo;
import com.example.hms.service.MyApplication;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.ui.patient.PatientActivity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button mLogin;
    private EditText username,password;
    private ProgressBar loadingProgressBar;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadingProgressBar = findViewById(R.id.loading);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        mLogin = findViewById(R.id.button_login);
        userDao= db.userDao();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                String username_ = username.getText().toString().trim();
                String password_ = password.getText().toString().trim();
                if (TextUtils.isEmpty(username_)){
                    loadingProgressBar.setVisibility(View.GONE);
                    username.setError("Enter Username");
                }else if (TextUtils.isEmpty(password_)){
                    loadingProgressBar.setVisibility(View.GONE);
                    password.setError("Enter Password");
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("username", username_)
                            .addFormDataPart("password", password_)
                            .build();
                    API.apiService.login(requestBody).enqueue(new Callback<LoginInfo>() {
                        @Override
                        public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                            int code=response.code();
                            loadingProgressBar.setVisibility(View.GONE);
                            if(code ==200){
                                LoginInfo userLogin =response.body();
                                LoginInfo loginInfo = new LoginInfo(userLogin.getAccess_token(),userLogin.getAccount_role(),userLogin.getToken_type());
                                userDao.deleteAllFromTable();
                                userDao.insertAll(loginInfo);
                                launchViewMainMenu(userLogin.getAccount_role());
                            } else if (code==403) {
                                showNotifyLogin("Sai  mật khẩu");
                            }else if (code==401) {
                                showNotifyLogin("Sai tài khoản");
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginInfo> call, Throwable t) {
                            loadingProgressBar.setVisibility(View.GONE);
                            showNotifyLogin("Đăng nhập thất bại, kiểm tra lại kết nối");
                        }
                    });
                }
            }
        });
    }

    private void launchViewMainMenu(String accountRole) {
        if(accountRole.equals("doctor")){
            startActivity(DoctorActivity.class);
        }else if(accountRole.equals("patient")){
            startActivity(PatientActivity.class);
        }
    }
    private void startActivity( Class<?> cls){
        this.finish();
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    private void showNotifyLogin(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder.setMessage(message);
        builder.setPositiveButton("OK",(dialogInterface,i)->{
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }
}
