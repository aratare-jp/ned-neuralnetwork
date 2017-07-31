#!/bin/bash

# Created by rex

# Start server function
function start_server () {
    nohup java -jar build/libs/neural-network-0.1.jar server configuration.yaml >server.log 2>&1 &
}

# Get the pid of the running process of the neural network
pid=$(jps -mlV | grep "neural-network" | cut -d" " -f1)

# Only kill the server without restarting it
if [ $1 = "kill" ]
then
    # Kill the running server
    kill $pid
    # Restart the server if successfully killed
    if [ $? -gt 0 ]
    then
        echo "Cannot kill the running process for some reasons"
    fi
else
    # Kill the current running server before restarting it
    if [ -z $pid ]
    then
        echo "No process found. Starting up server..."
        # Restart the server program
        start_server
    else
        # Kill the running server
        kill $pid
        # Restart the server if successfully killed
        if [ $? -gt 0 ]
        then
            echo "Cannot kill the running process for some reasons"
        else
            # Restart the server program
            start_server
        fi
    fi
fi
