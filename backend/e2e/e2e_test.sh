#!/usr/bin/env bash
set -e
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
OLD_PWD=$PWD
cd ${DIR}/..
PROCESS_ID=$(../gradlew run 2> ${DIR}/log.tmp > ${DIR}/log.tmp & echo $!)

function finish {
    echo "Killing process"
    echo "--------------SERVER LOG----------------"
    cat ${DIR}/log.tmp
    rm ${DIR}/log.tmp
    cd ${OLD_PWD}
    kill ${PROCESS_ID}
}
trap finish EXIT

wait_time=0
until curl -s localhost:8080; do
    sleep 5
    wait_time=$((wait_time + 5))
    echo "Waiting on server"
    if [ $wait_time -gt 60 ]; then
    echo "Server did not start!"
        exit 1 # Server did not start!
    fi
done

# Tests
# Home page exists
curl -s localhost:8080 | grep -q "<title>React App</title>"

# We can login
TOKEN_LINE=$(curl -POST -sd '{"username":"admin","password":"admin"}' localhost:8080/api/auth/ \
        |  grep -oe '"sessiontoken":"[0-9a-zA-Z\-]\+"')
TOKEN=${TOKEN_LINE#\"sessiontoken\":\"}
TOKEN=${TOKEN%\"}

# We have a active session
curl -s localhost:8080/api/auth/sessiontoken/${TOKEN} | grep -q "\"username\":\"admin\""
# We do not return the password hash
! curl -s localhost:8080/api/auth/sessiontoken/${TOKEN} | grep -q "\"password\":"

# We can not see password hashes
$(! curl -s "localhost:8080/api/users?sessiontoken=${TOKEN}" | grep -q "\"password\":")

# We can logout
curl -sX DELETE localhost:8080/api/auth/sessiontoken/${TOKEN} | grep -q "Deleted"