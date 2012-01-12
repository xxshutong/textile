package org.opentaps.notes.tests;

import org.opentaps.tests.OpentapsTestCase;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import static org.ops4j.pax.exam.CoreOptions.*;

public class NotesTestCase extends OpentapsTestCase {


    @Configuration
    public Option[] config() {
        System.err.println("getting PAX config");
        return options(
                       logProfile(),
                       systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("INFO"),
                       mavenBundle().groupId("asm").artifactId("asm-all"),
                       mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.serp"),
                       mavenBundle().groupId("org.apache.geronimo.specs").artifactId("geronimo-jpa_2.0_spec"),
                       mavenBundle().groupId("org.apache.geronimo.specs").artifactId("geronimo-jta_1.1_spec"),
                       mavenBundle().groupId("org.apache.geronimo.specs").artifactId("geronimo-jms_1.1_spec"),
                       wrappedBundle(mavenBundle().groupId("org.apache.ws.commons").artifactId("ws-commons-util")),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.codec"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.httpclient"),
                       mavenBundle().groupId("org.opentaps").artifactId("org.apache.xmlrpc").version("3.0.0-v20100427-1100"),
                       mavenBundle().groupId("org.apache.oro").artifactId("com.springsource.org.apache.oro"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.collections"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.lang"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.pool"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.digester"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.beanutils"),
                       mavenBundle().groupId("org.apache.commons").artifactId("com.springsource.org.apache.commons.validator"),
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
                       mavenBundle().groupId("org.apache.openjpa").artifactId("openjpa").version("2.0.1"),
                       mavenBundle().groupId("mysql").artifactId("mysql-connector-java").version("5.1.10"),
                       mavenBundle().groupId("org.apache.derby").artifactId("derby"),
                       mavenBundle().groupId("org.opentaps").artifactId("entity").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("core").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("tests").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.api").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.services").version("2.0.1-SNAPSHOT"),
                       mavenBundle().groupId("org.opentaps").artifactId("notes.repository").version("2.0.1-SNAPSHOT"),
                       junitBundles(),
                       felix()
                       );
    }
}
