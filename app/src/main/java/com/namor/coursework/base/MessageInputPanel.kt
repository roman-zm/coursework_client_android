package com.namor.coursework.base

import android.view.View

interface MessageInputPanel {
    fun onSendClick()
    fun setOnSendClickListener(listener: OnSendListener)
    fun clear()

    interface OnSendListener {
        fun onSend(message: IMessage)
    }

    interface IMessage {
        val messageId: String
        val message: String
    }
}