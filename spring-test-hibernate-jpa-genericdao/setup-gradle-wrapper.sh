#!/usr/bin/env bash

for DIR in [0-9][0-9]*-*
do
    pushd $DIR
    gradle wrapper
    popd
done

