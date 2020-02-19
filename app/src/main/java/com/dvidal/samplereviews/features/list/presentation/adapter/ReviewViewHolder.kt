package com.dvidal.samplereviews.features.list.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dvidal.samplereviews.R
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

        val context = view.context
        this.reviewView = reviewView

        Glide.with(context)
            .load(reviewView.author.photo)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.author_photo_placeholder)
            .into(view.iv_author_photo)

        view.tv_author_name.text = reviewView.author.fullName
        view.rb_activity_trip_rate.rating = reviewView.rating.toFloat()
        view.tv_review_date.text = reviewView.created
        view.tv_review_message.text = reviewView.message
    }

    override fun onClick(p0: View?) {

        reviewView?.let { listener?.onClickReview(it) }
    }

    interface ReviewViewHolderListener {

        fun onClickReview(reviewView: ReviewView)
    }
}