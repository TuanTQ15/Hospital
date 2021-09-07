package com.example.hms.ui.doctor.password;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hms.ModelClass.ChangePassword;
import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.databinding.FragmentDoctorChangePasswordBinding;
import com.example.hms.databinding.FragmentDoctorEditProfileBinding;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;

import java.util.Locale;

import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    private FragmentDoctorChangePasswordBinding binding;
    private EmployeeModel employee;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorChangePasswordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setEvent();
        return root;
    }

    private void setEvent() {
        binding.buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_ = binding.userName.getText().toString().trim();
                String password_ = binding.pass.getText().toString().trim();
                String confirmPassword_ = binding.passConfirm.getText().toString().trim();
                binding.loading.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(username_)){
                    binding.userName.setError("Enter Username");
                }else if (TextUtils.isEmpty(password_)){
                    binding.pass.setError("Enter Password");
                }else if (TextUtils.isEmpty(confirmPassword_)){
                    binding.passConfirm.setError("Enter Password");
                }
                if (!password_.equals(confirmPassword_)){
                    binding.passConfirm.setError("Password Not Match");
                }else {
                    ChangePassword changePassword = new ChangePassword(username_,password_);
                    API.apiService.changePassswordEmployee(changePassword).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            int code=response.code();
                            binding.loading.setVisibility(View.GONE);
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
                binding.loading.setVisibility(View.GONE);
            }
        });
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