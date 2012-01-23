#!/bin/sh
#
# Copyright (c) Open Source Strategies, Inc.
#
# Opentaps is free software: you can redistribute it and/or modify it
# under the terms of the GNU Affero General Public License as published
# by the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# Opentaps is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.

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

