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
export DS_URL=$DS_URL
export DS_USER=$DS_USER
export DS_PASSWORD=$DS_PASSWORD

# copy generated jar file to server
rsync -chavzP --rsh="sshpass -e ssh -l $SSH_USERNAME" * $SSH_USERNAME@ituk.ee:/home/deploy/newHub/dev

# restart server
sshpass -e ssh $SSH_USERNAME@ituk.ee export DS_URL=$DS_URL
sshpass -e ssh $SSH_USERNAME@ituk.ee export DS_USER=$DS_USER
sshpass -e ssh $SSH_USERNAME@ituk.ee export DS_PASSWORD=$DS_PASSWORD
sshpass -e ssh $SSH_USERNAME@ituk.ee sudo systemctl restart newHubDev.service
