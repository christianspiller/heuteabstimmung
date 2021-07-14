package com.noser.heuteabstimmung.core.model

data class VotationLocation (
    val id: Int,
    val name: LocalizedString,
    val level: VotationLocationLevel,
    val parent: VotationLocation?
)
