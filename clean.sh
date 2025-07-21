#!/bin/bash

# 1. Stop and remove all containers
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)

# 2. Get the MySQL image ID
MYSQL_IMAGE_ID=$(docker images mysql:8.0 --format "{{.ID}}")

# 3. Remove all images except the MySQL image
docker images -q | grep -v "$MYSQL_IMAGE_ID" | xargs -r docker rmi -f

# 4. Clean up unused volumes
docker volume prune -f

# 5. Clean up all volumes
# docker volume ls -q | xargs -r docker volume rm -f

echo "Only the MySQL image remains; containers have been removed, and unused volumes have been cleaned."
