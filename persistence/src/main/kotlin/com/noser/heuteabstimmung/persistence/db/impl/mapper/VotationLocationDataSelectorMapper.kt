package com.noser.heuteabstimmung.persistence.db.impl.mapper

import com.noser.heuteabstimmung.core.model.VotationLocationDataSelector
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DivisionLevelEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationDataSelectorEntity
import javax.inject.Singleton

@Singleton
class VotationLocationDataSelectorMapper(private val dataSelectorMapper: DataSelectorMapper) {

    fun toEntity(dataSelector: VotationLocationDataSelector, dataSelectorEntity: DataSelectorEntity)
    : VotationLocationDataSelectorEntity {

        val divisionLevel: DivisionLevelEntity = when (dataSelector.divisionLevel) {
            com.noser.heuteabstimmung.core.model.DivisionLevel.Nation -> DivisionLevelEntity.Nation
            com.noser.heuteabstimmung.core.model.DivisionLevel.Canton -> DivisionLevelEntity.Canton
            com.noser.heuteabstimmung.core.model.DivisionLevel.District -> DivisionLevelEntity.District
            com.noser.heuteabstimmung.core.model.DivisionLevel.Municipality -> DivisionLevelEntity.Municipality
        }

        return VotationLocationDataSelectorEntity(0,  dataSelector.bfsNumber, dataSelectorEntity, divisionLevel)
    }

    fun fromEntity(entity: VotationLocationDataSelectorEntity) : VotationLocationDataSelector {

        val divisionLevel: com.noser.heuteabstimmung.core.model.DivisionLevel = when (entity.divisionLevel) {
            DivisionLevelEntity.Nation -> com.noser.heuteabstimmung.core.model.DivisionLevel.Nation
            DivisionLevelEntity.Canton -> com.noser.heuteabstimmung.core.model.DivisionLevel.Canton
            DivisionLevelEntity.District -> com.noser.heuteabstimmung.core.model.DivisionLevel.District
            DivisionLevelEntity.Municipality -> com.noser.heuteabstimmung.core.model.DivisionLevel.Municipality
        }

        val dataSelector = dataSelectorMapper.fromEntity(entity.dataSelectorEntity)

        return VotationLocationDataSelector(entity.bfsNumber, dataSelector, divisionLevel)
    }
}