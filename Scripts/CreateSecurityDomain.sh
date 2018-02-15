#!/usr/bin/env bash
/bin/cp /java/Source/Anouman/usersAnouman.properties /java/wildfly-11.0.0.Final/standalone/configuration/usersAnouman.properties
/bin/cp /java/Source/Anouman/rolesAnouman.properties /java/wildfly-11.0.0.Final/standalone/configuration/rolesAnouman.properties

/java/wildfly-11.0.0.Final/bin/jboss-cli.sh --file=/java/Source/Anouman/Scripts/CreateSecurityDomain.cli
