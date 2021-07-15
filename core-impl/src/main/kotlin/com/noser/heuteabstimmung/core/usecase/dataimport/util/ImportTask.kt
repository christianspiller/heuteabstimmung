package com.noser.heuteabstimmung.core.usecase.dataimport.util


abstract class ImportTask : Runnable {

    internal var isRunning: Boolean = true

    fun stopTask() {
        isRunning = false
    }
}
