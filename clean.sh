docker ps -q | xargs -r docker stop
docker ps -aq | xargs -r docker rm

for img in microservice-project_user-service \
           microservice-project_order-service \
           microservice-project_product-service \
           microservice-project_api-gateway \
           microservice-project_eureka-server; do
  docker rmi "$img" 2>/dev/null || true
done

docker volume ls -q --filter name=microservice-project_ | xargs -r docker volume rm

echo "✅ Cleanup completed!"
