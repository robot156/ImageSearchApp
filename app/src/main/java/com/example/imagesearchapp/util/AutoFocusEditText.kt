package com.example.imagesearchapp.util

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo

class AutoFocusEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    private var beforeRepeatCount: Int = 0

    override fun onEditorAction(actionCode: Int) {
        if (actionCode == EditorInfo.IME_ACTION_DONE) {
            this.hideKeyboard(true)
        }
        super.onEditorAction(actionCode)
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action != KeyEvent.ACTION_UP) {
            beforeRepeatCount = event?.repeatCount ?: 0
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && beforeRepeatCount == 0 && event?.let { it.action == KeyEvent.ACTION_UP } != false) {
            this.hideKeyboard(true)
        }
        return super.onKeyPreIme(keyCode, event)
    }
}