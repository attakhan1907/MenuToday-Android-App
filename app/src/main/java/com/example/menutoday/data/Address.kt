package com.example.menutoday.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val tableNumber: String,
    val fullName: String,
    val notes: String,
    val phone: String
): Parcelable {

    constructor(): this("","","","")

}
