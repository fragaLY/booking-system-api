# booking-system
1. To restore data in Atlas cluster run the next command from 'scr' folder:
```
mongorestore --drop --uri mongodb+srv://developer:developerPassword@development-hmiup.mongodb.net/booking-system data
```

2. To build the application run the next commands: 
```
gradlew bootRun
``` 
Feel free to use the application on <b>localhost<b> with port <b>8080</b>.
