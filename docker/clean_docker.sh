#!/bin/bash
docker stop $(docker ps -a -q)
docker rmi -f $(docker images | awk '$1~/fra/{print $3}')
docker images prune
docker container prune

././docker-compose stop monitoring api