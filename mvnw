#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Maven Start Up Batch script
#
# Required ENV vars:
# ------------------
#   JAVA_HOME - location of a JDK home dir
#
# Optional ENV vars
# -----------------
#   MAVEN_OPTS - parameters passed to the Java VM when running Maven
#     e.g. to debug Maven itself, use
#       set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
#   MAVEN_SKIP_RC - flag to disable loading of mavenrc files
# ----------------------------------------------------------------------------

if [ -z "$MAVEN_SKIP_RC" ] ; then

  if [ -f /usr/local/etc/mavenrc ] ; then
    . /usr/local/etc/mavenrc
  fi

  if [ -f /etc/mavenrc ] ; then
    . /etc/mavenrc
  fi

  if [ -f "$HOME/.mavenrc" ] ; then
    . "$HOME/.mavenrc"
  fi

fi

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
mingw=false
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  MINGW*) mingw=true ;;
  Darwin*) darwin=true ;;
esac

if [ -z "$JAVA_HOME" ] ; then
  if $darwin ; then

    if [ -d "/Library/Java/JavaVirtualMachines/CurrentJDK/Contents/Home" ] ; then
      JAVA_HOME="/Library/Java/JavaVirtualMachines/CurrentJDK/Contents/Home"
    fi

  else

    if [ -r /etc/gentoo-release ] ; then
      JAVA_HOME=$(java-config --jre-home)
    fi

  fi
fi

if [ -z "$MAVEN_HOME" ] ; then
  if $darwin ; then

    if [ -d "/usr/share/maven" ] ; then
      MAVEN_HOME="/usr/share/maven"
    fi

  else

    if [ -d "/usr/share/maven" ] ; then
      MAVEN_HOME="/usr/share/maven"
    fi

  fi
fi

if [ -z "$MAVEN_HOME" ] ; then
  if $cygwin ; then
    if [ -d "$HOME/maven" ] ; then
      MAVEN_HOME="$HOME/maven"
    fi
  fi
fi

if [ -z "$MAVEN_HOME" ] ; then
  if $mingw ; then
    if [ -d "/c/maven" ] ; then
      MAVEN_HOME="/c/maven"
    fi
  fi
fi

if [ -z "$MAVEN_HOME" ] ; then
  if [ -d "$HOME/maven" ] ; then
    MAVEN_HOME="$HOME/maven"
  fi
fi

if [ -z "$MAVEN_HOME" ] ; then
  if [ -d "$HOME/.m2/wrapper/dists/apache-maven-3.9.6" ] ; then
    MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.9.6"
  fi
fi

if [ -z "$MAVEN_HOME" ] ; then
  if [ -d "$HOME/.m2/wrapper/dists/apache-maven-3.8.8" ] ; then
    MAVEN_HOME="$HOME/.m2/wrapper/dists/apache-maven-3.8.8"
  fi
fi

if [ -z "$MAVEN_HOME" ] ; then
  echo "Could not find MAVEN_HOME!"
  exit 1
fi

if [ -z "$JAVA_HOME" ] ; then
  echo "JAVA_HOME is not set!"
  exit 1
fi

if [ ! -d "$JAVA_HOME" ] ; then
  echo "JAVA_HOME ($JAVA_HOME) is not a directory!"
  exit 1
fi

if [ ! -x "$JAVA_HOME/bin/java" ] ; then
  echo "JAVA_HOME ($JAVA_HOME) is not set correctly!"
  exit 1
fi

MAVEN_JAVA_EXEC="$JAVA_HOME/bin/java"

MAVEN_JAR="$MAVEN_HOME/boot/plexus-classworlds-2.7.0.jar"

if [ ! -f "$MAVEN_JAR" ] ; then
  echo "Could not find $MAVEN_JAR!"
  exit 1
fi

if [ -z "$MAVEN_PROJECTBASEDIR" ]; then
  MAVEN_PROJECTBASEDIR=$(dirname "$0")
  MAVEN_PROJECTBASEDIR=$(cd "$MAVEN_PROJECTBASEDIR" && pwd)
fi

CLASSWORLDS_CONF="$MAVEN_PROJECTBASEDIR/.mvn/jvm.config"

if [ -f "$CLASSWORLDS_CONF" ] ; then
  MAIN_CLASS=org.codehaus.plexus.classworlds.launcher.Launcher
  CLASSWORLDS_CONF="-Dclassworlds.conf=$CLASSWORLDS_CONF"
else
  MAIN_CLASS=org.apache.maven.wrapper.MavenWrapperMain
  CLASSWORLDS_CONF=""
fi

WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ] ; then
  echo "Could not find Maven Wrapper at $WRAPPER_JAR!"
  exit 1
fi

exec "$MAVEN_JAVA_EXEC" \
  $MAVEN_OPTS \
  $CLASSWORLDS_CONF \
  -classpath "$WRAPPER_JAR" \
  -Dmaven.home="$MAVEN_HOME" \
  -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" \
  $MAIN_CLASS "$@"