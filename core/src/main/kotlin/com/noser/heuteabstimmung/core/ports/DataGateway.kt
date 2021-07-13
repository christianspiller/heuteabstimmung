package com.noser.heuteabstimmung.core.ports

import com.noser.heuteabstimmung.core.model.DataType
import com.noser.heuteabstimmung.core.model.SourceDetails

interface DataGateway {
    fun startImport(sourceDetails: SourceDetails, dataType: DataType)
}