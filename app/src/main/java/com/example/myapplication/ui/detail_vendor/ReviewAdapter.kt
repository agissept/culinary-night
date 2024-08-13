package com.example.myapplication.ui.detail_vendor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ReviewItemBinding

class ReviewAdapter(
    private val values: List<Review>,
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Glide.with(holder.itemView)
            .load(item.userPhoto)
            .fallback(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_24)
            .into(holder.binding.ivThumb)

        holder.binding.tvName.text = item.userName
        holder.binding.tvReview.text = item.review
        holder.binding.tvRating.text = item.rating.toString()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ReviewItemBinding) : RecyclerView.ViewHolder(binding.root)

}