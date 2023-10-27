
for DIR in 0?-* 1?-*
do

    cp -p build-hibernate.gradle $DIR/build.gradle

    cp -p gradle.properties $DIR/

    cp -p settings.gradle $DIR/

    # cp -p logback.xml $DIR/src/main/resources

    # cp -p hibernate.properties $DIR/src/main/resources
done

#cp hibernate.properties-ch10 ch10-mysql/src/main/resources
#cp hibernate.properties      ch11-eclipse/src/main/resources
#cp hibernate.properties      ch12-maven-annotation/src/main/resources

# Windows file format
# find . -depth -type f | xargs --verbose -L1 unix2dos --quiet
# find . -depth -type f -name "*.sh" | xargs --verbose -L1 dos2unix --quiet

