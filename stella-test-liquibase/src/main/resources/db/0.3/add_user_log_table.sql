--liquibase formatted sql

--changeset sail:sail_change_id_1
--comment: add user log table
CREATE TABLE USER_LOG (
    ID bigint(20) PRIMARY KEY,
    USER_ID bigint(20) NOT NULL DEFAULT 0,
    CONTENT varchar(255) NOT NULL DEFAULT "",
    KEY `USER_ID` (`USER_ID`)
);
--rollback DROP TABLE USER_LOG;