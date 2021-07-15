package com.noser.heuteabstimmung.persistence.db.impl

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.ports.persistence.DataSelectorPersistencePort
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataIndexEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import java.util.function.Consumer
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class DataSelectorPersistencePort(
    private val dataSelectorRepository: DataSelectorRepository) : DataSelectorPersistencePort{

    @Transactional
    override fun saveDataSelector(dataSelector: DataSelector, indexKeys: Set<String>) {

        var dataSelectorEntity = DataSelectorEntity(
            0, dataSelector.name, dataSelector.extid, dataSelector.type, dataSelector.level,
            dataSelector.hash, dataSelector.source
        )

        dataSelectorEntity = dataSelectorRepository.save(dataSelectorEntity)

        val dataIndexEntities = mutableListOf<DataIndexEntity>()
        indexKeys.forEach(Consumer { key -> dataIndexEntities.add(createEntity(key, dataSelectorEntity)) })

        dataSelectorRepository.saveAll(dataIndexEntities)
    }

    private fun createEntity(key: String, dataSelectorEntity: DataSelectorEntity): DataIndexEntity {
        return DataIndexEntity(0, key, dataSelectorEntity)
    }
}