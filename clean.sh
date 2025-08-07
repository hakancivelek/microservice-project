#!/bin/bash
echo "Checking and stopping relevant containers..."

# 1. Find and stop/remove containers created from these images
for image in microservice-project_product-service microservice-project_api-gateway microservice-project_user-service microservice-project_order-service microservice-project_eureka-server; do
  containers=$(docker ps -aq --filter ancestor=$image:latest 2>/dev/null)
  if [ ! -z "$containers" ]; then
    echo "Containers found: $containers"
    docker stop $containers 2>/dev/null || true
    docker rm $containers 2>/dev/null || true
  fi
done

echo "Forcefully removing microservice images..."

# 2. Force remove the images using the -f flag
docker rmi -f microservice-project_product-service:latest
docker rmi -f microservice-project_api-gateway:latest
docker rmi -f microservice-project_user-service:latest
docker rmi -f microservice-project_order-service:latest
docker rmi -f microservice-project_eureka-server:latest

echo "Checking containers using named volumes..."

# 3. Find and stop containers using the volumes
for volume in microservice-project_es_data microservice-project_mysql_data; do
  containers=$(docker ps -aq --filter volume=$volume 2>/dev/null)
  if [ ! -z "$containers" ]; then
    echo "Containers using volume $volume: $containers"
    docker stop $containers 2>/dev/null || true
    docker rm $containers 2>/dev/null || true
  fi
done

echo "Gracefully stopping ELK containers..."

# 4. Stop and remove ELK containers (preserving images)
docker stop elasticsearch kibana logstash 2>/dev/null || true
docker rm elasticsearch kibana logstash 2>/dev/null || true

echo "Cleaning up volumes..."

# 5. Only remove the es_data volume (preserve mysql_data)
docker volume rm -f microservice-project_es_data 2>/dev/null || true

# 6. Preserve MySQL volume, just remove old containers using it
mysql_containers=$(docker ps -aq --filter volume=microservice-project_mysql_data 2>/dev/null)
if [ ! -z "$mysql_containers" ]; then
  echo "Cleaning containers using MySQL volume: $mysql_containers"
  docker stop $mysql_containers 2>/dev/null || true
  docker rm $mysql_containers 2>/dev/null || true
fi

echo "Cleanup completed!"
echo "Removed images:"
echo "- microservice-project_product-service:latest"
echo "- microservice-project_api-gateway:latest"
echo "- microservice-project_user-service:latest"
echo "- microservice-project_order-service:latest"
echo "- microservice-project_eureka-server:latest"
echo ""
echo "Cleaned/preserved resources:"
echo "- ELK Stack images preserved (elasticsearch, kibana, logstash)"
echo "- MySQL image preserved"
echo "- microservice-project_es_data volume removed"
echo "- microservice-project_mysql_data volume preserved"
echo ""
echo "You can now safely start the project using 'docker-compose up'"
