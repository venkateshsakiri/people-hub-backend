#!/bin/bash

cd /home/ec2-user/app

pkill -f 'java -jar' || true

JAR_FILE=$(ls *.jar | head -n 1)

nohup java -jar $JAR_FILE > app.log 2>&1 &