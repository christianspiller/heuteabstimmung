package com.noser.heuteabstimmung.core.usecase

import com.noser.heuteabstimmung.core.model.DataType
import com.noser.heuteabstimmung.core.ports.DataGateway
import com.noser.heuteabstimmung.core.ports.persistence.DataSourceDetailsPersistencePort
import java.util.function.Consumer
import javax.inject.Singleton

@Singleton
class ManageImportsUseCaseImpl (private val dataGateway: DataGateway, private val dataSourceDetailsPersistencePort: DataSourceDetailsPersistencePort): ManageImportsUseCase {
    override fun importLocations() {
        dataSourceDetailsPersistencePort.getSourceDetails()
            .forEach(Consumer { t -> dataGateway.startImport(t, DataType.LOCATION_DATA) })
    }
}