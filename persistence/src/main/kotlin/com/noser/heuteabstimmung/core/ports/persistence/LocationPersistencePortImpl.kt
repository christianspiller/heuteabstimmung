package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.VotationLocation
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class LocationPersistencePortImpl : LocationPersistencePort {
    private val LOG = LoggerFactory.getLogger(LocationPersistencePortImpl::class.java)

    override fun saveLocation(location: VotationLocation) {
        LOG.info("Location saved $location")
    }
}