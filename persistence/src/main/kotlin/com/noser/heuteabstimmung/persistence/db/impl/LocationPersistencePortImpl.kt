package com.noser.heuteabstimmung.persistence.db.impl

import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.ports.persistence.LocationPersistencePort
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationEntity
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationRepository
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class LocationPersistencePortImpl(private val votationLocationRepository: VotationLocationRepository) : LocationPersistencePort {
    private val LOG = LoggerFactory.getLogger(LocationPersistencePortImpl::class.java)

    @Transactional
    override fun saveLocation(location: VotationLocation) {
        LOG.info("Save location $location")

        val votationLocationEntity = VotationLocationEntity(0, location.extid, location.level.toString())
        votationLocationRepository.save(votationLocationEntity)

        LOG.info("Location saved $votationLocationEntity")
    }
}