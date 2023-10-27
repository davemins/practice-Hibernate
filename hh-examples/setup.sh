#!/usr/bin/env zsh

# don't throw errors when file globs don't match anything like Bash
# setopt null_glob

setopt extendedglob

# exlude both *spring* and *kotlin*
prj_hibernate=( [0-9]*~*kotlin*~*querydsl*~*spring*~*nativeSql* )
prj_kotlin=([0-9]*kotlin*)
prj_querydsl=([0-9]*querydsl*)
prj_spring=([0-9]*spring*~*springboot*~*springmvc*)
prj_springboot=([0-9]*springboot*)

for DIR in $prj_hibernate
do
    print -l $DIR
    cp -p build-hibernate.gradle $DIR/build.gradle
    # cp -p gradle.properties $DIR/gradle.properties
    cp -p settings.gradle $DIR/settings.gradle
done

for DIR in $prj_kotlin
do
    print -l $DIR
    cp -p build-kotlin.gradle $DIR/build.gradle
    # cp -p gradle.properties $DIR/gradle.properties
    cp -p settings.gradle $DIR/settings.gradle
done

for DIR in $prj_spring
do
    print -l $DIR
    ##diff build-spring.gradle $DIR/build.gradle
    #cp -p gradle.properties $DIR/gradle.properties
    cp -p settings.gradle $DIR/settings.gradle
done

for DIR in $prj_springboot
do
    print -l $DIR
    #diff build-springboot.gradle $DIR/build.gradle
    cp -p settings.gradle $DIR/settings.gradle
done

# echo checking logback.xml

# for DIR in 0?-* 1[12]-*
# do
#     cp -p logback.xml $DIR/src/main/resources/logback.xml
# done





# Windows file format
# find . -depth -type f | xargs --verbose -L1 unix2dos --quiet
# find . -depth -type f -name "*.sh" | xargs --verbose -L1 dos2unix --quiet

