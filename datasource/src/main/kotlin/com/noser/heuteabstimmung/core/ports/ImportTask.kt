package com.noser.heuteabstimmung.core.ports

import com.noser.heuteabstimmung.core.model.LocalizedString
import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.model.VotationLocationLevel
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import org.slf4j.LoggerFactory

class ImportTask (private val importLocationUseCase: ImportLocationUseCase) : Runnable {

    private val LOG = LoggerFactory.getLogger(ImportTask::class.java)

    private var isRunning: Boolean = true

    fun stopTask() {
        isRunning = false
    }

    override fun run() {
        LOG.info("Import startet")
        var counter = 0

        while (isRunning) {
            val votationLocation = VotationLocation(
                counter++,
                LocalizedString("${counter}_de", "${counter}_fr", "${counter}_it", "${counter}_en"),
                VotationLocationLevel.State,
                null
            )
            importLocationUseCase.importLocation(votationLocation)
            Thread.sleep(1000)
        }

        LOG.info("Import stopped")
    }

}
