package com.example.myapplication.ui.vendor

import java.io.Serializable

data class Vendor(
    val id: String? = null,
    val name: String? = null,
    val address: String? = null,
    val rating: Double? = null,
    val phone: String? = null,
    val photo: String? = null,
): Serializable