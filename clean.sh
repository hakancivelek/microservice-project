docker ps -q --filter "name=microservice-project" | xargs -r docker stop
docker ps -aq --filter "name=microservice-project" | xargs -r docker rm

for img in microservice-project-api-gateway microservice-project-product-service microservice-project-user-service microservice-project-order-service microservice-project-eureka-server; do
  docker rmi "$img":latest 2>/dev/null || true
done

docker volume ls -q --filter name=microservice-project_ | xargs -r docker volume rm

echo "✅ Cleanup completed!"
