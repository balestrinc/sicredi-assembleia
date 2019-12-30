#!/bin/bash
set -x
set -e
cmd="$@"

# creates needed databases for tests
createdb -h $1 -p $2 -U $3 assembleia_db_test

if [ "$SYSTEM_ENV" == "dev" ]
then
    # creates needed databases for development
    createdb -h $1 -p $2 -U $3 assembleia_db
fi
