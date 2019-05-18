package com.namor.coursework.domain

import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.text.format.DateUtils
import android.text.style.TtsSpan
import androidx.core.util.TimeUtils
import com.namor.coursework.adapter.diffutil.DiffUtilable
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalTime

data class Comment(
        override val id: Int = 0,
        val user: User,
        val film: Int,
        val text: String,
        val date: Timestamp? = null
): DiffUtilable {
    override fun <T : DiffUtilable?> areContentsTheSame(rItem: T) =
            rItem is Comment && hashForDiff() == rItem.hashForDiff()

    override fun hashForDiff() = hashCode()
}