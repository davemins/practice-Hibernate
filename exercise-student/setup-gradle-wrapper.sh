#!/usr/bin/env bash

for DIR in ex* first
do
    pushd $DIR
    gradle wrapper
    popd
done

