package com.noser.heuteabstimmung.core.usecase.dataimport.util

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.ports.DataGateway
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import org.slf4j.LoggerFactory

class ImportLocationsTask (private val importLocationUseCase: ImportLocationUseCase,
                           private val dataGateway: DataGateway,
                           private val sourceDetails: SourceDetails) : ImportTask() {

    private val LOG = LoggerFactory.getLogger(ImportLocationsTask::class.java)

    override fun run() {
        LOG.info("Import startet")

        val allLocations = dataGateway.getAllLocations(sourceDetails).listIterator()

        while (isRunning && allLocations.hasNext()) {
            importLocationUseCase.importLocation(allLocations.next(), sourceDetails)
        }

        if(isRunning)
            LOG.info("Import finished")
        else
            LOG.info("Import aborted")
    }

}
