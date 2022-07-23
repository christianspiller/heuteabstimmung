package com.noser.heuteabstimmung.core.model

data class VotationLocationDataSelector(
    val bfsNumber: Int,
    val dataSelector: DataSelector,
    val divisionLevel: DivisionLevel
)
