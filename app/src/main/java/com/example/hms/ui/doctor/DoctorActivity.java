package com.example.hms.ui.doctor;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hms.R;
import com.example.hms.databinding.ActivityDoctorBinding;
import com.example.hms.databinding.LayoutHeaderDrawerBinding;
import com.example.hms.ui.base.BaseActivity;
import com.example.hms.ui.doctor.profile.ProfileFragment;
import com.example.hms.ui.doctor.report.ReportFragment;
import com.example.hms.ui.login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class DoctorActivity extends BaseActivity<ActivityDoctorBinding, DoctorViewModel> {

    private ActionBar actionBar;
    private ActionBarDrawerToggle drawerToggle;

    public DoctorActivity() {
        super(R.layout.activity_doctor);
    }

    @Override
    public DoctorViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(DoctorViewModel.class);
    }

    private LayoutHeaderDrawerBinding headerDrawerBinding;

    private void backButton() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu);
//        drawable.setColorFilter(getResources().getColor(R.color.white) , PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(drawable);
        headerDrawerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_header_drawer, binding.navView, false);
    }

    @Override
    public void createView() {
        setSupportActionBar(binding.toolBar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        backButton();
        openFragment(getReportFragment(), getString(R.string.report));
        drawerToggle = new ActionBarDrawerToggle(this, binding.activityMainDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.activityMainDrawer.addDrawerListener(drawerToggle);
        binding.navView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.doctor_nav_logout:
                    startActivity(LoginActivity.class);
                    break;
                case R.id.doctor_nav_report:
                    openFragment(getReportFragment(), getString(R.string.report));
                    break;
                case R.id.doctor_nav_edit_profile:
                    openFragment(getProfileFragment(), getString(R.string.edit_profile_doctor));
                    break;
            }
            binding.activityMainDrawer.closeDrawer(binding.navView);
            return drawerToggle.onOptionsItemSelected(item);
        });
    }

    private <F extends Fragment> void openFragment(F fragment, String tag) {
        actionBar.setTitle(tag);
        openFragment(R.id.doctor_container, fragment, tag);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return drawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private long clickFirstTime = 0;
    private void startActivity(Class<?> cls){
        this.finish();
        Intent intent= new Intent(this, cls);
        startActivity(intent);
    }
    protected void twiceTimeToExit() {
        if (clickFirstTime == 0L) {
            clickFirstTime = System.currentTimeMillis();
            showToast(getString(R.string.mess_when_click_back_btn));
        } else {
            if (System.currentTimeMillis() - clickFirstTime < 2000L) {
                finishAffinity();
            }
        }
    }

    @Override
    public void onBackPressed() {
        twiceTimeToExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        composite.dispose();
    }

    public static final String TYPE_UPDATE = "TYPE_UPDATE";
    public static final String TYPE_ACTION = "TYPE_ACTION";
    public static final String DATA = "DATA";


    private ReportFragment reportFragment = null;
    private ProfileFragment profileFragment = null;

    private final CompositeDisposable composite = new CompositeDisposable();
    private void setSelectedMenu(int index) {
        binding.navView.getMenu().getItem(0).setChecked(index == 0);
        binding.navView.getMenu().getItem(1).setChecked(index == 1);
    }
    public ProfileFragment getProfileFragment() {
        if(profileFragment == null){
            profileFragment = new ProfileFragment();

            Disposable subscribe = profileFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(0);
            });
            composite.add(subscribe);
        }
        return profileFragment;
    }
    public ReportFragment getReportFragment() {
        if(reportFragment == null){
            reportFragment = new ReportFragment();

            Disposable subscribe = reportFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(1);
            });
            composite.add(subscribe);
        }
        return reportFragment;
    }
}