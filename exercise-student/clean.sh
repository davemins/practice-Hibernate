#!/usr/bin/env zsh

# don't throw errors when file globs don't match anything
setopt NULL_GLOB

rm -rf ex*/{.gradle,.settings,bin,build,.vscode}
rm -f ex*/{.project,.classpath}

# for dir in ex*
# do
#     echo $dir;
#     pushd $dir
#     gradle clean
#     popd
# done

# rm -rf 0[1-8]*/gradle
# rm -f 0[1-8]*/{gradlew,gradlew.bat}

