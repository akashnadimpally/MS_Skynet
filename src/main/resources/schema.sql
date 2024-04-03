SET @dbname = DATABASE();
SET @tablename = "SPRING_SESSION";
SET @indexname1 = "SPRING_SESSION_IX1";
SET @indexname2 = "SPRING_SESSION_IX2";
SET @indexname3 = "SPRING_SESSION_IX3";

-- Create SPRING_SESSION table if it does not exist
CREATE TABLE IF NOT EXISTS SPRING_SESSION (
                                              PRIMARY_ID CHAR(36) NOT NULL,
                                              SESSION_ID CHAR(36) NOT NULL,
                                              CREATION_TIME BIGINT NOT NULL,
                                              LAST_ACCESS_TIME BIGINT NOT NULL,
                                              MAX_INACTIVE_INTERVAL INT NOT NULL,
                                              EXPIRY_TIME BIGINT NOT NULL,
                                              PRINCIPAL_NAME VARCHAR(100),
                                              CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

-- Create indexes conditionally
SELECT COUNT(*) INTO @exist FROM INFORMATION_SCHEMA.STATISTICS
WHERE table_schema = @dbname AND table_name = @tablename AND index_name = @indexname1;
SET @sql = IF(@exist <= 0, CONCAT('CREATE INDEX ', @indexname1, ' ON ', @tablename, ' (SESSION_ID)'), 'SELECT ''Index exists''');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Repeat for other indexes
SELECT COUNT(*) INTO @exist FROM INFORMATION_SCHEMA.STATISTICS
WHERE table_schema = @dbname AND table_name = @tablename AND index_name = @indexname2;
SET @sql = IF(@exist <= 0, CONCAT('CREATE INDEX ', @indexname2, ' ON ', @tablename, ' (EXPIRY_TIME)'), 'SELECT ''Index exists''');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT COUNT(*) INTO @exist FROM INFORMATION_SCHEMA.STATISTICS
WHERE table_schema = @dbname AND table_name = @tablename AND index_name = @indexname3;
SET @sql = IF(@exist <= 0, CONCAT('CREATE INDEX ', @indexname3, ' ON ', @tablename, ' (PRINCIPAL_NAME)'), 'SELECT ''Index exists''');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Create SPRING_SESSION_ATTRIBUTES table if it does not exist
CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES (
                                                         SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                                                         ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                                                         ATTRIBUTE_BYTES BLOB NOT NULL,
                                                         CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                                                         CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;


CREATE TABLE IF NOT EXISTS users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       contact_number VARCHAR(50) NOT NULL,
                       email VARCHAR(45) NOT NULL,
                       first_name VARCHAR(45) NOT NULL,
                       last_name VARCHAR(45) NOT NULL,
                       password VARCHAR(1000) NOT NULL,
                       country VARCHAR(45) NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE KEY (contact_number),
                       UNIQUE KEY (email)
);
