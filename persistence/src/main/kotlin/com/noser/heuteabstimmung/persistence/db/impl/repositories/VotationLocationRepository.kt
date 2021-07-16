package com.noser.heuteabstimmung.persistence.db.impl.repositories

import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationEntity
import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface VotationLocationRepository : CrudRepository<VotationLocationEntity, Long> {
}