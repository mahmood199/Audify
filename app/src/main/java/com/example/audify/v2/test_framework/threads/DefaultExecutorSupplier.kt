package com.example.audify.v2.test_framework.threads

import android.os.Process
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object DefaultExecutorSupplier {

    private val NUMBER_OF_CORES by lazy {
        Runtime.getRuntime().availableProcessors()
    }

    private val backgroundPriorityThreadFactory by lazy {
        PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND)
    }

    private val forBackgroundTasks by lazy {
        ThreadPoolExecutor(
            /* corePoolSize = */ NUMBER_OF_CORES * 2,
            /* maximumPoolSize = */ NUMBER_OF_CORES * 2,
            /* keepAliveTime = */ 60L,
            /* unit = */ TimeUnit.SECONDS,
            /* workQueue = */ LinkedBlockingQueue(),
            /* threadFactory = */ backgroundPriorityThreadFactory
        )
    }

    private val forLightWeightBackgroundTasks by lazy {
        ThreadPoolExecutor(
            NUMBER_OF_CORES * 2,
            NUMBER_OF_CORES * 2,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue(),
            backgroundPriorityThreadFactory
        )
    }

    private val mainThreadExecutor: Executor = MainThreadExecutor()

    @JvmStatic
    fun getInstance(): DefaultExecutorSupplier {
        return Holder.INSTANCE
    }

    fun forBackgroundTasks(): ThreadPoolExecutor {
        return forBackgroundTasks
    }

    fun forLightWeightBackgroundTasks(): ThreadPoolExecutor {
        return forLightWeightBackgroundTasks
    }

    fun forMainThreadTasks(): Executor {
        return mainThreadExecutor
    }

    private object Holder {
        val INSTANCE = DefaultExecutorSupplier
    }
}
