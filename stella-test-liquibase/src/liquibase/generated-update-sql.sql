Starting Liquibase at Fri, 22 Nov 2019 15:30:19 CST (version 3.6.3 built at 2019-01-29 11:34:48)
--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: ../main/resources/db/master.xml
--  Ran at: 11/22/19 3:30 PM
--  Against: root@192.168.1.154@jdbc:mysql://192.168.1.8:3306/liquibase?useUnicode=true&characterEncoding=utf-8
--  Liquibase version: 3.6.3
--  *********************************************************************

--  Lock Database
UPDATE liquibase.DATABASECHANGELOGLOCK SET `LOCKED` = 1, LOCKEDBY = '192.168.1.154 (192.168.1.154)', LOCKGRANTED = '2019-11-22 15:30:20.816' WHERE ID = 1 AND `LOCKED` = 0;

--  Changeset ../main/resources/db/0.0/init.xml::1::sail
CREATE TABLE liquibase.USER_INFO (ID BIGINT NOT NULL, NAME VARCHAR(32) DEFAULT '' NOT NULL, CONSTRAINT PK_USER_INFO PRIMARY KEY (ID), UNIQUE (NAME));

INSERT INTO liquibase.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1', 'sail', '../main/resources/db/0.0/init.xml', NOW(), 5, '8:5d3bce498274671f0875e0caa25df64c', 'createTable tableName=USER_INFO', '', 'EXECUTED', NULL, NULL, '3.6.3', '4407821859');

--  Changeset ../main/resources/db/0.1/alter_user_info.xml::1::sail
ALTER TABLE liquibase.USER_INFO ADD PASSWORD VARCHAR(32) DEFAULT '' NULL;

INSERT INTO liquibase.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1', 'sail', '../main/resources/db/0.1/alter_user_info.xml', NOW(), 6, '8:70af14904ea851c0bbbba108616cf11f', 'addColumn tableName=USER_INFO', '', 'EXECUTED', NULL, NULL, '3.6.3', '4407821859');

--  Changeset ../main/resources/db/0.1/add_user_ext_info.xml::1::sail
CREATE TABLE liquibase.USER_INFO_EXT (ID BIGINT NOT NULL, BIRTH_DAY datetime DEFAULT '2019-01-01 00:00:00' NOT NULL, CONSTRAINT PK_USER_INFO_EXT PRIMARY KEY (ID));

INSERT INTO liquibase.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1', 'sail', '../main/resources/db/0.1/add_user_ext_info.xml', NOW(), 7, '8:76fffb5e09bc212158abd06e64cd3174', 'createTable tableName=USER_INFO_EXT', '', 'EXECUTED', NULL, NULL, '3.6.3', '4407821859');

--  Changeset ../main/resources/db/0.2/add_user_view.xml::1::sail
CREATE OR REPLACE VIEW liquibase.USER_VIEW AS SELECT
            u.ID as USER_ID, u.NAME as USER_NAME, u.PASSWORD as USER_PASSWORD, ue.BIRTH_DAY as USER_BIRTH_DAY
            FROM
            USER_INFO u, USER_INFO_EXT ue
            WHERE
            u.ID = ue.ID;

INSERT INTO liquibase.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('1', 'sail', '../main/resources/db/0.2/add_user_view.xml', NOW(), 8, '8:b472332605cc8d2629964aa602a48f0d', 'createView viewName=USER_VIEW', '', 'EXECUTED', NULL, NULL, '3.6.3', '4407821859');

--  Changeset ../main/resources/db/0.3/add_user_log_table.sql::sail_change_id_1::sail
--  add user log table
CREATE TABLE USER_LOG (
    ID bigint(20) PRIMARY KEY,
    USER_ID bigint(20) NOT NULL DEFAULT 0,
    CONTENT varchar(255) NOT NULL DEFAULT "",
    KEY `USER_ID` (`USER_ID`)
);

INSERT INTO liquibase.DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, `DESCRIPTION`, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('sail_change_id_1', 'sail', '../main/resources/db/0.3/add_user_log_table.sql', NOW(), 9, '8:d4d7431413af18c9354f4f6eadb98df0', 'sql', 'add user log table', 'EXECUTED', NULL, NULL, '3.6.3', '4407821859');

--  Release Database Lock
UPDATE liquibase.DATABASECHANGELOGLOCK SET `LOCKED` = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

