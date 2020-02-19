package com.dvidal.samplereviews.features.list.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import kotlinx.android.synthetic.main.recycler_item_review.view.*

/**
 * @author diegovidal on 2020-02-18.
 */
class ReviewViewHolder(
    private val view: View,
    private val listener: ReviewViewHolderListener?
) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private var reviewView: ReviewView? = null

    init {
        view.setOnClickListener(this)
    }

    fun onBind(reviewView: ReviewView) {

        this.reviewView = reviewView

        view.tv_review_message.text = reviewView.message
    }

    override fun onClick(p0: View?) {

        reviewView?.let { listener?.onClickReview(it) }
    }

    interface ReviewViewHolderListener {

        fun onClickReview(reviewView: ReviewView)
    }
}