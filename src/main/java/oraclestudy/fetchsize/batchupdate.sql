DROP TABLE INSERT_TO_MILLION_ROWS_TABLE PURGE;
CREATE TABLE INSERT_TO_MILLION_ROWS_TABLE 
(
  COLUMN1 VARCHAR2(20) 
);

ALTER SYSTEM FLUSH BUFFER_CACHE;
