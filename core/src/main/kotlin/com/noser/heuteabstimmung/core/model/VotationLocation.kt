package com.noser.heuteabstimmung.core.model

data class VotationLocation (
    val extid: String,
    val name: String,
    val shortName: String,
    val level: DivisionLevel
)
