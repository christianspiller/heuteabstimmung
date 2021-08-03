package com.noser.heuteabstimmung.core.usecase.dataimport.impl

import com.noser.heuteabstimmung.core.model.*
import com.noser.heuteabstimmung.core.ports.persistence.LocationPersistencePort
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import com.noser.heuteabstimmung.core.usecase.dataimport.util.IndexKeysCreator
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ImportLocationUseCaseImpl(private val locationPersistencePort: LocationPersistencePort,
                                private val indexKeysCreator: IndexKeysCreator) : ImportLocationUseCase {
    private val log = LoggerFactory.getLogger(ImportLocationUseCaseImpl::class.java)

    override fun importLocation(votationLocation: VotationLocation, sourceDetails: SourceDetails) {
        log.info("Import Location $votationLocation")
        
        val dataSelector = createLocationDataSelector(votationLocation, sourceDetails)

        if(sourceDetails.storageAllowed) {
            locationPersistencePort.saveLocationAndSelector(votationLocation, dataSelector)
        } else {
            locationPersistencePort.saveSelector(votationLocation, dataSelector)
        }

    }

    private fun createLocationDataSelector(votationLocation: VotationLocation, sourceDetails: SourceDetails)
    : VotationLocationDataSelector {
        val dataSelector = DataSelector(votationLocation.name, votationLocation.extid, DataType.LOCATION_DATA,
            votationLocation.hashCode().toString(), sourceDetails.name)

        val votationLocationDataSelector = VotationLocationDataSelector(dataSelector, votationLocation.level)

        val keys = indexKeysCreator.createIndexKeys(votationLocation.name, votationLocation.shortName)
        dataSelector.indexKeys = keys

        return votationLocationDataSelector
    }
}