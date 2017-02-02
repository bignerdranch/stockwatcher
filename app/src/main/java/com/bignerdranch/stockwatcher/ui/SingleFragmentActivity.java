package com.bignerdranch.stockwatcher.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.stockwatcher.R;
import com.bignerdranch.stockwatcher.databinding.ActivitySingleFragmentBinding;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySingleFragmentBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_single_fragment);
        if (savedInstanceState == null) {
            Fragment fragment = createFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(binding.fragmentContainer.getId(), fragment)
                    .commit();
        }
    }
}
