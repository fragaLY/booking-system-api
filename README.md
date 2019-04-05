# The booking-system template

[![Build Status](https://travis-ci.com/fragaLY/booking-system.svg?branch=master)](https://travis-ci.com/fragaLY/booking-system) 
[![Coverage Status](https://coveralls.io/repos/github/fragaLY/booking-system/badge.svg?branch=master)](https://coveralls.io/github/fragaLY/booking-system?branch=master)

| Links        | Description     |
| ------------- |:-------------:|
| [Java Docs](https://fragaly.github.io/booking-system/)     | The latest javadocs |
| [Docker Hub](https://hub.docker.com/r/fragaly/booking-system)   | The docker hub |

> To restore data in [Atlas](https://www.mongodb.com/cloud/atlas) cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

# How to up the application:
* > To up the application using [gradlew](https://docs.gradle.org/current/userguide/gradle_wrapper.html) run the script and follow the [link](http://localhost:8080)
```
gradlew bootRun
``` 

* > To up with [Docker Compose](https://docs.docker.com/compose/) run the script and follow the [link](http://localhost:8080)

-To check statistics of containers please follow the [link](http://localhost:8081/containers/)
```
gradle docker
docker-compose up -d
```
