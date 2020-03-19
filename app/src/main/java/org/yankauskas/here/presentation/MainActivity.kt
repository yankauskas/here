package org.yankauskas.here.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.yankauskas.here.R
import org.yankauskas.here.presentation.entity.Category
import org.yankauskas.here.presentation.util.observeLiveData
import org.yankauskas.here.presentation.util.observeResource
import org.yankauskas.here.presentation.util.showError
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
        fab.setOnClickListener { showCategoriesDialog() }

        fab.hide()

        observeLiveData(myViewModel.requestLocationEvent) { askForLocationWithPermissionCheck() }
        observeResource(
            myViewModel.geocode,
            { titleText.text = getText(R.string.loading) },
            {
                titleText.text = getText(R.string.error)
                showError(this, it)
            }) {
            titleText.text = it
        }
        observeResource(myViewModel.getCategories, error = { showError(this, it) }) { fab.show() }
        observeLiveData(myViewModel.openMap) { viewPager.currentItem = 0 }
    }

    private fun showCategoriesDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.select_categories)

        builder.setMultiChoiceItems(
            myViewModel.categories.map { it.title }.toTypedArray(),
            myViewModel.categories.map { myViewModel.selectedCategoriesIds.contains(it.id) }
                .toBooleanArray()
        ) { _, which, isChecked ->
            myViewModel.categories[which].id.let {
                with(myViewModel.selectedCategoriesIds) {
                    if (isChecked) add(it) else remove(it)
                }
            }
        }

        builder.setPositiveButton(R.string.load) { _, _ -> }
        builder.setOnDismissListener { myViewModel.loadPlaces() }
        builder.create().show()
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