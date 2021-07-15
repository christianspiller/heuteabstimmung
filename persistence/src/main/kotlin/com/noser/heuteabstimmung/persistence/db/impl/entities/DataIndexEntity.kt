package com.noser.heuteabstimmung.persistence.db.impl.entities

import javax.persistence.*

@Entity
@Table(name = "data_index")
data class DataIndexEntity(@Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            val id: Long,
                           val key: String,
                           @ManyToOne
                           val dataSelectorEntity: DataSelectorEntity
                        )