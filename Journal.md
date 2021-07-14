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

Error: No backing RepositoryOperations configured for repository. Check your configuration and try again

=> Correct package scan:

    jpa:
        default:
            packages-to-scan:
                - 'com.noser.heuteabstimmung.persistence.db'

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