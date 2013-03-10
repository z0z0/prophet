#!/bin/sh

# BUILD CLASSPATH
base_dir=$(dirname $0)/..
CLASSPATH=$(echo $base_dir/target/*.jar $base_dir/lib/*.jar | tr " " :)

# OTHER PARAMETERS
USER_ARGS="$@"
JVM_ARGS="-Xmx2048m -Xms512m"
CLASSPATH="${CLASSPATH}:conf/"
MAIN_CLASS="org.zprj.prophet.ProphetApp"

# RUN APPLICATION
echo "Starting: java -server -cp $CLASSPATH -Djava.library.path=${EXTERNAL_LIBS} ${JVM_ARGS} ${MAIN_CLASS} ${USER_ARGS}"
java -server -cp $CLASSPATH ${JVM_ARGS} ${MAIN_CLASS} ${USER_ARGS}