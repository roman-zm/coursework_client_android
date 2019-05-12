package com.namor.coursework.abstract

import android.text.Editable
import android.text.TextWatcher

abstract class AbstractTextWatcher: TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}

class TextChangedListener(
        val listener: (s: Editable?) -> Unit
): AbstractTextWatcher() {
    override fun afterTextChanged(s: Editable?) = listener(s)
}