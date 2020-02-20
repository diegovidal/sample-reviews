package com.dvidal.samplereviews.features.list.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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

    private var isDescendingOrderRating = true

    fun configureListener(listener: ReviewViewHolder.ReviewViewHolderListener) {
        this.listener = listener
    }

    fun updateIsDescendingOrderRating(isDesc: Boolean) {

        this.isDescendingOrderRating = isDesc
        updateDataSet(dataSet)
    }

    fun updateDataSet(list: List<ReviewView>) {

        val listOrd = sortByRating(list)

        val diffResultComplete = DiffUtil.calculateDiff(ReviewsDiffCallback(this.dataSet, listOrd))
        diffResultComplete.dispatchUpdatesTo(this)
        this.dataSet = listOrd
    }

    private fun sortByRating(list: List<ReviewView>): List<ReviewView> {

        return if (isDescendingOrderRating)
            list.sortedByDescending { it.rating }
        else
            list.sortedBy { it.rating }
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