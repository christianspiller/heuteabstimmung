package com.noser.heuteabstimmung.persistence.db.impl.repositories

import com.noser.heuteabstimmung.persistence.db.impl.entities.DataIndexEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
abstract class DataSelectorRepository : CrudRepository<DataSelectorEntity, Long> {
    @Executable
    abstract fun saveAll(dataIndexEntities: MutableList<DataIndexEntity>): MutableList<DataIndexEntity>

    @Executable
    abstract fun findByExtIdAndSource(extId: String, source: String): DataSelectorEntity?

    @Query("SELECT selector FROM DataSelectorEntity selector " +
            "LEFT JOIN selector.indices di " +
            "WHERE LOWER(di.key) like LOWER(:query)")
    abstract fun listDataSelectors(query: String): List<DataSelectorEntity>
}