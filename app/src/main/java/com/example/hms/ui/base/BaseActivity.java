package com.example.hms.ui.base;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;

public abstract class BaseActivity<VB extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity implements BaseUI<VB, VM> {
    @LayoutRes
    private int layoutRes;
    protected VB binding;
    protected VM viewModel;

    private BaseActivity() {
    }

    protected BaseActivity(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutRes);
        binding.setLifecycleOwner(this);
        viewModel = setUpViewModel();
        fragmentManager = getSupportFragmentManager();
        createView();
    }

    private FragmentManager fragmentManager;

    protected  <F extends Fragment> void openFragment(@IdRes int containerId, F fragment, String tag) {
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle(tag);
        }
        fragmentManager.beginTransaction().replace(containerId, fragment, tag).addToBackStack(tag).commit();
    }
}
