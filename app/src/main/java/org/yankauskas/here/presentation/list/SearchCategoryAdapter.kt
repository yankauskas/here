package org.yankauskas.here.presentation.list

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_text.view.*
import org.yankauskas.here.R

import org.yankauskas.here.presentation.entity.Place


/**
 * Created by Viktor Yankauskas (v.yankauskas@synergetica.net) on 18.03.2020.
 */
class SearchCategoryAdapter : RecyclerView.Adapter<SearchCategoryAdapter.CategoryViewHolder>() {
    var onItemClickListener: (item: Place) -> Unit = { }
    private val categories = ArrayList<Place>()

    fun setData(data: ArrayList<Place>) {
        categories.clear()
        categories.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false))

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) = holder.bind(categories[position])

    override fun getItemCount() = categories.size

    inner class CategoryViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Place) {
            containerView.text.text = item.title
            containerView.setOnClickListener { onItemClickListener(item) }
        }
    }
}

