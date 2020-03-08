#!/bin/sh

echo "starting build from script"

# this is done here, not in the script phase because Travis CI by default disables the :jar task,
# which we need to deploy the application. Running the build task here does generate the jar file.
if ./gradlew build; then
  echo "Build succeeded, continuing with deploy."
else
  echo "Build failed, will not continue with deployment."
  exit 1
fi

# change to folder with generated jar file
cd ./build/libs || exit

# define SSHPASS env variable that sshpass uses with -e argument
export SSHPASS=$DEPLOY_PASS

# copy generated jar file to server
rsync -chavzP --rsh="sshpass -e ssh -l $SSH_USERNAME" * $SSH_USERNAME@ituk.ee:/home/deploy/newHub/dev

# restart service with new jar
sshpass -e ssh $SSH_USERNAME@ituk.ee sudo systemctl restart newHubDev.service