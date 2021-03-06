package com.example.hms.ui.patient.profile;

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
import com.example.hms.ModelClass.LoginInfoModel;
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.ModelClass.UserPatient;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.databinding.FragmentPatientEditProfileBinding;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;
import com.example.hms.ui.patient.PatientActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.rxjava3.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private static final String PATTERN_DATE="dd/MM/yyyy";
    private static int REQUEST_CODE = 1999;
    private int time_7h=25200100;
    private ProgressBar loadingProgressBar;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    private FragmentPatientEditProfileBinding binding;
    boolean isFullName,isIdentityNumber,isEmail,isMobileNumber,isBirthDay,isAddress,isGender;
    private PatientModel patient;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPatientEditProfileBinding.inflate(inflater, container, false);
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

    private void setValueForProfile(){
        if (validateProfile()) {
            String fullName = binding.metFullName.getText().toString().trim();
            String idNumber = binding.metIdentityNumber.getText().toString().trim();
            String phoneNumber = binding.metMobileNumber.getText().toString().trim();
            String email = binding.metEmail.getText().toString().trim();
            String gender = binding.spGender.getSelectedItem().toString().trim();
            long birthDay = convertTimeToLong(binding.metDob.getText().toString().trim())+25200100;
            String imageBase64="data:image/png;base64,"+convertBase64(((BitmapDrawable)binding.patientImage.getDrawable()).getBitmap());
            String address= binding.metAddress.getText().toString().trim();
            PatientModel patient= new PatientModel(idNumber,fullName,email,phoneNumber,address,gender,
                    birthDay,imageBase64);
            loadingProgressBar.setVisibility(View.VISIBLE);
            API.apiService.updatePatient(patient.getCMND(),patient).enqueue(new Callback<PatientModel>() {
                @Override
                public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                    int code=response.code();

                    if(code!=200 && response.body()==null){
                        loadingProgressBar.setVisibility(View.GONE);
                        showNotify("L???i k???t n???i server",false);
                    }else{
                        setNav(response.body().getHOTEN(),response.body().getEMAIL());

                    }

                }

                @Override
                public void onFailure(Call<PatientModel> call, Throwable t) {
                    showNotify("Ki???m tra l???i Internet",false);
                    loadingProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }
    private void setNav(String fullName,String email){
       try{
           NavigationView navigationView= getActivity().findViewById(R.id.patient_nav_view);
           View header = navigationView.getHeaderView(0);
           TextView tvName =header.findViewById(R.id.user_name);
           TextView tvemail = header.findViewById(R.id.email_user);
           tvName.setText(fullName);
           tvemail.setText(email);
           loadingProgressBar.setVisibility(View.GONE);
           showNotify("C???p nh???t th??ng tin th??nh c??ng",true);
       }catch (Exception e){

       }

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
    private void setControl() {
        binding.calendarChoose.setOnClickListener(v -> getDatePicker().show());
        loadingProgressBar=binding.loading;
        userDao= db.userDao();
        loadingProgressBar.setVisibility(View.VISIBLE);
        API.apiService.getPatient(userDao.getLogin().getUsername()).enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                int code=response.code();
                patient=response.body();
                if(code==200){
                    setUpPatient();
                }
                loadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                loadingProgressBar.setVisibility(View.GONE);
                showNotify("K???t n???i th???t b???i, ki???m tra l???i k???t n???i",false);
            }
        });

        binding.btnUpdate.setOnClickListener(v -> setValueForProfile());
    }
    @NonNull
    protected DatePickerDialog getDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog= new DatePickerDialog(getContext(),
                getDatePickerCallBack(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;

    }
    private DatePickerDialog.OnDateSetListener datePickerCallBack;

    protected DatePickerDialog.OnDateSetListener getDatePickerCallBack() {

        if (datePickerCallBack == null) {
            datePickerCallBack = (view, year, monthOfYear, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                patient.setNGAYSINH(calendar.getTimeInMillis());
                binding.metDob.setText(convertTime(calendar.getTimeInMillis()));
            };
        }
        return datePickerCallBack;
    }
    private void setUpPatient(){
        try{
            binding.metIdentityNumber.setText(patient.getCMND());
            binding.metFullName.setText(patient.getHOTEN());
            binding.metAddress.setText(patient.getDIACHI());
            binding.metDob.setText(patient.getNGAYSINH());
            binding.metEmail.setText(patient.getEMAIL());
            binding.metMobileNumber.setText(patient.getSODIENTHOAI());
            setUpGenderSpinner();
            setImage();
        }catch (Exception e){

        }
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
                }).into(binding.patientImage);

    }

    private void setUpGenderSpinner(){
        ArrayAdapter<CharSequence> createFromResource2 = ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.item_spinner);
        createFromResource2.setDropDownViewResource(R.layout.item_spinner);
        binding.spGender.setAdapter( createFromResource2);
        binding.spGender.setSelection(getIndexGender());
    }
    private int getIndexGender(){
        String gender=patient.getGIOITINH();
        switch (gender){
            case "Nam":
                return 1;
            case "N???":
                return 2;
        }
       return 0;
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

}