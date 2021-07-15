package com.noser.heuteabstimmung
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import org.flywaydb.core.Flyway
import javax.inject.Inject

@MicronautTest(transactional = false)
abstract class DatabaseTest(body: StringSpec.() -> Unit = {}) : StringSpec(body) {

    @Inject
    lateinit var flyway: Flyway // We inject the Flyway instance only here

    override fun afterTest(testCase: TestCase, result: TestResult) {
//        flyway.clean()
//        flyway.migrate()
    }
}
