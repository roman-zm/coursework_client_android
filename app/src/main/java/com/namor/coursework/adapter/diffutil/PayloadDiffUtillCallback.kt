package com.namor.coursework.adapter.diffutil

class PayloadDiffUtillCallback(
        oldList: List<PayloadDiffUtilable>,
        newList: List<PayloadDiffUtilable>)
    : GeoDiffUtilCallback(oldList, newList) {

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition] as PayloadDiffUtilable
        val newItem = newList[newItemPosition] as PayloadDiffUtilable
        return oldItem.getChangePayload(newItem)
    }
}