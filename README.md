# booking-system

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
* To generate test reports:
```
gradlew jacocoTestReport
```
