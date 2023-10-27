
echo checking build.gradle

for H in [0-9][0-9]*-*
do
    diff build-hibernate.gradle $H/build.gradle
done

echo checking settings.gradle

for H in [0-9][0-9]*-*
do
    diff settings.gradle $H/settings.gradle
done

echo checking gradle.properties

for H in [0-9][0-9]*-*
do
    diff gradle.properties $H/gradle.properties
done

echo checking logback.xml

for H in [0-9][0-9]*-*
do
    diff logback.xml $H/src/main/resources/logback.xml
done


# echo checking hibernate.cfg.xml

# for H in [0-9][0-9]*-*
# do
#     diff hibernate.cfg.xml $H/src/main/resources/hibernate.cfg.xml
# done

