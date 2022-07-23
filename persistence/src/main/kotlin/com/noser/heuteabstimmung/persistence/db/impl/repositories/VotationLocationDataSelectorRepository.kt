package com.noser.heuteabstimmung.persistence.db.impl.repositories

import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationDataSelectorEntity
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
abstract class VotationLocationDataSelectorRepository : CrudRepository<VotationLocationDataSelectorEntity, Long> {

    @Query("SELECT vl_selector FROM VotationLocationDataSelectorEntity vl_selector")
    abstract fun findAllVotationLocationDataSelectors(): List<VotationLocationDataSelectorEntity>

    @Executable
    abstract fun findByDataSelectorEntity(dataSelectorEntity: DataSelectorEntity): List<VotationLocationDataSelectorEntity>
}