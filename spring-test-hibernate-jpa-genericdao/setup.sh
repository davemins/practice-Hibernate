#!/usr/bin/env bash

for H in [0-9][0-9]*-*
do
    cp -p build.gradle "$H"/
    # cp -p gradle.properties $H
    # cp -p settings.gradle $H/
done

if false; then
    ruplacer --no-regex "hibernate_sequence.nextval" "nextval('hibernate_sequence')" --type '*.sql'
    ruplacer --no-regex "hibernate_sequence.currval" "currval('hibernate_sequence')" --type '*.sql'

    for F in **/import.sql; do echo "$F"; meld "$F" import.sql; done

    for F in **/build.gradle; do echo "$F"; diff "$F" build.gradle; done
fi
