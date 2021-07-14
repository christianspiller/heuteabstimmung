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

### Trap: Lazy initialization
Because I had a @Scheduled method in my dummy class, the class was always instantiated and also @PostConstruct was 
called. When I removed the @Scheduled method suddenly my @PostConstruct method was not called anymore (where I started 
some tasks).

=> Set .eagerInitSingletons(true) in Application class !!only for tests!!. It is bad practise to start tasks with 
@PostConstruct, because at that time other tasks may still be running (e.g. database migration with flyway)

=> Start Tasks with server events: Implement ApplicationEventListener<ServerStartupEvent> on the @Singleton class.

## Integrations

### Flyway integration
New dependncy to micronaut-flyway:3.7.0 had a problem with loading flyway-core from Maven repository. After severall 
version changes it worked again. Don't know exactly how...

Another problem was

    ClassNotFoundException org.flywaydb.core.api.configuration.FluentConfiguration

=> an unused property <flyway.version>3.2.1</flyway.version> changed the dependencies. Maybe the property was used
in an external library.
Help: Check dependencies in IntelliJ and with mvn