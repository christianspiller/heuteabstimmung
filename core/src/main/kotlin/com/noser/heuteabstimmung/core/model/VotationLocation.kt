package com.noser.heuteabstimmung.core.model

data class VotationLocation (
    val extId: String,
    val bfsNumber: Int,
    val name: String,
    val shortName: String,
    val level: DivisionLevel
)
