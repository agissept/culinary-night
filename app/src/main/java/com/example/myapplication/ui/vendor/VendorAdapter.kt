package com.example.myapplication.ui.vendor

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.VendorItemBinding
import com.example.myapplication.ui.detail_vendor.DetailVendorActivity

class VendorAdapter(
    private val values: List<Vendor>,
) : RecyclerView.Adapter<VendorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            VendorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Glide.with(holder.itemView)
            .load(item.photo)
            .into(holder.binding.ivThumb)

        holder.binding.tvName.text = item.name
        holder.binding.tvAddress.text = item.address
        holder.binding.tvRating.text = item.rating.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailVendorActivity::class.java)
            intent.putExtra("vendor", item)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: VendorItemBinding) : RecyclerView.ViewHolder(binding.root)

}