# Journal about 'heuteabstimmung'

## Initialize maven modules

### Micronaut cli
    mn create-app -b=maven -t=kotest -l=kotlin --jdk=16 com.noser.heuteabstimmung.core
creates a new folder named 'core'. Do this for all modules and create a parent pom.

=> Kotest did not run correctly. I decided to not use the CLI. Instead I used the parent pom from my prototype project
and I created a modul 'modul-template' with only the necessary dependencies. From this I created my modules. 

