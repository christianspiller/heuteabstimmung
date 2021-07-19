package com.noser.heuteabstimmung.persistence.db.impl.mapper

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataTypeEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DivisionLevelEntity
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

        val divisionLevel: DivisionLevelEntity = when (dataSelector.divisionLevel) {
            com.noser.heuteabstimmung.core.model.DivisionLevel.Nation -> DivisionLevelEntity.Nation
            com.noser.heuteabstimmung.core.model.DivisionLevel.Canton -> DivisionLevelEntity.Canton
            com.noser.heuteabstimmung.core.model.DivisionLevel.District -> DivisionLevelEntity.District
            com.noser.heuteabstimmung.core.model.DivisionLevel.Municipality -> DivisionLevelEntity.Municipality
        }

        return DataSelectorEntity(
            0, dataSelector.name, dataSelector.extId, dataType, divisionLevel,
            dataSelector.hash, dataSelector.source
        )
    }

    fun fromEntity(entity: DataSelectorEntity) : DataSelector {

        val dataType: com.noser.heuteabstimmung.core.model.DataType = when (entity.dataType) {
            DataTypeEntity.LOCATION_DATA -> com.noser.heuteabstimmung.core.model.DataType.LOCATION_DATA
            DataTypeEntity.VOTATION_DATA -> com.noser.heuteabstimmung.core.model.DataType.VOTATION_DATA
            DataTypeEntity.VECTOR_DATA -> com.noser.heuteabstimmung.core.model.DataType.VECTOR_DATA
            DataTypeEntity.VOTATION_DAY -> com.noser.heuteabstimmung.core.model.DataType.VOTATION_DAY
        }

        val divisionLevel: com.noser.heuteabstimmung.core.model.DivisionLevel = when (entity.divisionLevel) {
            DivisionLevelEntity.Nation -> com.noser.heuteabstimmung.core.model.DivisionLevel.Nation
            DivisionLevelEntity.Canton -> com.noser.heuteabstimmung.core.model.DivisionLevel.Canton
            DivisionLevelEntity.District -> com.noser.heuteabstimmung.core.model.DivisionLevel.District
            DivisionLevelEntity.Municipality -> com.noser.heuteabstimmung.core.model.DivisionLevel.Municipality
        }

        val dataSelector =
            DataSelector(entity.name, entity.extId, dataType, divisionLevel, entity.hash, entity.source)

        return dataSelector
    }
}