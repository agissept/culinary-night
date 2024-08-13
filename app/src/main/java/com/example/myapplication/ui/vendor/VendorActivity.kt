package com.example.myapplication.ui.vendor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityVendorBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class VendorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorBinding
    private val vendors = mutableListOf<Vendor>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = VendorAdapter(vendors)

        val vendorsRef = Firebase.database.getReference("vendors")
        vendorsRef.get().addOnSuccessListener {
            it.children.forEach { data ->
                val id = data.key
                val name = data.child("name").value as String?
                val address = data.child("address").value as String?
                val phone = data.child("phone").value as String?
                val photo = data.child("photo").value as String?

                val reviews = data.child("reviews")
                var rating = 0.0
                reviews.children.forEach { review ->
                    rating += review.child("rating").value as Long
                }
                rating /= reviews.childrenCount

                val vendor = Vendor(
                    id = id,
                    name = name,
                    address = address,
                    rating = rating,
                    phone = phone,
                    photo = photo,
                )
                vendors.add(vendor)
                binding.recycleView.adapter?.notifyDataSetChanged()
            }
        }

    }
}