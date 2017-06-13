package com.bignerdranch.stockwatcher.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_single_fragment.*

abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            val fragment = createFragment()
            supportFragmentManager.beginTransaction()
                    .add(fragment_container.id, fragment)
                    .commit()
        }
    }
}
