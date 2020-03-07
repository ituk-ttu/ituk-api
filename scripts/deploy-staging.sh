#!/bin/sh

echo "started develop deploy script"

ls -a

# define SSHPASS env variable that sshpass uses with -e argument
export SSHPASS=$DEPLOY_PASS

# copy generated jar file to server
rsync -chavzP --rsh="sshpass -e ssh -l $SSH_USERNAME" build/lib/* $SSH_USERNAME@ituk.ee:/home/deploy/newHub/dev

# restart server
sshpass -e ssh $SSH_USERNAME@ituk.ee systemctl restart newHubDev.service