#!/usr/bin/env bash
set -e

IMAGE_NAME="bookandmanage/studio-server:latest"

echo "git checkout and remote update"
git checkout integration

echo "git reset --hard origin/integration"
git reset --hard origin/integration

echo "Building Docker image..."
docker build -t "$IMAGE_NAME" .

echo "Pushing Docker image..."
docker push "$IMAGE_NAME"

echo "Done!"
