package com.namor.coursework.domain

import com.namor.coursework.adapter.diffutil.DiffUtilable
import java.sql.Timestamp

data class Comment(
        override val id: Int,
        val user: User,
        val film: Int,
        val text: String,
        val date: Timestamp
): DiffUtilable {
    override fun <T : DiffUtilable?> areContentsTheSame(rItem: T) =
            rItem is Comment && hashForDiff() == rItem.hashForDiff()

    override fun hashForDiff() = hashCode()
}