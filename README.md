# The booking-system template.

[![Build Status](https://travis-ci.com/fragaLY/booking-system.svg?branch=master)](https://travis-ci.com/fragaLY/booking-system) 
[![Coverage Status](https://coveralls.io/repos/github/fragaLY/booking-system/badge.svg?branch=master)](https://coveralls.io/github/fragaLY/booking-system?branch=master)

* Javadocs are available [here](https://fragaly.github.io/booking-system/).

* To restore data in Atlas cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

* To run the application use the next command and follow the [link](localhost:8080 "Application Homepage"): 
```
gradlew bootRun
``` 
or run the latest docker image:
```
docker run --name application -p 80:8080 -d fragaly/booking-system
```
