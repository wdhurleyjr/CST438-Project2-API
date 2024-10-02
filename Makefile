# Makefile for building and deploying the Docker image

# Variables
IMAGE_NAME = project2
HEROKU_APP_NAME = cst438-project2
HEROKU_REGISTRY = registry.heroku.com/$(HEROKU_APP_NAME)/web

# Default target
all: build tag push release open

# Build the Docker image (force no cache to ensure fresh image)
build:
	echo "Building Docker image for $(IMAGE_NAME)..."
	docker build --no-cache --platform linux/amd64 -t $(IMAGE_NAME) .

# Tag the Docker image
tag:
	echo "Tagging Docker image..."
	docker tag $(IMAGE_NAME) $(HEROKU_REGISTRY)

# Push the Docker image to Heroku
push:
	echo "Pushing Docker image to Heroku..."
	docker push $(HEROKU_REGISTRY)

# Release the Docker image on Heroku and restart the app
release:
	echo "Releasing Docker image on Heroku..."
	heroku container:release web --app $(HEROKU_APP_NAME)
	echo "Restarting the Heroku app..."
	heroku ps:restart --app $(HEROKU_APP_NAME)
	echo "Checking Heroku logs..."
	heroku logs --tail --app $(HEROKU_APP_NAME)

# Open the Heroku app
open:
	echo "Opening Heroku app..."
	heroku open --app $(HEROKU_APP_NAME)

# Clean up (optional)
clean:
	echo "Cleaning up..."
	docker rmi $(HEROKU_REGISTRY) || true

# View logs from local Docker container (if running locally)
logs:
	@echo "Fetching logs from Docker container..."
	@docker logs -f $(shell docker ps -q --filter ancestor=$(IMAGE_NAME))


