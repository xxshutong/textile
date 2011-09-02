#!/bin/sh
#

#
# org.apache.xmlrpc 3.0.0
# Source: Orbit project
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=org.apache.xmlrpc -Dversion=3.0.0-v20100427-1100 -Dpackaging=jar -Dfile=./lib/org.apache.xmlrpc_3.0.0.v20100427-1100.jar

#
# org.apache.ws.commons.util 1.0.1
# Source: Orbit project
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=org.apache.ws.commons.util -Dversion=1.0.1-v20100518-1140 -Dpackaging=jar -Dfile=./lib/org.apache.ws.commons.util_1.0.1.v20100518-1140.jar

