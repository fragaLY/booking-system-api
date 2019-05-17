# The booking-system template

| Links        | Description     |
| ------------- |:-------------:|
| [Docker Hub](https://hub.docker.com/r/fragaly/booking-system)   | The docker hub |

> To restore data in [Atlas](https://www.mongodb.com/cloud/atlas) cluster run the next command from <b>'scr'</b> folder:
```
mongorestore --drop --uri mongodb+srv://developer:Password@booking-system-0-hmiup.mongodb.net/booking-system data
```

# How to up the application

* > To up with [Docker](https://docs.docker.com/) run the next script:

|Script| Links        | Description     |
|----| ------------- |:-------------:|
|```docker run -p 8080:8080 fragaly/booking-system:lite -d```| [REST API](http://localhost:8080/api/) | Run the containers and up application |

# To test performance using [WRK](https://github.com/wg/wrk):
```wrk -t100 -c500 -d10m http://localhost:8080/api/orders```