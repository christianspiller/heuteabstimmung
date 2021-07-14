package com.noser.heuteabstimmung.core.usecase

import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.ports.persistence.LocationPersistencePort
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ImportLocationUseCaseImpl(val locationPersistencePort: LocationPersistencePort) : ImportLocationUseCase {
    private val LOG = LoggerFactory.getLogger(ImportLocationUseCaseImpl::class.java)

    override fun importLocation(votationLocation: VotationLocation) {
        LOG.info("Import Location $votationLocation")
        locationPersistencePort.saveLocation(votationLocation)
    }
}