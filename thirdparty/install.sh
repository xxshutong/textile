#!/bin/sh
#

#
# BeanShell 2.0.0.b4
# Source: OfBiz modification
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=ofbiz-bsh -Dversion=2.0.0.b4 -Dpackaging=jar -Dfile=./lib/bsh-2.0b4.jar

#
# ICU4J 3.6.1
# Source: Orbit project
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=com.ibm.icu -Dversion=3.6.1 -Dpackaging=jar -Dfile=./lib/com.ibm.icu_3.6.1.v20080530.jar

#
# OWASP Enterprise Security API
# Source: ofbiz/framework/base/lib
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=org.owasp.esapi -Dversion=1.4.4 -Dpackaging=jar -Dfile=./lib/ESAPI-1.4.4.jar

#
# Minerva TX software
# Source: ofbiz/framework/entity/lib
# Provided w/o sources due to a lincense issue.
# Version actually unknown.
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=ofbiz-minerva -Dversion=1.0.0 -Dpackaging=jar -Dfile=./lib/ofbiz-minerva.jar

#
# org.apache.xmlrpc 3.0.0
# Source: Orbit project
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=org.apache.xmlrpc -Dversion=3.0.0-v20100427-1100 -Dpackaging=jar -Dfile=./lib/org.apache.xmlrpc_3.0.0.v20100427-1100.jar

#
# org.apache.ws.commons.util 1.0.1
# Source: Orbit project
#
mvn install:install-file -DgroupId=org.opentaps -DartifactId=org.apache.ws.commons.util -Dversion=1.0.1.v20100518-1140 -Dpackaging=jar -Dfile=./lib/org.apache.ws.commons.util_1.0.1.v20100518-1140.jar


