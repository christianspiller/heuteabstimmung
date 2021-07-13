package com.noser.heuteabstimmung.core.ports

import com.noser.heuteabstimmung.core.model.DataType
import com.noser.heuteabstimmung.core.model.SourceDetails

import javax.inject.Singleton

@Singleton
class DataGatewayImpl(val dataImportScheduler: DataImportScheduler) : DataGateway {
    override fun startImport(sourceDetails: SourceDetails, dataType: DataType) {
        dataImportScheduler.startImportTask()
    }
}