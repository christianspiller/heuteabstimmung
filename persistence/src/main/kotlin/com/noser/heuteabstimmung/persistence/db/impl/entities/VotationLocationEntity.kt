package com.noser.heuteabstimmung.persistence.db.impl.entities

import javax.persistence.*

@Entity
@Table(name = "votation_location")
data class VotationLocationEntity(@Id
                                  @GeneratedValue(strategy = GenerationType.IDENTITY)
                                  var id: Long,
                                  var level: String)
