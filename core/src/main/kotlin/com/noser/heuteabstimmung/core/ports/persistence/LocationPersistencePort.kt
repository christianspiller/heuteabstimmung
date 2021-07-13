package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.VotationLocation

interface LocationPersistencePort {
    fun saveLocation(location: VotationLocation)
}