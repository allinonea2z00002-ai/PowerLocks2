package com.example.powerlocks

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class PowerBlockService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        try {
            startForegroundService(Intent(this, KeepAliveService::class.java))
        } catch (_: Exception) {
        }
    }

    override fun onAccessibilityEvent(e: AccessibilityEvent?) {
        try {
            val end = getSharedPreferences("lock", MODE_PRIVATE).getLong("end", 0)
            if (System.currentTimeMillis() > end) return
            if (e?.packageName?.toString()?.contains("systemui") != true) return

            if (hasPowerMenu(e.source) || windows.any { hasPowerMenu(it.root) }) {
                performGlobalAction(GLOBAL_ACTION_BACK)
                performGlobalAction(GLOBAL_ACTION_HOME)
            }
        } catch (_: Exception) {
        }
    }

    private fun hasPowerMenu(n: AccessibilityNodeInfo?): Boolean {
        if (n == null) return false
        return n.findAccessibilityNodeInfosByText("Swipe to power off").isNotEmpty() ||
                n.findAccessibilityNodeInfosByText("Swipe to restart").isNotEmpty()
    }

    override fun onInterrupt() {}
}
