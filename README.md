# metering-demo application

* Spring boot demo application with REST API for smart metering
* When started available at: `http://localhost:9090`
* Home page redirects to `swagger-ui` HTML page with endpoint documentation
* Requires Java 1.8+ to run
* Uses an in-memory `h2` database

## Starting the application from console

Linux / MacOS:
```
./gradlew clean bootRun
```

Windows:
```
gradlew clean bootRun
```

Open app in browser: http://localhost:9090

## Run tests from console
Linux / MacOS:
```
./gradlew clean test
```

Windows:
```
gradlew clean test
```

## Running from IDE
* The application uses `Lombok` so IDE needs to be set-up to support it

IntelliJ:
* Should suggest plugin and setup automatically (if not set-up already)
* https://projectlombok.org/setup/intellij

Eclipse:
* No plugin available, use instructions from `Lombok`
* https://projectlombok.org/setup/eclipse 

