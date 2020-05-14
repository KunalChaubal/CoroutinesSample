package com.coroutines.sample.ui

import android.os.Bundle
import com.coroutines.sample.R
import com.coroutines.sample.base.DataBindingActivity
import com.coroutines.sample.databinding.ActivityMainBinding
import com.coroutines.sample.extensions.addFragment
import com.coroutines.sample.ui.example.FetchDetailsFragment
import org.koin.android.ext.android.inject

class MainActivity : DataBindingActivity<ActivityMainBinding>() {

    private val mainVM by inject<MainVM>()

    override fun layoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb.vm = mainVM

        addFragment(
            FetchDetailsFragment.newInstance(),
            vb.container.id,
            FetchDetailsFragment.TAG
        )
    }
}