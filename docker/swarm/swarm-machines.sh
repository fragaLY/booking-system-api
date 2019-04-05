#!/bin/bash

managers=2
workers=2

# create manager machines
echo "Creating $managers manager machines ...";
for node in $(seq 1 $managers);
do
	echo "Creating manager$node machine ...";
	docker-machine create -d virtualbox manager$node;
done

# create worker machines
echo "Creating $workers worker machines ...";
for node in $(seq 1 $workers);
do
	echo "Creating worker$node machine ...";
	docker-machine create -d virtualbox worker$node;
done

docker-machine ls