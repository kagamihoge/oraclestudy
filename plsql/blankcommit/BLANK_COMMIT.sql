create or replace PROCEDURE BLANK_COMMIT AS 
  INTO_1 INT;
BEGIN
  FOR I IN 1..1000000 LOOP
    SELECT 1 INTO INTO_1 FROM DUAL;
    COMMIT;
  END LOOP;
END BLANK_COMMIT;