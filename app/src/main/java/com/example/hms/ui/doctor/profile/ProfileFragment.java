package com.example.hms.ui.doctor.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.LoginInfoModel;
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.ModelClass.UserDoctor;
import com.example.hms.ModelClass.UserPatient;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.databinding.FragmentDoctorEditProfileBinding;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    boolean isFullName,isEmail,isMobileNumber,isBirthDay,isAddress,isGender;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    private FragmentDoctorEditProfileBinding binding;
    private EmployeeModel employee;
    private static int REQUEST_CODE = 1999;
    private int time_7h=25200100;
    private static final String PATTERN_DATE="dd/MM/yyyy";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDoctorEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setControl();
        setEvent();
        return root;
    }
    private void setEvent() {
        binding.btnSelectPic.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }
    private void setControl() {
        userDao= db.userDao();
        binding.calendarChoose.setOnClickListener(v -> getDatePicker().show());
        API.apiService.getEmployee(userDao.getLogin().getUsername()).enqueue(new Callback<EmployeeModel>() {
            @Override
            public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                if(response.code()==200& response.body()!=null){
                    employee=response.body();
                    setUpDoctor(employee);
                }
            }

            @Override
            public void onFailure(Call<EmployeeModel> call, Throwable t) {

            }
        });
        binding.btnUpdate.setOnClickListener(v -> setValueForProfile());
    }
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat(PATTERN_DATE);
        return format.format(date);
    }

    private DatePickerDialog.OnDateSetListener datePickerCallBack;
    protected DatePickerDialog.OnDateSetListener getDatePickerCallBack() {
        if (datePickerCallBack == null) {
            datePickerCallBack = (view, year, monthOfYear, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                employee.setBirthday(calendar.getTimeInMillis());
                binding.metDob.setText(convertTime(calendar.getTimeInMillis()));
            };
        }
        return datePickerCallBack;
    }
    @NonNull
    protected DatePickerDialog getDatePicker() {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
                getContext(),
                getDatePickerCallBack(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    private void setUpDoctor(EmployeeModel employee){
        try{
            binding.employeeNumber.setText(employee.getEmployeeNumber());
            binding.metFullName.setText(employee.getFullName());
            binding.metAddress.setText(employee.getAddress());
            binding.metDob.setText(employee.getBirthday());
            binding.metEmail.setText(employee.getEmail());
            binding.metMobileNumber.setText(employee.getPhoneNumber());
            binding.position.setText(employee.getPosition());
            setUpGenderSpinner();
            setImage();
        }catch (Exception e){

        }
    }
    private int getIndexGender(){
        String gender=employee.getGender();
        switch (gender.toUpperCase(Locale.ROOT)){
            case "NAM":
                return 1;
            case "NỮ":
                return 2;
        }
        return 0;
    }
    private void setImage() {
        userDao= db.userDao();
        String url=userDao.getLogin().getImage_url();
        Glide.with(getContext()).load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.default_user)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(binding.doctorImage);

    }
    private void setUpGenderSpinner(){
        ArrayAdapter<CharSequence> createFromResource2 = ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.item_spinner);
        createFromResource2.setDropDownViewResource(R.layout.item_spinner);
        binding.spGender.setAdapter( createFromResource2);
        binding.spGender.setSelection(getIndexGender());
    }
    private boolean validateProfile() {
        if (!binding.metFullName.getText().toString().trim().equals("")) {
            this.isFullName = true;
        } else {
            this.isFullName = false;
            binding.metFullName.setError(getString(R.string.enter_your_name));
        }
        if (!binding.metEmail.getText().toString().equals("")) {
            this.isEmail = true;
        } else {
            binding.metEmail.setError(getString(R.string.enter_your_email));
            this.isEmail = false;
        }
        if (binding.metEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            this.isEmail = true;
        } else {
            this.isEmail= false;
            binding.metEmail.setError(getString(R.string.valid_email_message));
        }
        if (!binding.metMobileNumber.getText().toString().equals("")) {
            this.isMobileNumber = true;
        } else {
            this.isMobileNumber = false;
            binding.metMobileNumber.setError(getString(R.string.enter_mobile_number));
        }
        if (binding.spGender.getSelectedItem().toString().equals("") || binding.spGender.getSelectedItem().toString().equals("-Select One-")) {
            this.isGender = false;
            ((TextView) binding.spGender.getChildAt(0)).setError(getString(R.string.gender));
        } else {
            this.isGender = true;
        }
        if (!binding.metDob.getText().toString().equals("")) {
            this.isBirthDay = true;
        } else {
            this.isBirthDay = false;
            binding.metDob.setError(getString(R.string.select_your_date_of_birth));
        }
        if (!binding.metAddress.getText().toString().equals("")) {
            this.isAddress = true;
        } else {
            this.isAddress = false;
            binding.metAddress.setError(getString(R.string.enter_your_address));
        }
        if (!this.isFullName || !this.isMobileNumber || !this.isEmail
                || !this.isBirthDay || !this.isGender || !this.isAddress) {
            return false;
        }
        return true;
    }
    private void setValueForProfile(){
        if (validateProfile()) {
            String fullName = binding.metFullName.getText().toString().trim();
            String idNumber = binding.employeeNumber.getText().toString().trim();
            String phoneNumber = binding.metMobileNumber.getText().toString().trim();
            String email = binding.metEmail.getText().toString().trim();
            String gender = binding.spGender.getSelectedItem().toString().trim();
            long birthDay = convertTimeToLong(binding.metDob.getText().toString().trim())+25200100;
            String imageBase64="data:image/png;base64,"+convertBase64(((BitmapDrawable)binding.doctorImage.getDrawable()).getBitmap());
            String address= binding.metAddress.getText().toString().trim();
            EmployeeModel new_employee= new EmployeeModel(idNumber,fullName,gender,address,birthDay,imageBase64,employee.getPosition(),
                    phoneNumber,email);
            binding.loading.setVisibility(View.VISIBLE);
            API.apiService.udapteEmployee(employee.getEmployeeNumber(),new_employee).enqueue(new Callback<EmployeeModel>() {
                @Override
                public void onResponse(Call<EmployeeModel> call, Response<EmployeeModel> response) {
                    binding.loading.setVisibility(View.GONE);
                    int code=response.code();
                    if(code!=200 && response.body()==null){
                        showNotify("Lỗi kết nối server",false);
                    }else{
                        getLoginInfo(response.body().getFullName(),response.body().getEmployeeNumber(),response.body().getEmail());
                        showNotify("Cập nhật thông tin thành công",true);
                    }
                }

                @Override
                public void onFailure(Call<EmployeeModel> call, Throwable t) {
                    showNotify("Kiểm tra lại Internet",false);
                    binding.loading.setVisibility(View.GONE);
                }
            });


        }
    }
    private void getLoginInfo(String fullName,String maNV,String email) {
        API.apiService.getLoginDoctor(maNV).enqueue(new Callback<UserDoctor>() {
            @Override
            public void onResponse(Call<UserDoctor> call, Response<UserDoctor> response) {
                if(response.body()!=null&response.code()==200){
                    LoginInfoModel userLogin=userDao.getLogin();
                    userLogin.setImage_url(response.body().getImage());
                    userDao.update(userLogin);
                    setNav(fullName,email,response.body().getImage());

                }
            }

            @Override
            public void onFailure(Call<UserDoctor> call, Throwable t) {
                System.out.println("");
            }
        });
    }
    private void setNav(String fullName,String email, String image_url){
        NavigationView navigationView= getActivity().findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvName =header.findViewById(R.id.user_name);
        TextView tvemail = header.findViewById(R.id.email_user);
        tvName.setText(fullName);
        tvemail.setText(email);
    }
    private void setImage(ImageView  imageView,String uri) {
        Glide.with(this).load(uri)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.default_user)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView);
    }
    private void showNotify(String message, boolean isSuccess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom);
        builder.setMessage(message);
        builder.setPositiveButton("OK",(dialogInterface,i)->{
            if(isSuccess){
                getActivity().getSupportFragmentManager().popBackStack();
            }else
                dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
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

    private byte[] imageToByteArray(Bitmap bitmapImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        return baos.toByteArray();
    }
    private String convertBase64(Bitmap bitmap){
        byte[] imageBytes = imageToByteArray(bitmap);
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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