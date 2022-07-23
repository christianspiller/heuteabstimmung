package com.noser.heuteabstimmung.core.usecase.dataimport.impl

import com.noser.heuteabstimmung.core.model.DataType
import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.ports.DataGateway
import com.noser.heuteabstimmung.core.ports.persistence.DataSourceDetailsPersistencePort
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import com.noser.heuteabstimmung.core.usecase.ManageImportsUseCase
import com.noser.heuteabstimmung.core.usecase.dataimport.util.DataImportScheduler
import com.noser.heuteabstimmung.core.usecase.dataimport.util.ImportLocationsTask
import org.slf4j.LoggerFactory
import java.util.function.Consumer
import javax.inject.Singleton

@Singleton
class ManageImportsUseCaseImpl (private val dataImportScheduler: DataImportScheduler,
                                private val dataSourceDetailsPersistencePort: DataSourceDetailsPersistencePort,
                                private val dataGateway: DataGateway,
                                private val importLocationUseCase: ImportLocationUseCase):
    ManageImportsUseCase {

    private val log = LoggerFactory.getLogger(ManageImportsUseCaseImpl::class.java)

    override fun importLocations() {
        log.info("Use Case: import Locations")
        dataSourceDetailsPersistencePort.getSourceDetails(DataType.LOCATION_DATA)
            .forEach(Consumer { sourceDetails -> importLocations(sourceDetails) })
    }

    private fun importLocations(sourceDetails: SourceDetails) {
        val importTask = ImportLocationsTask(importLocationUseCase, dataGateway, sourceDetails)
        dataImportScheduler.startImportTask(importTask)
    }

}