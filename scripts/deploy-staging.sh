#!/bin/sh

echo "started develop deploy script"

ls -al

echo "starting build from script"
./gradlew build

echo "ls after script build"
ls -al

cd ./build/libs || exit

# define SSHPASS env variable that sshpass uses with -e argument
export SSHPASS=$DEPLOY_PASS

# copy generated jar file to server
rsync -chavzP --rsh="sshpass -e ssh -l $SSH_USERNAME" * $SSH_USERNAME@ituk.ee:/home/deploy/newHub/dev

# restart server
sshpass -e ssh $SSH_USERNAME@ituk.ee sudo systemctl restart newHubDev.service