#!/usr/bin/env bash
USERNAME="root"
PASSWORD="tiny"
DRIVER="com.mysql.jdbc.Driver"
URL="jdbc:mysql://192.168.1.8:3306/liquibase?useUnicode=true&characterEncoding=utf-8"
CP="lib/liquibase-core-3.6.3.jar:lib/ext/*"
TARGET=./generated-chenge-log.xml

java -cp ${CP} \
    liquibase.integration.commandline.Main \
     --changeLogFile=${TARGET} \
     --driver=${DRIVER} \
     --url=${URL} \
     --username=${USERNAME} \
     --password=${PASSWORD} \
     generateChangeLog