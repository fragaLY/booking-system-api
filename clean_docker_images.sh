#!/bin/bash
docker rmi $(docker images | awk '$1~/fra/{print $3}')

