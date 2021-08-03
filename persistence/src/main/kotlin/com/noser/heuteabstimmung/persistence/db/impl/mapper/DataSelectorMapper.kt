package com.noser.heuteabstimmung.persistence.db.impl.mapper

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataTypeEntity

import javax.inject.Singleton

@Singleton
class DataSelectorMapper {
    fun toEntity(dataSelector: DataSelector): DataSelectorEntity {

        val dataType: DataTypeEntity = when (dataSelector.dataType) {
            com.noser.heuteabstimmung.core.model.DataType.LOCATION_DATA -> DataTypeEntity.LOCATION_DATA
            com.noser.heuteabstimmung.core.model.DataType.VOTATION_DATA -> DataTypeEntity.VOTATION_DATA
            com.noser.heuteabstimmung.core.model.DataType.VECTOR_DATA -> DataTypeEntity.VECTOR_DATA
            com.noser.heuteabstimmung.core.model.DataType.VOTATION_DAY -> DataTypeEntity.VOTATION_DAY
        }
        
        return DataSelectorEntity(0, dataSelector.name, dataSelector.extId, dataType,
            dataSelector.hash, dataSelector.source)
    }

    fun fromEntity(entity: DataSelectorEntity): DataSelector {

        val dataType: com.noser.heuteabstimmung.core.model.DataType = when (entity.dataType) {
            DataTypeEntity.LOCATION_DATA -> com.noser.heuteabstimmung.core.model.DataType.LOCATION_DATA
            DataTypeEntity.VOTATION_DATA -> com.noser.heuteabstimmung.core.model.DataType.VOTATION_DATA
            DataTypeEntity.VECTOR_DATA -> com.noser.heuteabstimmung.core.model.DataType.VECTOR_DATA
            DataTypeEntity.VOTATION_DAY -> com.noser.heuteabstimmung.core.model.DataType.VOTATION_DAY
        }

        return DataSelector(entity.name, entity.extId, dataType, entity.hash, entity.source)
    }
}