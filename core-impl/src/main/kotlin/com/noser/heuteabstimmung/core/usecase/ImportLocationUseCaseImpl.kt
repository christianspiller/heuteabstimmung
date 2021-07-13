package com.noser.heuteabstimmung.core.usecase

import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.ports.persistence.LocationPersistencePort
import javax.inject.Singleton

@Singleton
class ImportLocationUseCaseImpl(val locationPersistencePort: LocationPersistencePort) : ImportLocationUseCase {
    override fun importLocation(votationLocation: VotationLocation) {
        locationPersistencePort.saveLocation(votationLocation)
    }
}