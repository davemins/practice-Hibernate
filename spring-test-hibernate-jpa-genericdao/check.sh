
echo checking build.gradle

for H in [0-9][0-9]*-*
do
    diff build.gradle $H/build.gradle
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


# Copy build.gradle gradle.properties settings.gradle to SUB-DIRS

# echo copying build.gradle

# for H in */build.gradle
# do
#     cp -p build.gradle $H
# done

# echo copying gradle.properties

# for H in */gradle.properties
# do
#     cp -p gradle.properties $H
# done

# echo copying settings.gradle

# for H in */settings.gradle
# do
#     cp -p settings.gradle $H
# done

