package com.example.powerlocks

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class PowerBlockService : AccessibilityService() {
    override fun onAccessibilityEvent(e: AccessibilityEvent?) {
        val prefs = getSharedPreferences("lock", MODE_PRIVATE)
        if (System.currentTimeMillis() > prefs.getLong("end", 0)) return
        if (e?.packageName?.toString()?.contains("systemui") != true) return
        val root = rootInActiveWindow ?: return
        if (root.findAccessibilityNodeInfosByText("Swipe to power off").isNotEmpty()) {
            performGlobalAction(GLOBAL_ACTION_BACK)
        }
    }

    override fun onInterrupt() {}
}
