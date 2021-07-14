package com.noser.heuteabstimmung

import com.noser.heuteabstimmung.core.usecase.ManageImportsUseCase
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import org.slf4j.LoggerFactory
import javax.annotation.PreDestroy
import javax.inject.Singleton

@Singleton
class FirstComponent(val manageImportsUseCase: ManageImportsUseCase): ApplicationEventListener<ServerStartupEvent> {

    private val LOG = LoggerFactory.getLogger(FirstComponent::class.java)

    @PreDestroy
    internal fun teardown() {
        LOG.info("Good bye!")
    }

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        LOG.info("Server started!")
        manageImportsUseCase.importLocations()
    }
}
