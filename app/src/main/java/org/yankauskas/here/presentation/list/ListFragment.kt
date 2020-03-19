package org.yankauskas.here.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.yankauskas.here.R
import org.yankauskas.here.presentation.MainViewModel
import org.yankauskas.here.presentation.util.*


class ListFragment : Fragment(R.layout.fragment_list) {

    private val myViewModel: MainViewModel by sharedViewModel()
    private val adapter: SearchCategoryAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, OrientationHelper.VERTICAL))
        recyclerView.adapter = adapter

        adapter.onItemClickListener = {
            myViewModel.openMap()
            myViewModel.selectPlace(it)
        }

        observeResource(myViewModel.getPlaces, { loadingText.visible() }, {
            loadingText.gone()
            showError(context!!, it)
        }) {
            loadingText.gone()
            adapter.setData(it)
        }
    }
}