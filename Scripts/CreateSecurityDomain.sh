#!/usr/bin/env bash
/bin/cp /java/Source/Anouman/usersAnouman.properties /java/wildfly/standalone/configuration/usersAnouman.properties
/bin/cp /java/Source/Anouman/rolesAnouman.properties /java/wildfly/standalone/configuration/rolesAnouman.properties

/java/wildfly-12.0.0.Final/bin/jboss-cli.sh --file=/java/Source/Anouman/Scripts/CreateSecurityDomain.cli
