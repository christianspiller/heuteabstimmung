package com.noser.heuteabstimmung.core.usecase.dataimport.util

import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.TaskScheduler
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.function.Consumer
import javax.annotation.PreDestroy
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DataImportScheduler(@param:Named(TaskExecutors.SCHEDULED) private val taskScheduler: TaskScheduler) {
    private val LOG = LoggerFactory.getLogger(DataImportScheduler::class.java)

    var importTasks = mutableListOf<ImportTask>()

    fun startImportTask(importTask: ImportTask) {
        importTasks.add(importTask)
        taskScheduler.schedule(Duration.ofSeconds(5), importTask)
    }

    @PreDestroy
    fun stopAllTasks() {
        LOG.info("Stop all Import Tasks")
        importTasks.forEach(Consumer { task ->  task.stopTask()})
    }

}