#!/usr/bin/env bash
USERNAME="root"
PASSWORD="tiny"
DRIVER="com.mysql.jdbc.Driver"
URL="jdbc:mysql://192.168.1.8:3306/liquibase?useUnicode=true&characterEncoding=utf-8"
CP="lib/liquibase-core-3.6.3.jar:lib/ext/*"
CHANGE_LOG="../main/resources/db/master.xml"
TARGET=./generated-update-sql.sql

java -cp ${CP} \
    liquibase.integration.commandline.Main \
     --changeLogFile=${CHANGE_LOG} \
     --driver=${DRIVER} \
     --url=${URL} \
     --username=${USERNAME} \
     --password=${PASSWORD} \
     updateSQL > ${TARGET}