package com.noser.heuteabstimmung

import com.noser.heuteabstimmung.core.usecase.ManageImportsUseCase
import org.slf4j.LoggerFactory
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.inject.Singleton

@Singleton
class FirstComponent(val manageImportsUseCase: ManageImportsUseCase) {

    private val LOG = LoggerFactory.getLogger(FirstComponent::class.java)

    @PostConstruct
    internal fun setup() {
        LOG.info("Setup done!")
        manageImportsUseCase.importLocations()
    }

    @PreDestroy
    internal fun teardown() {
        LOG.info("Good bye!")
    }
}