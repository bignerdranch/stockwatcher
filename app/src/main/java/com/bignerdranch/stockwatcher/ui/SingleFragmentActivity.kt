package com.bignerdranch.stockwatcher.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.bignerdranch.stockwatcher.R
import com.bignerdranch.stockwatcher.databinding.ActivitySingleFragmentBinding

abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivitySingleFragmentBinding>(this, R.layout.activity_single_fragment)
        savedInstanceState ?: let {
            val fragment = createFragment()
            supportFragmentManager.beginTransaction()
                    .add(binding.fragmentContainer.id, fragment)
                    .commit()
        }
    }
}
