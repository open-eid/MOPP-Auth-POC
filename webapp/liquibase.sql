-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: /Users/siimsuu/projects/id-card-auth-service/webapp/src/main/resources/db/changelog/db.changelog-master.yaml
-- Ran at: 10/3/18, 3:18 PM
-- Against: postgres@jdbc:postgresql://localhost:5432/POC
-- Liquibase version: 3.6.2
-- *********************************************************************

-- Create Database Lock Table
CREATE TABLE public.databasechangeloglock (ID INTEGER NOT NULL, LOCKED BOOLEAN NOT NULL, LOCKGRANTED TIMESTAMP WITHOUT TIME ZONE, LOCKEDBY VARCHAR(255), CONSTRAINT DATABASECHANGELOGLOCK_PKEY PRIMARY KEY (ID));

-- Initialize Database Lock Table
DELETE FROM public.databasechangeloglock;

INSERT INTO public.databasechangeloglock (ID, LOCKED) VALUES (1, FALSE);

-- Lock Database
UPDATE public.databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = '192.168.77.114 (192.168.77.114)', LOCKGRANTED = '2018-10-03 15:18:44.113' WHERE ID = 1 AND LOCKED = FALSE;

-- Create Database Change Log Table
CREATE TABLE public.databasechangelog (ID VARCHAR(255) NOT NULL, AUTHOR VARCHAR(255) NOT NULL, FILENAME VARCHAR(255) NOT NULL, DATEEXECUTED TIMESTAMP WITHOUT TIME ZONE NOT NULL, ORDEREXECUTED INTEGER NOT NULL, EXECTYPE VARCHAR(10) NOT NULL, MD5SUM VARCHAR(35), DESCRIPTION VARCHAR(255), COMMENTS VARCHAR(255), TAG VARCHAR(255), LIQUIBASE VARCHAR(20), CONTEXTS VARCHAR(255), LABELS VARCHAR(255), DEPLOYMENT_ID VARCHAR(10));

-- Changeset /Users/siimsuu/projects/id-card-auth-service/webapp/src/main/resources/db/changelog/changes/initial-version.sql::raw::includeAll
create table account (
  id SERIAL,
  national_identity_number varchar(20) not null,
  device_id varchar(256) not null,
  created timestamp not null default current_timestamp,
  primary key (id)
);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, CONTEXTS, LABELS, LIQUIBASE, DEPLOYMENT_ID) VALUES ('raw', 'includeAll', '/Users/siimsuu/projects/id-card-auth-service/webapp/src/main/resources/db/changelog/changes/initial-version.sql', NOW(), 1, '8:243afb94b416a11b956a048f48b7a268', 'sql', '', 'EXECUTED', NULL, NULL, '3.6.2', '8569124485');

-- Release Database Lock
UPDATE public.databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

