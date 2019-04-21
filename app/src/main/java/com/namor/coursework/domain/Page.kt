package com.namor.coursework.domain

data class Page<out T>(
        val content: List<T>,
        val pageSize: Int,
        val page: Int
): List<T> by content
