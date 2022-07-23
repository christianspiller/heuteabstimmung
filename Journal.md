# Journal about 'heuteabstimmung'

## Initialize maven modules

### Micronaut cli
    mn create-app -b=maven -t=kotest -l=kotlin --jdk=16 com.noser.heuteabstimmung.core
creates a new folder named 'core'. Do this for all modules and create a parent pom.

=> Kotest did not run correctly. I decided to not use the CLI. Instead I used the parent pom from my prototype project
and I created a modul 'modul-template' with only the necessary dependencies. From this I created my modules.

### Start the executable jar
There was an error when starting

    $> java -jar app/target/app-0.1.jar
    no main manifest attribute, in app/target/app-0.1.jar

=> Missing micronaut-maven-plugin in app/pom.xml:plugins. It doesn't need to be defined in parent pom.xml.

    Hint: $>  mvn dependency:resolve-plugins

### Start with maven
install mn:run -Dmicronaut.environments=local

Does not detect changes in other modules. You have to recompile the parent-pom (as you would do anyway with maven).

=> Start it with IntelliJ

### Trap: Lazy initialization
Because I had a @Scheduled method in my dummy class, the class was always instantiated and also @PostConstruct was 
called. When I removed the @Scheduled method suddenly my @PostConstruct method was not called anymore (where I started 
some tasks).

=> Set .eagerInitSingletons(true) in Application class !!only for tests!!. It is bad practise to start tasks with 
@PostConstruct, because at that time other tasks may still be running (e.g. database migration with flyway)

=> Start Tasks with server events: Implement ApplicationEventListener<ServerStartupEvent> on the @Singleton class.

## Flyway integration
New dependncy to micronaut-flyway:3.7.0 had a problem with loading flyway-core from Maven repository. After severall 
version changes it worked again. Don't know exactly how...

Another problem was

    ClassNotFoundException org.flywaydb.core.api.configuration.FluentConfiguration

=> an unused property <flyway.version>3.2.1</flyway.version> changed the dependencies. Maybe the property was used
in an external library.
Help: Check dependencies in IntelliJ and with mvn

## Okteto
Add the database password as secret in the Okteto Cloud. The secrets are for all namespaces the same! So I needed to
use environment variables like POSTGRESQL_PASSWORD_PROD.

Add the micronaut environment as a variable on the deployment in the Okteto Cloud: MICRONAUT_ENVIRONMENTS

These environment variables need to be declared in the app/kubernetes/deployment.yaml.template. The environment 
variables are substituted from Okteto while deployment. This is done with the command envsubst in /okteto-pipeline.yml

### Check the db
https://okteto.com/blog/connecting-to-database-with-port-forwarding/

    $> okteto namespace (runs a login via browser)
    $> okteto namespace heuteabstimmung-christianspiller (switch to namespace)
    $> kubectl get svc (get the available services)
    NAME                  TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
    heuteabstimmung       ClusterIP   10.155.248.237   <none>        8080/TCP   19h
    postgresql            ClusterIP   10.152.57.202    <none>        5432/TCP   29m
    $> kubectl port-forward service/postgresql 6432:5432 (ports <local-port>:<remote-port>

## Micronaut Data

### Correct dependencies and configuration
Error on Tests: No backing RepositoryOperations configured for repository. Check your configuration and try again

=> Correct dependencies (micronaut-jdbc-hikari, postgresql, micronaut-flyway, micronaut-data-hibernate-jpa)
=> Correct package scan (I had a typo in 'persistence'):

    jpa:
        default:
            packages-to-scan:
                - 'com.noser.heuteabstimmung.persistence.db'

### @Entity
Error: The @Transactional method was blocked and nothing happened. No error is logged.

=> I missed the @Entity at the data class.

### Sequence for primary keys
Error: Hibernate does not find secuence

=> @GeneratedValue(strategy = GenerationType.IDENTITY) on the id of the Entity

### Default Constructor
Error: Hibernate needs a default constructor on @Entity classes, but data classes in kotlin are usually designed with
the values constructor.

=> Kotlin has a plugin option: in the kotlin-maven-plugin of the persistence/pom.xml add

    <configuration>
      <compilerPlugins>
        <plugin>jpa</plugin>
      </compilerPlugins>
    </configuration>
    ...
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-maven-noarg</artifactId>
      <version>1.5.10</version>
    </dependency>

https://kotlinlang.org/docs/no-arg-plugin.html#jpa-support


### HashCode
Error: When updating a list of child entities (data_index of data_selector) somehow there was a val in the child entity
class set to null (should not happen with kotlin!) and there was a NullPointerException in the hashCode() method.

Overriding the hashCode() method did not help. The val was set to null, wenn saving the main entity (DataSelectorEntity.kt)
with a set of child entities (DataIndexEntity.kt).

=> I changed the Set<DataIndexEntity> of DataSelectorEntity to List<DataIndexEntity> so the hashCode() method is not 
used anymore. To be shure about not having duplicates i still use a set in the core models.

### Child entities
Nothing happens when

    dataSelectorEntity.indices = newKeys
    repository.save(dataSelectorEntity)

=> The mapping has to be correct
    
    @Entity
    @Table(name = "data_selector")
    data class DataSelectorEntity( ... ) {
        @OneToMany(mappedBy = "dataSelectorEntity", fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
        var indices: List<DataIndexEntity> = mutableListOf()
    }
    
    @Entity
    @Table(name = "data_index")
    data class DataIndexEntity(
        ...
        @ManyToOne
        val dataSelectorEntity: DataSelectorEntity
        ...)

    Foreign Key name on data_index: data_selector_entity_id INT

and the save calls have to be correct:
    
    val dataSelectorSaved = repository.save(dataSelector)

    val dataIndices = dataIndices
        .forEach( dataIndex -> dataIndex.dataSelector = dataSelectorSaved)
    repository.saveAll(dataIndices)

## Endpoints
### Info Endpoint
Micronaut displays the content of META-INF/build-info.properties file on the /health endpoint. This is created best with
the spring-boot-maven-plugin (goal build-info) in the app/pom.xml

    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <executions>
            <execution>
                <goals>
                    <goal>build-info</goal>
                </goals>
            </execution>
        </executions>
    </plugin>

## Micronaut Test

### Transactions
Micronaut tests usually use a transaction per test. This was generating a conflict with Flyway:

    override fun afterTest(testCase: TestCase, result: TestResult) {
        // These two methods will completely erase and recreate the schema of our test DB instance
        flyway.clean()
        flyway.migrate()
    }

If a test failed, the transaction was somehow blocking the call to flwyway.clean() and the test did not finish.

=> Set @MicronautTest(transactional = false) fixed the problem.

I created a abstract class, which cares about flyway and the transaction:

    @MicronautTest(transactional = false)
    abstract class DatabaseTest(body: StringSpec.() -> Unit = {}) : StringSpec(body) {

        @Inject
        lateinit var flyway: Flyway // We inject the Flyway instance only here
    
        override fun afterTest(testCase: TestCase, result: TestResult) {
            flyway.clean()
            flyway.migrate()
        }
    }

### shouldBe

My test

    repository.findAll() shouldBe listOf(DataSelectorEntity(...))

did pass even when the returned list was empty! Always use

    repository.findAll() shouldHaveSingleElement DataSelectorEntity(...)


## IntelliJ

### Kotlin Integration

The integration of the Kotlin annotation processor (kapt) is very bad. The build inside IntelliJ does not update
anything in relation with annotations. A mvn install from the run configuration has to be done, when you:
- add/change/remove annotations. e.g. @Singleton, @Prototype, @Query
- change attributes of annotations. e.g. @Query("From x") -> @Query("From x where y")

=> Known Issue: https://youtrack.jetbrains.com/issue/KT-15040

