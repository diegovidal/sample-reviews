package com.dvidal.samplereviews.features.list.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.dvidal.samplereviews.features.list.presentation.ReviewView

/**
 * @author diegovidal on 2020-02-20.
 */
class ReviewsDiffCallback(private val oldList: List<ReviewView>,
                          private val newList: List<ReviewView>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}