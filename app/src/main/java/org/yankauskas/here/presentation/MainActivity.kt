package org.yankauskas.here.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.yankauskas.here.R
import org.yankauskas.here.presentation.util.observeLiveData
import org.yankauskas.here.presentation.util.observeResource
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    private val myViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        fab.setOnClickListener { view ->
//            askForLocationWithPermissionCheck()


        }

        observeLiveData(myViewModel.requestLocationEvent) { askForLocationWithPermissionCheck() }
        observeResource(myViewModel.geocode, { titleText.text = "Loading" }, {}) {
            titleText.text = it
        }

    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun askForLocation() {
        myViewModel.requestLocation()
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        onRequestPermissionsResult(requestCode, grantResults)
    }
}