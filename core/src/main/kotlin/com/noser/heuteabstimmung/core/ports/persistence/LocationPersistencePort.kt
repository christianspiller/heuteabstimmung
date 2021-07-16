package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.model.VotationLocation

interface LocationPersistencePort {
    fun saveLocationAndSelector(location: VotationLocation, dataSelector: DataSelector)
    fun saveSelector(votationLocation: VotationLocation, dataSelector: DataSelector)
}