package com.coroutines.sample.ui.example

import android.os.Bundle
import androidx.lifecycle.Observer
import com.coroutines.sample.R
import com.coroutines.sample.base.DataBindingFragment
import com.coroutines.sample.databinding.FragmentFetchDetailsBinding
import org.koin.android.ext.android.inject


class FetchDetailsFragment : DataBindingFragment<FragmentFetchDetailsBinding>() {

    private val vm by inject<FetchDetailsViewModel>()

    companion object {
        const val TAG  = "FetchDogsFragment"
        fun newInstance() =
            FetchDetailsFragment()
    }

    override fun layoutId() = R.layout.fragment_fetch_details

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vb.vm = vm
        initObservers()

//        Call this functions one at a time for clean Logs
//        vm.tryAsyncNetworkCall()
//        vm.tryExceptionHandler()
//        vm.trySupervisorScope()
        vm.tryAdvancedSupervisorScope()
    }

    private fun initObservers() {
        vm.updateEvent.observe(viewLifecycleOwner, Observer {
            // Update UI here
        })
    }

}
