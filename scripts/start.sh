#!/bin/bash

cd /home/ec2-user/webapps/school-api

JAR_FILE=$(ls *.jar | head -n 1)

nohup java -jar $JAR_FILE > logs/app.log 2>&1 &