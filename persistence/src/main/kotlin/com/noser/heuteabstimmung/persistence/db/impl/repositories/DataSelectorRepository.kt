package com.noser.heuteabstimmung.persistence.db.impl.repositories

import com.noser.heuteabstimmung.persistence.db.impl.entities.DataIndexEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationEntity
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface DataSelectorRepository : CrudRepository<DataSelectorEntity, Long> {
    @Executable
    fun saveAll(dataIndexEntities: MutableList<DataIndexEntity>): MutableList<DataIndexEntity>
}