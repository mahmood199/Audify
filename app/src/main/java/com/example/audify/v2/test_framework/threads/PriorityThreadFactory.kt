package com.example.audify.v2.test_framework.threads

import android.os.Process
import java.util.concurrent.ThreadFactory

class PriorityThreadFactory(private val threadPriority: Int) : ThreadFactory {

    override fun newThread(runnable: Runnable): Thread {
        val wrapperRunnable = Runnable {
            try {
                Process.setThreadPriority(threadPriority)
            } catch (t: Throwable) {
                // Handle the exception if needed
            }
            runnable.run()
        }
        return Thread(wrapperRunnable)
    }
}
