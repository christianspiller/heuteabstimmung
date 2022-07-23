package com.noser.heuteabstimmung.persistence.db.impl.entities

import javax.persistence.*

@Entity
@Table(name = "votation_location_data_selector")
data class VotationLocationDataSelectorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var bfsNumber: Int,
    @OneToOne
    val dataSelectorEntity: DataSelectorEntity,
    val divisionLevel: DivisionLevelEntity
)
