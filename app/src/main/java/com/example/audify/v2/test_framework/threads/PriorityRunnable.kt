package com.example.audify.v2.test_framework.threads

class PriorityRunnable(val priorityLevel: Priority) : Runnable {

    override fun run() {
        // nothing to do here.
    }

    fun getPriority(): Priority {
        return priorityLevel
    }
}
