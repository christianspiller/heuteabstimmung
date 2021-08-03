package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.model.VotationLocationDataSelector

interface LocationPersistencePort {
    fun saveLocationAndSelector(location: VotationLocation, votationLocationDataSelector: VotationLocationDataSelector)
    fun saveSelector(votationLocation: VotationLocation, votationLocationDataSelector: VotationLocationDataSelector)
}