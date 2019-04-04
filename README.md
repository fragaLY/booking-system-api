# The booking-system template.

[![Build Status](https://travis-ci.com/fragaLY/booking-system.svg?branch=master)](https://travis-ci.com/fragaLY/booking-system) 
[![Coverage Status](https://coveralls.io/repos/github/fragaLY/booking-system/badge.svg?branch=master)](https://coveralls.io/github/fragaLY/booking-system?branch=master)

[Java Docs](https://fragaly.github.io/booking-system/)

* To restore data in [Atlas](https://www.mongodb.com/cloud/atlas) cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

* To run the application using [gradlew](https://docs.gradle.org/current/userguide/gradle_wrapper.html) 
```
gradlew bootRun
``` 
OR run the [Docker](https://www.docker.com/resources/what-container) image:
```
docker run -p 8080:8080 fragaly/booking-system -m 512M --memoryreservation 256M
```
Now you are able may follow the [link](http://localhost:8080) and check the REST API.

[![Docker_Hub](https://avatars0.githubusercontent.com/u/7739233?s=400&v=4
)](https://hub.docker.com/r/fragaly/booking-system)
