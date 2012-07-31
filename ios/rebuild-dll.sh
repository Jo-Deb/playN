#!/bin/sh

if [ -z "$IKVM_HOME" ]; then
    IKVM_HOME=$HOME/projects/ikvm-monotouch
fi
MAVEN_REPO=$HOME/.m2/repository
export MONO_PATH=/Developer/MonoTouch/usr/lib/mono/2.1

cd `dirname $0`

echo "Converting Java bytecode to CLR dll..."
rm -f showcase-core.dll
mono $IKVM_HOME/bin/ikvmc.exe -nostdlib -debug -target:library -out:showcase-core.dll \
    -r:$MONO_PATH/mscorlib.dll \
    -r:$MONO_PATH/System.dll \
    -r:$MONO_PATH/System.Core.dll \
    -r:$MONO_PATH/System.Data.dll \
    -r:$MONO_PATH/OpenTK.dll \
    -r:$MONO_PATH/monotouch.dll \
    -r:$MONO_PATH/Mono.Data.Sqlite.dll \
    $MAVEN_REPO/com/samskivert/pythagoras/1.1/pythagoras-1.1.jar \
    $MAVEN_REPO/com/threerings/react/1.1/react-1.1.jar \
    $MAVEN_REPO/com/threerings/tripleplay/1.1/tripleplay-1.1.jar \
    $MAVEN_REPO/com/googlecode/playn/playn-core/1.1/playn-core-1.1.jar \
    $MAVEN_REPO/com/googlecode/playn/playn-jbox2d/1.1/playn-jbox2d-1.1.jar \
    $MAVEN_REPO/com/googlecode/playn/playn-ios/1.1/playn-ios-1.1.jar \
    ../core/target/playn-showcase-core-1.0-SNAPSHOT.jar
