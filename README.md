# The booking-system template.

[![Build Status](https://travis-ci.com/fragaLY/booking-system.svg?branch=master)](https://travis-ci.com/fragaLY/booking-system) 
[![Coverage Status](https://coveralls.io/repos/github/fragaLY/booking-system/badge.svg?branch=master)](https://coveralls.io/github/fragaLY/booking-system?branch=master)
* To restore data in Atlas cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

* To build the application run the next command and follow the [link](localhost:8080 "Application Homepage"): 
```
gradlew bootRun
``` 

* To generate java documentation run the next command and find the files in <b>'build/docs'</b> directory:
```
gradlew javadoc
```
