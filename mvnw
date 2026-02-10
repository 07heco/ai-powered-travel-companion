#!/bin/sh

# ----------------------------------------------------------------------------
# Maven Start Up Batch script
# 
# Required ENV vars:
# JAVA_HOME - location of a JDK home dir
# 
# Optional ENV vars
# M2_HOME - location of maven2's installed home dir
# MAVEN_BATCH_ECHO - set to 'on' to enable the echoing of the batch commands
# MAVEN_BATCH_PAUSE - set to 'on' to wait for a keystroke before ending
# MAVEN_OPTS - parameters passed to the Java VM when running Maven
#     e.g. to debug Maven itself, use
# set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
# MAVEN_SKIP_RC - flag to disable loading of mavenrc files
# ----------------------------------------------------------------------------

# Begin all REM lines with '@' in case MAVEN_BATCH_ECHO is 'on'
@REM "@""%~dp0""\.mvn\wrapper\maven-wrapper.jar" "%~dp0"" %*

if [ -z "$MAVEN_SKIP_RC" ]; then
  if [ -f "${HOME}/.mavenrc" ]; then
    . "${HOME}/.mavenrc"
  fi
  if [ -f ".mavenrc" ]; then
    . ".mavenrc"
  fi
fi

# Provide a default JAVA_HOME if not set
if [ -z "$JAVA_HOME" ]; then
  if [ -x "/usr/libexec/java_home" ]; then
    export JAVA_HOME=$(/usr/libexec/java_home)
  elif [ -d "/usr/java/latest" ]; then
    export JAVA_HOME="/usr/java/latest"
  elif [ -d "/usr/java/default" ]; then
    export JAVA_HOME="/usr/java/default"
  else
    echo "Error: JAVA_HOME is not set and no 'java' command could be found in your PATH."
    echo "Please set the JAVA_HOME variable in your environment to match the"
    echo "location of your Java installation."
    exit 1
  fi
fi

# Set JAVA_EXE
JAVA_EXE="$JAVA_HOME/bin/java"
if [ ! -x "$JAVA_EXE" ]; then
  echo "Error: JAVA_HOME is set to an invalid directory: $JAVA_HOME"
  echo "Please set the JAVA_HOME variable in your environment to match the"
  echo "location of your Java installation."
  exit 1
fi

# Set MAVEN_OPTS
if [ -z "$MAVEN_OPTS" ]; then
  MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
fi

# Set MAVEN_PROJECTBASEDIR
MAVEN_PROJECTBASEDIR=$(pwd)

# Run Maven
"$JAVA_EXE" $MAVEN_OPTS -classpath "$(dirname "$0")/.mvn/wrapper/maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain "$@"
