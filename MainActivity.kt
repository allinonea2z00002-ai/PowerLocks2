package com.example.powerlocks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
        }
        val input = EditText(this).apply {
            hint = "কত মিনিট?"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        val status = TextView(this)

        val startBtn = Button(this).apply {
            text = "লক শুরু করো"
            setOnClickListener {
                val mins = input.text.toString().toLongOrNull()
                if (mins == null || mins <= 0) {
                    Toast.makeText(context, "মিনিট লিখুন", Toast.LENGTH_SHORT).show()
                } else {
                    getSharedPreferences("lock", MODE_PRIVATE).edit()
                        .putLong("end", System.currentTimeMillis() + mins * 60_000L)
                        .apply()
                    status.text = "$mins মিনিটের জন্য লক চালু"
                }
            }
        }
        val accBtn = Button(this).apply {
            text = "Accessibility সেটিংস খুলুন"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }

        layout.addView(input)
        layout.addView(startBtn)
        layout.addView(accBtn)
        layout.addView(status)
        setContentView(layout)
    }
}
