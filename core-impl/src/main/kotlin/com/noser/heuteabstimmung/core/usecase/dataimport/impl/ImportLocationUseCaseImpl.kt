package com.noser.heuteabstimmung.core.usecase.dataimport.impl

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.ports.persistence.LocationPersistencePort
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ImportLocationUseCaseImpl(val locationPersistencePort: LocationPersistencePort) : ImportLocationUseCase {
    private val LOG = LoggerFactory.getLogger(ImportLocationUseCaseImpl::class.java)

    override fun importLocation(votationLocation: VotationLocation, sourceDetails: SourceDetails) {
        LOG.info("Import Location $votationLocation")
        locationPersistencePort.saveLocation(votationLocation)
    }
}