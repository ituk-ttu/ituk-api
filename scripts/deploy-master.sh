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

# copy generated jar file to server
rsync -chavzP --rsh="ssh -l $SSH_USERNAME" * $SSH_USERNAME@ituk.ee:/home/deploy/newHub/master

# restart service with new jar
ssh $SSH_USERNAME@ituk.ee sudo systemctl restart newHubMaster.service
