package com.noser.heuteabstimmung.persistence.db.impl.entities

import javax.persistence.*

@Entity
@Table(name = "data_index")
data class DataIndexEntity(@Id
                            @GeneratedValue(strategy = GenerationType.IDENTITY)
                            var id: Long,
                           var key: String,
                           var fk_data_selector: Long
                        )
