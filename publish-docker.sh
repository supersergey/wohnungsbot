#!/bin/bash
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 573481835800.dkr.ecr.us-east-1.amazonaws.com
docker tag wohnungsbot:latest 573481835800.dkr.ecr.us-east-1.amazonaws.com/wohnungsbot:latest
docker push 573481835800.dkr.ecr.us-east-1.amazonaws.com/wohnungsbot:latest