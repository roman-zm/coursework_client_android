package com.namor.coursework.adapter.diffutil

interface PayloadDiffUtilable : DiffUtilable {
    fun  getChangePayload(newItem: PayloadDiffUtilable): Any?
}