package com.noser.heuteabstimmung.persistence.db.impl

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.ports.persistence.LocationPersistencePort
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataIndexEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationEntity
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationRepository
import org.slf4j.LoggerFactory
import java.util.function.Consumer
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class LocationPersistencePortImpl(private val votationLocationRepository: VotationLocationRepository,
                                       private val dataSelectorRepository: DataSelectorRepository) : LocationPersistencePort {
    private val LOG = LoggerFactory.getLogger(LocationPersistencePortImpl::class.java)

    @Transactional
    override fun saveLocationAndSelector(location: VotationLocation, dataSelector: DataSelector) {
        LOG.info("Save location $location")

        if(dataSelectorExists(dataSelector)) {
            return
        }
        
        val dataSelectorEntity = saveDataSelector(dataSelector)

        val votationLocationEntity = VotationLocationEntity(0, location.shortName, dataSelectorEntity)
        votationLocationRepository.save(votationLocationEntity)

        LOG.info("Location saved $votationLocationEntity")
    }

    @Transactional
    override fun saveSelector(votationLocation: VotationLocation, dataSelector: DataSelector) {
        saveDataSelector(dataSelector)
    }

    private fun saveDataSelector(dataSelector: DataSelector): DataSelectorEntity {
        var dataSelectorEntity = DataSelectorEntity(
            0, dataSelector.name, dataSelector.extid, dataSelector.type, dataSelector.level,
            dataSelector.hash, dataSelector.source
        )

        dataSelectorEntity = dataSelectorRepository.save(dataSelectorEntity)

        val dataIndexEntities = mutableListOf<DataIndexEntity>()
        dataSelector.indexKeys.forEach(Consumer { key -> dataIndexEntities.add(createEntity(key, dataSelectorEntity)) })

        dataSelectorRepository.saveAll(dataIndexEntities)

        return dataSelectorEntity
    }

    private fun createEntity(key: String, dataSelectorEntity: DataSelectorEntity): DataIndexEntity {
        return DataIndexEntity(0, key, dataSelectorEntity)
    }

    private fun dataSelectorExists(dataSelector: DataSelector): Boolean {
        return dataSelectorRepository.findByExtidAndSource(dataSelector.extid, dataSelector.source) != null
    }
}
