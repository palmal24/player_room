#!/bin/bash

# CD to workdir and call scripts "asynchronously" to make sure initiator has started before calling responder

pwd=$(pwd)
src_target_len="/src/main/resources/scripts"
cmd_alpha="java -jar target/player-1.0-SNAPSHOT.jar \"initiator\" 9999"
cmd_beta="java -jar target/player-1.0-SNAPSHOT.jar \"responder\" 9999"

WORK_DIR=${pwd::-${#src_target_len}}

cd "$WORK_DIR" && mvn package

sleep 2 && eval "$cmd_beta" & eval "$cmd_alpha"
