package com.projectPlant

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

class ToastRender {
    companion object {
        @SuppressLint("ResourceAsColor")
        fun toast(context: Context, message: String): Toast {
            var color = R.color.red
            if (message.startsWith("Success: ")) {
                color = R.color.green
            }
            val text = TextView(context)
            text.text = message
            text.setTextColor(color)

            val toast = Toast(context)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = text

            return toast
        }
    }

}