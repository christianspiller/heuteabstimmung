package com.noser.heuteabstimmung.core.usecase.dataimport.impl

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.ports.persistence.DataSelectorPersistencePort
import com.noser.heuteabstimmung.core.ports.persistence.LocationPersistencePort
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import com.noser.heuteabstimmung.core.usecase.dataimport.util.IndexKeysCreator
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ImportLocationUseCaseImpl(private val locationPersistencePort: LocationPersistencePort,
                                private val dataSelectorPersistencePort: DataSelectorPersistencePort,
                                private val indexKeysCreator: IndexKeysCreator) : ImportLocationUseCase {
    private val LOG = LoggerFactory.getLogger(ImportLocationUseCaseImpl::class.java)

    override fun importLocation(votationLocation: VotationLocation, sourceDetails: SourceDetails) {
        LOG.info("Import Location $votationLocation")
        
        if(sourceDetails.storageAllowed) {
            locationPersistencePort.saveLocation(votationLocation)
        }

        val dataSelector = DataSelector(votationLocation.name, votationLocation.extid, "Location",
            votationLocation.level.toString(), votationLocation.hashCode().toString(), sourceDetails.name)

        val keys = indexKeysCreator.createIndexKeys(votationLocation.name, votationLocation.shortName)

        dataSelectorPersistencePort.saveDataSelector(dataSelector, keys)
    }
}