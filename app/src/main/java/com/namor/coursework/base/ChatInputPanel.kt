package com.namor.coursework.base

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.namor.coursework.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class ChatInputPanel : FrameLayout, MessageInputPanel {
    private var isButtonEnabled = true
    private var layout = R.layout.view_chat_input_panel

    private lateinit var sendButton: View
    private lateinit var editText: EditText

    private var listener: MessageInputPanel.OnSendListener? = null
    private val typingSubject = PublishSubject.create<Boolean>()
    val typingObservable: Observable<Boolean>
        get() = typingSubject
                .debounce { b ->
                    if (!b) Observable.empty<Boolean>()
                            .delay(700, TimeUnit.MILLISECONDS)
                    else Observable.empty<Boolean>()
                }
                .distinctUntilChanged()


    constructor(context: Context) : super(context) { init() }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.ChatInputPanel,
                    0, 0)

            layout = a.getResourceId(R.styleable.ChatInputPanel_panelLayout,
                    R.layout.view_chat_input_panel)

            a.recycle()
        }
        init()
    }

    private fun init() {
        View.inflate(context, layout, this)
        sendButton = findViewById(R.id.sendButton)
        sendButton.setOnClickListener { onSendClick() }
        editText = findViewById(R.id.editText)

        initTextListener()
    }

    private fun initTextListener() {
        editText.addTextChangedListener(object : AbstractTextWatcher() {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                typingSubject.onNext(true)
            }

            override fun afterTextChanged(s: Editable?) {
                sendButton.isVisible = isButtonEnabled && !s?.trim().isNullOrBlank()
                typingSubject.onNext(false)
            }
        })
    }

    override fun onSendClick() {
        listener?.let {
            it.onSend(object: MessageInputPanel.IMessage {
                override val messageId: String = UUID.randomUUID().toString()
                override val message: String = editText.text.toString().trim()
            })
            clear()
        }
    }

    override fun setOnSendClickListener(listener: MessageInputPanel.OnSendListener) {
        this.listener = listener
    }

    override fun clear() {
        editText.text.clear()
    }

    fun setSendButtonEnabled(active: Boolean) {
        isButtonEnabled = active

        sendButton.isVisible = isButtonEnabled && !editText.text?.trim().isNullOrEmpty()
    }
}