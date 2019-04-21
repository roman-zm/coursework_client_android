package com.namor.coursework.adapter.diffutil

interface DiffUtilable {
    val id: Int
    fun <T : DiffUtilable?> areContentsTheSame(rItem: T): Boolean
    fun hashForDiff(): Int
}
