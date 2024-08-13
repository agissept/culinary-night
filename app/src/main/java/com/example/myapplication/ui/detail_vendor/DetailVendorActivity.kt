package com.example.myapplication.ui.detail_vendor

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDetailVendorBinding
import com.example.myapplication.ui.vendor.Vendor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DetailVendorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailVendorBinding

    private lateinit var vendor: Vendor
    private lateinit var reviewsRef: DatabaseReference
    private val reviews = mutableListOf<Review>()
    private val currentUser = Firebase.auth.currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityDetailVendorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        vendor = intent.getSerializableExtra("vendor") as Vendor
        reviewsRef = Firebase.database.getReference("vendors").child(vendor.id!!).child("reviews")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvVendorName.text = vendor.name
        binding.tvAddress.text = vendor.address
        binding.tvRating.text = vendor.rating.toString()

        Glide.with(this)
            .load(vendor.photo)
            .into(binding.ivThumb)


        binding.recyclerView.adapter = ReviewAdapter(reviews)

        reviewsRef.addChildEventListener(childEventListener)

        binding.btnSubmit.setOnClickListener {
            val rating = binding.ratingBar.rating
            val review = binding.etReview.text.toString()

            val addReview = Review(
                vendor.id!!,
                rating.toDouble(),
                review,
                currentUser.uid,
                currentUser.displayName!!,
                currentUser.photoUrl.toString()
            )
            reviewsRef.push().setValue(addReview)

            binding.materialCardView.visibility = android.view.View.GONE
            Toast.makeText(this, "Review added", Toast.LENGTH_SHORT).show()
        }
    }

    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            val review = dataSnapshot.getValue<Review>()

            if (review != null) {
                reviews.add(0, review)
                if (review.userId == currentUser.uid) {
                    binding.materialCardView.visibility = android.view.View.GONE
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
        }


        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    }

}