package com.dvidal.samplereviews.features.list.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-18.
 */
class ReviewsAdapter @Inject constructor(): RecyclerView.Adapter<ReviewViewHolder>() {

    private var dataSet = emptyList<ReviewView>()
    private var listener: ReviewViewHolder.ReviewViewHolderListener? = null

    fun configureListener(listener: ReviewViewHolder.ReviewViewHolderListener) {
        this.listener = listener
    }

    fun updateDataSet(list: List<ReviewView>) {

        this.dataSet = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_review, parent, false)

        return ReviewViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

        val reviewView = dataSet[position]
        holder.onBind(reviewView)
    }
}