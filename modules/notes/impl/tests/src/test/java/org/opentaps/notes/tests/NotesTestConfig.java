/*
 * Copyright (c) Open Source Strategies, Inc.
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opentaps.notes.tests;

import org.opentaps.tests.OpentapsTestCase;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import static org.ops4j.pax.exam.CoreOptions.*;

public class NotesTestConfig extends OpentapsTestCase {

    @Configuration
    public Option[] config() {
        System.err.println("getting PAX config");
        return options(
                       systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("WARN"),
                       mavenBundle().groupId("ch.qos.logback").artifactId("logback-core").version("0.9.29"),
                       mavenBundle().groupId("ch.qos.logback").artifactId("logback-classic").version("0.9.29"),
                       mavenBundle().groupId("asm").artifactId("asm-all"),
                       mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.serp"),
                       mavenBundle().groupId("org.apache.geronimo.specs").artifactId("geronimo-jpa_2.0_spec"),
                       mavenBundle().groupId("org.apache.geronimo.specs").artifactId("geronimo-jta_1.1_spec"),
                       mavenBundle().groupId("org.apache.geronimo.specs").artifactId("geronimo-validation_1.0_spec"),
                       wrappedBundle(mavenBundle().groupId("org.apache.ws.commons").artifactId("ws-commons-util")),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.codec"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.httpclient"),
                       mavenBundle().groupId("org.opentaps").artifactId("org.apache.xmlrpc").version("3.0.0-v20100427-1100"),
                       mavenBundle().groupId("org.apache.oro").artifactId("com.springsource.org.apache.oro"),
                       mavenBundle().groupId("org.apache.log4j").artifactId("com.springsource.org.apache.log4j").version("1.2.15"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.logging").version("1.1.1"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.collections"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.lang"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.pool"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.digester"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.beanutils"),
                       mavenBundle().groupId("commons-validator").artifactId("commons-validator").version("1.4.0"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.dbcp"),
                       mavenBundle().groupId("org.apache.aries").artifactId("org.apache.aries.util"),
                       mavenBundle().groupId("org.apache.aries.proxy").artifactId("org.apache.aries.proxy"),
                       mavenBundle().groupId("org.apache.aries.blueprint").artifactId("org.apache.aries.blueprint"),
                       mavenBundle().groupId("org.apache.aries.jpa").artifactId("org.apache.aries.jpa.api"),
                       mavenBundle().groupId("org.apache.aries.jpa").artifactId("org.apache.aries.jpa.blueprint.aries"),
                       mavenBundle().groupId("org.apache.aries.jpa").artifactId("org.apache.aries.jpa.container"),
                       mavenBundle().groupId("org.apache.aries.jpa").artifactId("org.apache.aries.jpa.container.context"),
                       mavenBundle().groupId("org.apache.aries.transaction").artifactId("org.apache.aries.transaction.manager"),
                       mavenBundle().groupId("org.apache.aries.transaction").artifactId("org.apache.aries.transaction.blueprint"),
                       mavenBundle().groupId("org.apache.aries.jndi").artifactId("org.apache.aries.jndi.api"),
                       mavenBundle().groupId("org.apache.aries.jndi").artifactId("org.apache.aries.jndi.core"),
                       mavenBundle().groupId("org.apache.aries.jndi").artifactId("org.apache.aries.jndi.url"),
                       mavenBundle().groupId("org.apache.bval").artifactId("org.apache.bval.bundle").version("0.3-incubating"),
                       mavenBundle().groupId("org.opentaps").artifactId("validation.api").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("validation.testimpl").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.apache.openjpa").artifactId("openjpa").version("2.1.1"),
                       mavenBundle().groupId("mysql").artifactId("mysql-connector-java").version("5.1.10"),
                       mavenBundle().groupId("org.restlet.jee").artifactId("org.restlet").version("2.0.11"),
                       mavenBundle().groupId("org.testng").artifactId("com.springsource.org.testng").version("5.10.0"),
                       mavenBundle().groupId("org.opentaps").artifactId("entity").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("core").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("tests").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.services.api").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.services.impl").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.security.impl").version("2.0.1-SNAPSHOT"),
                       //mavenBundle().groupId("org.opentaps").artifactId("notes.repository.impl.jpa").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.repository.impl.mongo").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.domain").version("2.0.1-SNAPSHOT"),
                       junitBundles(),
                       felix()
                       );
    }
}
