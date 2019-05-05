package com.namor.coursework.domain

import android.os.Parcelable
import com.namor.coursework.adapter.diffutil.DiffUtilable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
        override val id: Int,
        val name: String,
        val loginAdmin: String,
        val director: String,
        val year: Int,
        val actors: String,
        val price: Double,
        val duration: String,
        val genres: Set<Genre> = setOf()
): DiffUtilable, Parcelable {
    override fun <T : DiffUtilable?> areContentsTheSame(rItem: T) =
            hashForDiff() == rItem?.hashForDiff() ?: 0

    override fun hashForDiff() = hashCode()
}