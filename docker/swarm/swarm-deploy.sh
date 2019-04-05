./swarm-cluster.sh

docker-machine ssh manager1
docker service create --name api --replicas 2 -p 8080:8080 fragaly/booking-system