package com.example.audify.v2.test_framework.threads

fun main() {
    DefaultExecutorSupplier.getInstance().forBackgroundTasks().execute {

    }

    DefaultExecutorSupplier.getInstance().forLightWeightBackgroundTasks().execute {

    }

    DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute {

    }

}