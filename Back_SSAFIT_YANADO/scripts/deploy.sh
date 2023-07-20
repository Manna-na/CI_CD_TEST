#!/bin/bash
fuser -k 80/tcp
REPOSITORY=/home/ec2-user/BE
cd $REPOSITORY
nohup java -Dserver.port=80 -jar target/*.jar > $REPOSITORY/nohup.out 2>&1 &