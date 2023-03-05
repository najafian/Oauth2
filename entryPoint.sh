. .env
mvn clean package

docker-compose -f docker-compose.yml down
docker rmi oauth_oauth
docker-compose -f docker-compose.yml up --build -d


#mvn clean
