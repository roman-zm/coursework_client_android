package com.namor.coursework.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
        val id: Int,
        val name: String,
        val description: String?
): Parcelable