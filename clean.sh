# Tüm containerları durdur ve sil
docker ps -q | xargs -r docker stop
docker ps -aq | xargs -r docker rm

# microservice-project ile başlayan tüm image'ları sil
docker images --format "{{.Repository}}:{{.Tag}}" | grep "^microservice-project" | xargs -r docker rmi

# microservice-project ile başlayan tüm volume'leri sil
docker volume ls -q --filter "name=microservice-project" | xargs -r docker volume rm

echo "✅ Cleanup completed!"
